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
import beijing.bankservice.model.Account;
import beijing.bankservice.model.Transaction;
import beijing.bankservice.repository.IPaymentRepository;
import beijing.bankservice.soap.BankService;
import beijing.bankservice.soap.BankServiceServiceLocator;

public class BankServiceManager {
	
	private static final String RPC_MERCHANTSERVICE_TO_PAYMENTSERVICE_QUEUE = "rpc_merchantservice_to_paymentservice";
	
	private final static String PAYMENT_CUSTOMER_REGITRATION = "payment_customer_registration";
	private final static String CUSTOMER_PAYMENT_REGITRATION = "customer_payment_registration";
	
	private final static String RPC_CUSTOMER_PAYMENT_REGITRATION = "rpc_customer_payment_registration";
	private final static String RPC_MERCHANT_PAYMENT_REGITRATION = "rpc_merchant_payment_registration";
	
	private ConnectionFactory factory;
	private Connection connection;
	private Channel channel;
	private Channel channel2;
	private Consumer consumer;
	private Account account;
	public static IPaymentRepository paymentRepository;
	
	private BankService bankService;
	
	public BankServiceManager(IPaymentRepository _prepository) throws IOException, TimeoutException {
		paymentRepository = _prepository;
		
		try {
			bankService = new BankServiceServiceLocator().getBankServicePort();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		setupMessageQueue();	 
	}
	
	
	private void setupMessageQueue() throws IOException, TimeoutException {
		
		// Connect to RabbitMQ
		factory = new ConnectionFactory();
		factory.setUsername("admin");
		factory.setPassword("Banana");
		factory.setHost("02267-bejing.compute.dtu.dk");
		connection = factory.newConnection();
		channel = connection.createChannel();
//		channel2 = connection.createChannel();
//		setupMerchantRPC();
		setupCustomerVerification();
//		setupSignupRPCChannel(RPC_CUSTOMER_PAYMENT_REGITRATION);
//		setupSignupRPCChannel(RPC_MERCHANT_PAYMENT_REGITRATION);
//		
	}
	
	
	private void setupCustomerVerification() throws IOException {
		channel.queueDeclare(PAYMENT_CUSTOMER_REGITRATION, false, false, false, null);

//		Listen for customer service sends details to verify account
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody());
			String[] verificationValues = message.split(",");
			
			String customerId = verificationValues[0];
			String customerCPR = verificationValues[1];
			
			Account a = bankService.getAccountByCprNumber(customerCPR);
			if(a != null) {
				a.setDtuId(customerId);
				paymentRepository.createAccount(a);
				channel.basicPublish("", PAYMENT_CUSTOMER_REGITRATION, null,"VERIFIED".getBytes() );
			}else {
				channel.basicPublish("", PAYMENT_CUSTOMER_REGITRATION, null,"UNVERIFIED".getBytes() );
			}
			
		};
		channel.basicConsume(CUSTOMER_PAYMENT_REGITRATION, true, deliverCallback, consumerTag -> {
		});		
	}


//	private void setupSignupRPCChannel(String channelRpc)throws IOException {
//		channel.queueDeclare(channelRpc, false, false, false, null);
//		channel.queuePurge(channelRpc);
//		channel.basicQos(1);
//		
//		Object monitor = new Object();
//        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//            AMQP.BasicProperties replyProps = new AMQP.BasicProperties
//                    .Builder()
//                    .correlationId(delivery.getProperties().getCorrelationId())
//                    .build();
//
//            String response = "";
//
//            try {
//           	 
//           	 String merchantInputMessage = new String(delivery.getBody(),"UTF-8");
//           	 String[] transferValues = merchantInputMessage.split(",");
//           	 
//           	 String cpr = transferValues[0];
//           	 String  dtuId = transferValues[1];
//           	 
//           	 boolean status = verifyAccount(cpr, dtuId);
//           	 
//           	 response = status?"VERIFIED":"NOT VERIFIED";
//           	
//                
//            } catch (RuntimeException e) {
//                System.out.println(" [.] " + e.toString());
//            } finally {
//                channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes("UTF-8"));
//                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
//                // RabbitMq consumer worker thread notifies the RPC server owner thread
//                synchronized (monitor) {
//                    monitor.notify();
//                }
//            }
//		
//        };
//        
//        channel.basicConsume(channelRpc, false, deliverCallback, (consumerTag -> { }));
//        // Wait and be prepared to consume the message from RPC client.
//        while (true) {
//            synchronized (monitor) {
//                try {
//                    monitor.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//	}

	
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

	
	public boolean verifyAccount(String cpr,String dtuId)  {
		boolean status = true;
		
		if (paymentRepository.getCustomerAccountByCPR(cpr) != null) {
			status = false;
		} 
		if(status) {
			Account a = null;
			try {
				a = bankService.getAccountByCprNumber(cpr);
				a.setDtuId(dtuId);
				paymentRepository.createAccount(a);
			} catch (BankServiceException e1) {
				status = false;
				e1.printStackTrace();
			} catch (RemoteException e1) {
				status = false;
				e1.printStackTrace();
			}
		}
		return status;	
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
