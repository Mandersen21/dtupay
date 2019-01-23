package beijing.merchantservice.domain;

import beijing.merchantservice.exception.CorruptedTokenException;
import beijing.merchantservice.exception.DataAccessException;
import beijing.merchantservice.exception.RequestRejected;
import beijing.merchantservice.repository.IMerchantRepository;
import beijing.merchantservice.repository.MerchantRepository;

import java.io.IOException;
import java.util.Calendar;
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

public class MerchantManager {

	private static IMerchantRepository repository;

//	private final static String TOKENID_TO_MERCHANTSERVICE_QUEUE = "tokenid_to_merchantservice";
	private final static String MERCHANTSERVICE_TO_TOKENID_QUEUE = "merchantservice_to_tokenid";
	private final static String RPC_MERCHANTSERVICE_TO_PAYMENTSERVICE_QUEUE = "rpc_merchantservice_to_paymentservice";
	private final static String MERCHANSERVICE_TO_PAYMENTSERVICE_REGISTRATION_QUEUE = "merchantservice_to_paymentservice_registration_queue";

	private ConnectionFactory factory;
	private Connection connection;
	private Channel channel;
	private Consumer consumer;

	/**
	 * Merchant manager is responsible for the business logic of the merchant-service
	 * and keeps track of the repository and message queues.
	 * @param repository
	 */
	public MerchantManager(IMerchantRepository _repository) {
		repository = _repository;		
				
		try {
			setupMessageQueue();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Merchant m = new Merchant("98765", "0202020202", "Cucumber Aps");
		try {
			this.repository.createMerchant(m);
			this.repository.addToken(new TokenValidation(true,"123", "123456"));
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CorruptedTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	/**
	 * do initial verifications before continuing on eith the 
	 * process to call 
	 * {@link #requestPayment(String, String, String, String)}
	 * @param merchantid
	 * @param tokenid
	 * @param amount
	 * @return
	 * @throws RequestRejected
	 * @throws DataAccessException
	 * @throws CorruptedTokenException
	 * @throws IOException
	 */
	public TransactionObject requestTransaction(String merchantid, String tokenid, String amount,String description)
			throws RequestRejected, DataAccessException, CorruptedTokenException, IOException {

		TokenValidation tv = getTokenValidation(tokenid);
		if (!tv.isValid()) {
			throw new RequestRejected("token is invalid");
		}

		TransactionObject to;
		
		try {
			to = requestPayment(merchantid, tv.getCustomerId(), amount,description);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			updateToken("tokenId", "INVALID");
			throw new RequestRejected("request failed");
		}
		
		
		updateToken("tokenId", "PAID");
		repository.createTransaction(to);

		return to;
	}

	/**
	 * creates a new merchant and stores the data in the repository.
	 * @param merchantId
	 * @param cvrNumber
	 * @param name
	 * @return newly created Merchant object
	 * @throws DataAccessException
	 * @throws RequestRejected 
	 */
	public Merchant createMerchant( String cvrNumber, String name) throws DataAccessException, RequestRejected {
		Merchant m =  repository.getMerchantByCVR(cvrNumber);
		if( m == null) {
			String merchantId = UUID.randomUUID().toString();
			m = new Merchant(merchantId, cvrNumber, name);
			repository.createMerchant(m);
		}else {
			throw new RequestRejected("CVR already registred");
		}
		
		return m;
	}

	/**
	 * retrieves the TokenValidation object with the give tokenId from the database
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
	 * informs the token-service of the use of a token with the given tokenId
	 * @param tokenId
	 * @param status
	 * @throws IOException
	 */
	private void updateToken(String tokenId, String status) throws IOException {
		String message = tokenId + "," + status;
		channel.basicPublish("", MERCHANTSERVICE_TO_TOKENID_QUEUE, null, message.getBytes());
	}
	
	/**
	 * contacts the payment-service through message queue tp request a 
	 * payment between merhcants and customer for the given amount.
	 * @param merchantId
	 * @param customerId
	 * @param amount
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws RequestRejected
	 */
	private TransactionObject requestPayment(String merchantId, String customerId, String amount, String description)
			throws IOException, InterruptedException, RequestRejected {
		
		
		final String corrId = UUID.randomUUID().toString();		
		String replyQueueName = channel.queueDeclare().getQueue();
		AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName)
				.build();

		String message = merchantId + "," + customerId + "," + amount+ "," + description;
		
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
//			String[] resultSplit = result.split(",");
			System.out.print(result);
			to = new TransactionObject(merchantId, UUID.randomUUID().toString(), amount, Calendar.getInstance().getTime());
//			to = new TransactionObject(merchantId, resultSplit[0], amount, new Date(Date.parse(resultSplit[1])));
		}
		channel.basicCancel(ctag);
		return to;
	}
	
	/**
	 * converts message into an object of TokenValidation
	 * and stores the token in the repository.
	 * and stores the object in the database.
	 * @param message
	 * @throws DataAccessException
	 */
	public void receiveNewTokens(String message) throws DataAccessException {
		System.out.println(" [x] Received '" + message + "'");
		String[] tokenMessage = message.split(",");
	
		try {
			repository.addToken(new TokenValidation(true, tokenMessage[0], tokenMessage[1]));
			System.out.println(repository.getTokenValidations());
		} catch (CorruptedTokenException e) {
			e.printStackTrace();
		}
	}


	/**
	 * find and retrieve the repository for a merchant with given id.
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	public Merchant getMerchantById(String id) throws DataAccessException {
		Merchant merchant = repository.getMerchantById(id);
		if (merchant == null) {
			throw new DataAccessException("Merchant with given id was not found");
		}
		return merchant;
	}

	
	/**
	 * sets up the message queue with predefined values and
	 * opens declares the needed channels
	 * @throws IOException
	 * @throws TimeoutException
	 */
	private void setupMessageQueue() throws IOException, TimeoutException {
		factory = new ConnectionFactory();
		factory.setUsername("admin");
		factory.setPassword("Banana");
		factory.setHost("02267-bejing.compute.dtu.dk");
		connection = factory.newConnection();
		channel = connection.createChannel();
		
//		channel.queueDeclare(TOKENID_TO_MERCHANTSERVICE_QUEUE, false, false, false, null);
//		channel.queueDeclare(MERCHANTSERVICE_TO_TOKENID_QUEUE, false, false, false, null);
		channel.queueDeclare(RPC_MERCHANTSERVICE_TO_PAYMENTSERVICE_QUEUE, false, false, false, null);
		
//		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//			String message = new String(delivery.getBody(), "UTF-8");
//			try {
//				receiveNewTokens(message);
//			} catch (DataAccessException e) {
//				e.printStackTrace();
//			}
//		};
//		channel.basicConsume(TOKENID_TO_MERCHANTSERVICE_QUEUE, true, deliverCallback, consumerTag -> {
//			
//		});
	}

	/**
	 * gets the complete list of merchants from the repository
	 * @return
	 * @throws DataAccessException
	 */
	public List<Merchant> getAllMerhcants() throws DataAccessException {
		return repository.getMerchants();
	}
	
	
	
}
