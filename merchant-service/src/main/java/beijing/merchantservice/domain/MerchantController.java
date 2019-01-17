package beijing.merchantservice.domain;

import beijing.merchantservice.exception.CorruptedTokenException;
import beijing.merchantservice.exception.DataAccessException;
import beijing.merchantservice.exception.RequestRejected;
import beijing.merchantservice.repository.IMerchantRepositry;
import beijing.merchantservice.repository.MerchantRepositry;

import java.io.IOException;
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

	IMerchantRepositry repositry;

	private final static String TOKENID_TO_MERCHANTSERVICE_QUEUE = "tokenid_to_merchantservice";
	private final static String MERCHANTSERVICE_TO_TOKENID_QUEUE = "merchantservice_to_tokenid";
	private final static String RPC_MERCHANTSERVICE_TO_PAYMENTSERVICE_QUEUE = "rpc_merchantservice_to_paymentservice";

	private ConnectionFactory factory;
	private Connection connection;
	private Channel channel;
	private Consumer consumer;

	public MerchantController() throws IOException, TimeoutException {
		repositry = new MerchantRepositry();

		// TODO: set up a message queue listener for new tokens.. use storeNewToken
		try {
			factory = new ConnectionFactory();
			factory.setUsername("guest");
			factory.setPassword("guest");
			factory.setHost("localhost");
			connection = factory.newConnection();
			channel = connection.createChannel();
		} catch (Exception e) {
			throw new TimeoutException("could not connect");
		}

		channel.queueDeclare(TOKENID_TO_MERCHANTSERVICE_QUEUE, false, false, false, null);
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println(" [x] Received '" + message + "'");
			// Add the token from the message to the repository
			// IF the message is made for first 6 letters are tokenID, then a space and next
			// 6 are the customerId
			String tokenId = StringUtils.left(message, 6);
			String customerId = StringUtils.substring(message, 8, 13);
			try {
				repositry.addToken(new TokenValidation(true, tokenId, customerId));
			} catch (CorruptedTokenException e) {
				e.printStackTrace();
			}

		};
		channel.basicConsume(TOKENID_TO_MERCHANTSERVICE_QUEUE, true, deliverCallback, consumerTag -> {
		});
	}

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
		repositry.createTransaction(to);

		return to;
	}

	private void storeNewToken(List<TokenValidation> newTokens) {
		for (TokenValidation t : newTokens) {
			try {
				repositry.addToken(t);
			} catch (CorruptedTokenException e) {
				e.printStackTrace();
			}
		}
	}

	private TokenValidation getTokenValidation(String tokenId) throws CorruptedTokenException {
		// TODO: check in repository
		if (repositry.getTokenById(tokenId) == null) {
			throw new CorruptedTokenException("Token does not exsist");
		}
		return repositry.getTokenById(tokenId);
	}

	private void updateToken(String tokenId, String status) throws IOException {
		// TODO: send request to tokenService through rabbitMQ
		channel.queueDeclare(MERCHANTSERVICE_TO_TOKENID_QUEUE, false, false, false, null);
		String message = tokenId + " " + status;
		channel.basicPublish("", MERCHANTSERVICE_TO_TOKENID_QUEUE, null, message.getBytes());

	}

	private TransactionObject requestPayment(String merchantId, String customerId, String amount)
			throws IOException, InterruptedException, RequestRejected {
		// TODO: send request to paymentService through rabbitMQ RPC
		final String corrId = UUID.randomUUID().toString();

		String replyQueueName = channel.queueDeclare().getQueue();
		AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName)
				.build();

		String message = merchantId + " " + customerId + " " + amount;

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
			// IF the message is made for first 6 letters are trID, then a space and next 8
			// are the date
			String transactionID = StringUtils.left(result, 6);
			String transactionDate = StringUtils.substring(result, 8, 15);
			to = new TransactionObject(merchantId, transactionID, amount, null);
		}

		channel.basicCancel(ctag);

		return to;

	}
}
