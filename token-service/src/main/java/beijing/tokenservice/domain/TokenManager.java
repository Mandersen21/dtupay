package beijing.tokenservice.domain;

import beijing.tokenservice.exception.CustomerNotFoundException;

import beijing.tokenservice.exception.DataAccessException;
import beijing.tokenservice.exception.RequestRejected;
import beijing.tokenservice.exception.TokenNotFoundException;
import beijing.tokenservice.repository.ICustomerRepository;
import beijing.tokenservice.repository.ITokenRepository;
import org.apache.commons.lang3.StringUtils;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class TokenManager {

	private final static String CUSTOMERID_TO_TOKENSERVICE_QUEUE = "customerid_to_tokenservice";
	private final static String TOKENID_TO_MERCHANTSERVICE_QUEUE = "tokenid_to_merchantservice";
	private final static String MERCHANTSERVICE_TO_TOKENID_QUEUE = "merchantservice_to_tokenid";

	public static ITokenRepository tokenRepository;
	public static ICustomerRepository customerRepository;

	private final int tokenLength = 6;
	private Token token;

	private List<TokenRepresentation> tokens;

	private ConnectionFactory factory;
	private Connection connection;
	private Channel channel;

	public TokenManager(ITokenRepository _tRepository, ICustomerRepository _cRepository)
			throws IOException, TimeoutException {
		tokenRepository = _tRepository;
		customerRepository = _cRepository;

		// Cucumber user story 1
		Token token = new Token("123", "123456", true, Status.ACTIVE, null);
		customerRepository.addCustomer("123456");
		tokenRepository.createToken(token);

		setupMessageQueue();
	}

	public List<TokenRepresentation> requestToken(String customerId, int tokenAmount) throws RequestRejected,
			TokenNotFoundException, DataAccessException, IOException, TimeoutException, CustomerNotFoundException {
		List<Token> t = new ArrayList<Token>();
		tokens = new ArrayList<TokenRepresentation>();

		if (tokenAmount >= 1 && tokenAmount <= 5) {
			if (customerRepository.getCustomer(customerId) == null) {
				throw new CustomerNotFoundException("No customer with customerId is created, request rejected");
			}
			t = tokenRepository.getTokensForCustomerId(customerId);
		} else {
			throw new RequestRejected("Your request was rejected due to requesting less than 1 or more than 5 tokens");
		}

		System.out.println("Number of tokens on customer: " + t.size());
		
		
		if (t.size() > 1) {
			throw new RequestRejected("Your request was rejected due to having more than one valid token left");
		}

		// Generate tokens
		for (int i = 0; i < tokenAmount; i++) {
			boolean unique = false;

			String tokenId = "";
			Token checkToken;

			while (!unique) {
				tokenId = generateRandomTokenNumber(tokenLength);
				checkToken = tokenRepository.getToken(tokenId);
				if (checkToken == null) {
					unique = true;
				}
			}

			Token token = new Token(tokenId, customerId, true, Status.ACTIVE, "");
			token.setPath(tokenId + ".png");
			tokenRepository.createToken(token);
			tokens.add(new TokenRepresentation(tokenId, token.getPath()));

			String message = token.getTokenId() + "," + token.getCustomerId() + "," + token.getValidationStatus();
			channel.basicPublish("", TOKENID_TO_MERCHANTSERVICE_QUEUE, null, message.getBytes());
		}
		channel.close();
		connection.close();
		return tokens;
	}

	public boolean isTokenValid(String tokenId) throws TokenNotFoundException {
		token = tokenRepository.getToken(tokenId);
		if (token == null) {
			throw new TokenNotFoundException("Could not find the token based on tokenId");
		}
		return token.getValidationStatus() == true;
	}

	public List<Token> getAllTokens(String customerId) {
		List<Token> list;
		list = tokenRepository.getTokens();

		if (customerId != null) {
			return list.stream().filter(t -> t.getCustomerId().contentEquals(customerId)).collect(Collectors.toList());
		} else {
			return list;
		}
	}

	public boolean isTokenInvalid(String tokenId) {
		token = tokenRepository.getToken(tokenId);
		return token.getValidationStatus() == false;
	}

	public Token getToken(String tokenId) throws TokenNotFoundException {
		Token token = tokenRepository.getToken(tokenId);
		if (token == null) {
			throw new TokenNotFoundException("Token not found");
		}
		return token;
	}

	public boolean updateToken(String tokenId, Boolean validationStatus, Status status) throws TokenNotFoundException {
		boolean response;
		try {
			token = tokenRepository.getToken(tokenId);

			if (token != null) {
				token.setStatus(status);
				token.setValidtionStatus(validationStatus);
				response = tokenRepository.updateToken(token);

			} else {
				throw new TokenNotFoundException("Could not find a token with that tokenId");
			}

			if (response == true) {
				return true;
			}
		} catch (Exception e) {
			throw new TokenNotFoundException("Could not find the token in the database");
		}

		return false;
	}

	private void setupMessageQueue() throws IOException, TimeoutException {
		// Connect to RabbitMQ
		factory = new ConnectionFactory();
		factory.setUsername("admin");
		factory.setPassword("Banana");

		factory.setHost("02267-bejing.compute.dtu.dk");

		connection = factory.newConnection();
		channel = connection.createChannel();
		channel.queueDeclare(TOKENID_TO_MERCHANTSERVICE_QUEUE, false, false, false, null);

		// Listen for customer service
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String customerId = new String(delivery.getBody(), "UTF-8");
			System.out.println("Message received: " + customerId);
			customerRepository.addCustomer(customerId);
		};
		channel.basicConsume(CUSTOMERID_TO_TOKENSERVICE_QUEUE, true, deliverCallback, consumerTag -> {
		});

		// Listen for merchant service
		DeliverCallback deliverCallbackMerchant = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			String[] resultSplit = message.split(",");

			String id = resultSplit[0];
			boolean valid = resultSplit[1] == "PAID" || resultSplit[1] == "INVALID" ? false : true;
			Status status = Status.ACTIVE;
			if (resultSplit[1] == "PAID") {
				status = Status.PAID;
			}
			if (resultSplit[1] == "INVALID") {
				status = Status.INVALID;
			}
			if (resultSplit[1] == "PENDING") {
				status = Status.PENDING;
			}

			try {
				updateToken(id, valid, status);
			} catch (TokenNotFoundException e) {

			}
		};
		channel.basicConsume(MERCHANTSERVICE_TO_TOKENID_QUEUE, true, deliverCallbackMerchant, consumerTag -> {
		});
	}

	public String generateRandomTokenNumber(int length) {
		int m = (int) Math.pow(10, length - 1);
		return Integer.toString(m + new Random().nextInt(9 * m));
	}

