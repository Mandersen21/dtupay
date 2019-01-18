package beijing.merchantservice.domain;

import beijing.merchantservice.exception.CorruptedTokenException;
import beijing.merchantservice.exception.DataAccessException;
import beijing.merchantservice.exception.RequestRejected;
import beijing.merchantservice.repository.IMerchantRepository;
import beijing.merchantservice.repository.MerchantRepository;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.StringUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.GetResponse;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;

public class MerchantController {

	IMerchantRepository repository;

	private final static String TOKENID_TO_MERCHANTSERVICE_QUEUE = "tokenid_to_merchantservice";
	private final static String MERCHANTSERVICE_TO_TOKENID_QUEUE = "merchantservice_to_tokenid";
	private final static String RPC_MERCHANTSERVICE_TO_PAYMENTSERVICE_QUEUE = "rpc_merchantservice_to_paymentservice";
	private final static String MERCHANSERVICE_TO_PAYMENTSERVICE_REGISTRATION_QUEUE = "merchantservice_to_paymentservice_registration_queue";

	private ConnectionFactory factory;
	private Connection connection;
	private Channel channel;
	private Consumer consumer;

	/**
	 * 
	 * @throws IOException
	 * @throws TimeoutException
	 */
	public MerchantController() throws IOException {
		repository = new MerchantRepository();
		

			try {
				factory = new ConnectionFactory();
				factory.setUsername("admin");
				factory.setPassword("Banana");
				factory.setHost("02267-bejing.compute.dtu.dk");
				connection = factory.newConnection();
				channel = connection.createChannel();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (TimeoutException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		channel.queueDeclare(TOKENID_TO_MERCHANTSERVICE_QUEUE, false, false, false, null);
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			try {
				receiveNewTokens(consumerTag,message);
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
		};
		channel.basicConsume(TOKENID_TO_MERCHANTSERVICE_QUEUE, true, deliverCallback, consumerTag -> {
			
		});
	}

	/**
	 * 
	 * @param merchantid
	 * @param tokenid
	 * @param amount
	 * @return
	 * @throws RequestRejected
	 * @throws DataAccessException
	 * @throws CorruptedTokenException
	 * @throws IOException
	 */
	public TransactionObject requestTransaction(String merchantid, String tokenid, String amount)
			throws RequestRejected, DataAccessException, CorruptedTokenException, IOException {

		TokenValidation tv = getTokenValidation(tokenid);
		if (!tv.isValid()) {
			throw new RequestRejected("token is invalid");
		}

		TransactionObject to;
		try {
			to = requestPayment(merchantid, tv.getCustomerId(), amount);
		} catch (Exception e) {
			updateToken("tokenId", "INVALID");
			throw new RequestRejected("request failed");
		}

		updateToken("tokenId", "PAID");
		repository.createTransaction(to);

		return to;
	}

	/**
	 * 
	 * @param merchantId
	 * @param cvrNumber
	 * @param name
	 * @return
	 * @throws DataAccessException
	 */
	public Merchant createMerchant(String merchantId, String cvrNumber, String name) throws DataAccessException {
		Merchant m = new Merchant(merchantId, cvrNumber, name);
		repository.createMerchant(m);
		return m;
	}

	/**
	 * 
	 * @param tokenId
	 * @return
	 * @throws CorruptedTokenException
	 * @throws DataAccessException
	 */
	private TokenValidation getTokenValidation(String tokenId) throws CorruptedTokenException, DataAccessException {
		if (repository.getTokenById(tokenId) == null) {
			throw new CorruptedTokenException("Token does not exsist");
		}
		return repository.getTokenById(tokenId);
	}
	
	/**
	 * 
	 * @param tokenId
	 * @param status
	 * @throws IOException
	 */
	private void updateToken(String tokenId, String status) throws IOException {
		channel.queueDeclare(MERCHANTSERVICE_TO_TOKENID_QUEUE, false, false, false, null);
		String message = tokenId + " " + status;
		channel.basicPublish("", MERCHANTSERVICE_TO_TOKENID_QUEUE, null, message.getBytes());

	}
	
	/**
	 * 
	 * @param merchantId
	 * @param customerId
	 * @param amount
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws RequestRejected
	 */
	private TransactionObject requestPayment(String merchantId, String customerId, String amount)
			throws IOException, InterruptedException, RequestRejected {
		final String corrId = UUID.randomUUID().toString();

		channel.queueDeclare(RPC_MERCHANTSERVICE_TO_PAYMENTSERVICE_QUEUE, false, false, false, null);
		String message = merchantId + " " + customerId + " " + amount;
		
		String replyQueueName = channel.queueDeclare().getQueue();
		AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName)
				.build();

		channel.basicPublish("", RPC_MERCHANTSERVICE_TO_PAYMENTSERVICE_QUEUE, props, message.getBytes("UTF-8"));

		final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

		String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
			if (delivery.getProperties().getCorrelationId().equals(corrId)) {
				response.offer(new String(delivery.getBody(), "UTF-8"));
			}
		}, consumerTag -> {
		});

		String result = response.take();
		TransactionObject to;
		// Make result string into a TransactionObject
		if (result.equalsIgnoreCase("500")) {
			throw new RequestRejected("The payment transaction failed");
		} else {
			String[] resultSplit = result.split(",");
			String transactionID = StringUtils.left(result, 6);
			String transactionDate = StringUtils.substring(result, 8, 15);
			to = new TransactionObject(merchantId, resultSplit[0], amount, new Date(Date.parse(resultSplit[1])));
		}
		channel.basicCancel(ctag);
		return to;
	}
	
	/**
	 * 
	 * @param cTag
	 * @param message
	 * @throws DataAccessException
	 */
	public void receiveNewTokens(String cTag, String message) throws DataAccessException {
		System.out.println(" [x] Received '" + message + "'");
		String[] tokenMessage = message.split(",");
	
		try {
			//repositry.addToken(new TokenValidation(true, tokenId, customerId));
			repository.addToken(new TokenValidation(true, tokenMessage[0], tokenMessage[1]));
			System.out.println(repository.getTokenValidation());
		} catch (CorruptedTokenException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	public Merchant getMerchant(String id) throws DataAccessException {
		Merchant m = repository.getMerchant(id);
		return m;
	}

	
	
	
}
