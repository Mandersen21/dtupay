package beijing.paymentservice.domain;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DeliverCallback;

public class PaymentServiceManager {
	
	private static final String RPC_MERCHANTSERVICE_TO_PAYMENTSERVICE_QUEUE = "rpc_merchantservice_to_paymentservice";
	
	private ConnectionFactory factory;
	private Connection connection;
	private Channel channel;
	private Consumer consumer;

	
	
	private void setupMessageQueue() throws IOException, TimeoutException {
		factory = new ConnectionFactory();
		factory.setUsername("admin");
		factory.setPassword("Banana");
		factory.setHost("02267-bejing.compute.dtu.dk");
		connection = factory.newConnection();
		channel = connection.createChannel();
		
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
            	 
            	 response = "helloWorld i hate you!";
            	 
//                 String message = new String(delivery.getBody(), "UTF-8");
//                 int n = Integer.parseInt(message);
//
//                 System.out.println(" [.] fib(" + message + ")");
//                 response += fib(n);
                 
                 
                 
                 
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
	
	

}