//	public File generateToken(String msg, String path) {
//		File file = new File(path);
//		try {
//			generate(msg, new FileOutputStream(file));
//		} catch (FileNotFoundException e) {
//			throw new RuntimeException(e);
//		}
//		return file;
//	}
//
//	public byte[] generate(String msg) {
//		ByteArrayOutputStream ous = new ByteArrayOutputStream();
//		generate(msg, ous);
//		return ous.toByteArray();
//	}
//
//	public void generate(String msg, OutputStream ous) {
//		if (StringUtils.isEmpty(msg) || ous == null) {
//			return;
//		}
//
//		Code39Bean bean = new Code39Bean();
//
//		// accuracy
//		final int dpi = 150;
//		// module width
//		final double moduleWidth = UnitConv.in2mm(1.0f / dpi);
//
//		// configuration object
//		bean.setModuleWidth(moduleWidth);
//		bean.setWideFactor(3);
//		bean.doQuietZone(false);
//
//		String format = "image/png";
//
//		try {
//
//			// output to the stream
//			BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi, BufferedImage.TYPE_BYTE_BINARY,
//					false, 0);
//
//			// Generate a bar-code
//			bean.generateBarcode(canvas, msg);
//
//			// end drawing
//			canvas.finish();
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//	}

	public boolean deleteTokens(String customerId) {
		return tokenRepository.deleteTokens(customerId);
	}

}
