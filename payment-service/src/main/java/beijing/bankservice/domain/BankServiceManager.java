package beijing.bankservice.domain;

import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.concurrent.TimeoutException;

import javax.xml.rpc.ServiceException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DeliverCallback;

import beijing.bankservice.exception.BankServiceException;
import beijing.bankservice.exception.RequestRejected;
import beijing.bankservice.model.Account;
import beijing.bankservice.model.Transaction;
import beijing.bankservice.repository.IPaymentRepository;
import beijing.bankservice.soap.BankService;
import beijing.bankservice.soap.BankServiceServiceLocator;

public class BankServiceManager {
	
	private static final String RPC_MERCHANTSERVICE_TO_PAYMENTSERVICE_QUEUE = "rpc_merchantservice_to_paymentservice";
	private static final String CUSTOMERSERVICE_TO_PAYMENTSERVICE_QUEUE = "customerservice_to_paymentservice";
	private final static String ACCOUNT_TO_CUSTOMERSERVICE_QUEUE = "account_to_customerservice";
	
	private ConnectionFactory factory;
	private Connection connection;
	private Channel channel;
	private Consumer consumer;
	private Account account;
	public static IPaymentRepository paymentRepository;
	
	private BankService bankService;
	
	public BankServiceManager(IPaymentRepository _prepository) {
		paymentRepository = _prepository;
		
		
		
		 try {
			bankService = new BankServiceServiceLocator().getBankServicePort();
			setupMessageQueue();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
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
            	 String[] transferValues = merchantInputMessage.split(",");
            	 
            	 response = initiateTransfer(transferValues[0],transferValues[1],transferValues[2],"DTUPay Service");
            	
                 
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

	
	public Account verifyAccount(String cpr) throws RequestRejected, IOException, TimeoutException {
		
		if (paymentRepository.getCustomerAccountByCPR(cpr) != null) {
			throw new RequestRejected("The account for the cpr " + cpr + " already exists");
		} 
		Account a = bankService.getAccountByCprNumber(cpr);
		
		
		channel.basicPublish("", CUSTOMERSERVICE_TO_PAYMENTSERVICE_QUEUE, null, cpr.getBytes());
		channel.close();
		connection.close();
		
		return paymentRepository.getCustomerAccountByCPR(cpr);
		
	}
	
	public String initiateTransfer(String merchantId, String customerId,String amount, String description) throws BankServiceException, RemoteException {
		// get Account finds the account based on the id given by the DTU Pay service
		Account merchant = paymentRepository.getAccount(merchantId); 
		Account customer = paymentRepository.getAccount(customerId);
		
		Transaction t = new Transaction();
		t.setAmount(new BigDecimal(amount));
		t.setCreditor(merchantId);
		t.setDebtor(customerId);
		t.setDescription(description);
		t.setTime(Calendar.getInstance());
			
			// getid refers to the account id which is given by the bank 
		bankService.transferMoneyFromTo(customer.getId(), merchant.getId(), new BigDecimal(amount), description);
		
		paymentRepository.storeTransaction(t);
		
		return "Transfer completed";
	}
}
