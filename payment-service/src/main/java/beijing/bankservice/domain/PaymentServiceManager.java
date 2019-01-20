package beijing.bankservice.domain;

import java.io.IOException;


import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DeliverCallback;

import beijing.bankservice.exception.RequestRejected;
import beijing.bankservice.repository.IAccount;
import beijing.bankservice.repository.IPaymentRepository;

public class PaymentServiceManager {
	
	private static final String RPC_MERCHANTSERVICE_TO_PAYMENTSERVICE_QUEUE = "rpc_merchantservice_to_paymentservice";
	private static final String CUSTOMERSERVICE_TO_PAYMENTSERVICE_QUEUE = "customerservice_to_paymentservice";
	private final static String ACCOUNT_TO_CUSTOMERSERVICE_QUEUE = "account_to_customerservice";
	
	private ConnectionFactory factory;
	private Connection connection;
	private Channel channel;
	private Consumer consumer;
	private IAccount account;
	public static IPaymentRepository paymentRepository;
	
	public PaymentServiceManager(IPaymentRepository _prepository) {
		paymentRepository = _prepository;
		
		 try {
			 
			setupMessageQueue();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
	
	
	private void setupMessageQueue() throws IOException, TimeoutException {
		
		// Connect to RabbitMQ
		factory = new ConnectionFactory();
		factory.setUsername("admin");
		factory.setPassword("Banana");
		factory.setHost("02267-bejing.compute.dtu.dk");
		connection = factory.newConnection();
		channel = connection.createChannel();
		
		setupMerchantRPC();
		sendAccountForCustomerMQ();
		
	}
	
	private void sendAccountForCustomerMQ() throws IOException, TimeoutException {
		
		channel.queueDeclare(ACCOUNT_TO_CUSTOMERSERVICE_QUEUE, false, false, false, null);
		
	}

	
	private void setupMerchantRPC() throws IOException {
		channel.queueDeclare(RPC_MERCHANTSERVICE_TO_PAYMENTSERVICE_QUEUE, false, false, false, null);
		channel.queuePurge(RPC_MERCHANTSERVICE_TO_PAYMENTSERVICE_QUEUE);
		channel.basicQos(1);
		
		 Object monitor = new Object();
         DeliverCallback deliverCallback = (consumerTag, delivery) -> {
             AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                     .Builder()
                     .correlationId(delivery.getProperties().getCorrelationId())
                     .build();

             String response = "";

             try {
            	 String merchantInputMessage = new String(delivery.getBody(),"UTF-8");
            	 String[] transforValues = merchantInputMessage.split(",");
            	 
            	 response = initiateTransfer(transforValues[0],transforValues[1],transforValues[2]);
            	
                 
             } catch (RuntimeException e) {
                 System.out.println(" [.] " + e.toString());
             } finally {
                 channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes("UTF-8"));
                 channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                 // RabbitMq consumer worker thread notifies the RPC server owner thread
                 synchronized (monitor) {
                     monitor.notify();
                 }
             }
		
         };
         
         
         channel.basicConsume(RPC_MERCHANTSERVICE_TO_PAYMENTSERVICE_QUEUE, false, deliverCallback, (consumerTag -> { }));
         // Wait and be prepared to consume the message from RPC client.
         while (true) {
             synchronized (monitor) {
                 try {
                     monitor.wait();
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
         }
	}
	
	public IAccount takeAccount(String cpr) throws RequestRejected, IOException, TimeoutException {
		
		if (paymentRepository.getCustomerAccountByCPR(cpr) != null) {
			throw new RequestRejected("The account for the cpr " + cpr + " already exists");
		} 

		channel.basicPublish("", CUSTOMERSERVICE_TO_PAYMENTSERVICE_QUEUE, null, cpr.getBytes());
		channel.close();
		connection.close();
		
		return paymentRepository.getCustomerAccountByCPR(cpr);
		
	}
	
	public String initiateTransfer(String merchantId, String customerId,String amount) {
		return "";
	}
}
