package beijing.tokenservice.domain;

import beijing.tokenservice.exception.DataAccessException;
import beijing.tokenservice.exception.RequestRejected;
import beijing.tokenservice.exception.TokenNotFoundException;
import beijing.tokenservice.repository.ICustomerRepository;
import beijing.tokenservice.repository.ITokenRepository;
import beijing.tokenservice.repository.TokenRepository;
import org.apache.commons.lang3.StringUtils;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Envelope;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class TokenManager {

	private final static String CUSTOMERID_TO_TOKENSERVICE_QUEUE = "customerid_to_tokenservice";
	private final static String TOKENID_TO_MERCHANTSERVICE_QUEUE = "tokenid_to_merchantservice";

	public static ITokenRepository repository;

	private final int tokenLength = 6;
	private Token token;
	private final String tokenPath = "tokens/";

	private List<TokenRepresentation> tokens;

	private ConnectionFactory factory;
	private Connection connection;
	private Channel channel;
	private Consumer consumer;

	public TokenManager(ITokenRepository _repository) throws IOException, TimeoutException {
		repository = _repository;

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
			repository.addCustomer(customerId);

		};
		channel.basicConsume(CUSTOMERID_TO_TOKENSERVICE_QUEUE, true, deliverCallback, consumerTag -> {
		});
	}

	public List<TokenRepresentation> requestToken(String customerId, int tokenAmount)
			throws RequestRejected, TokenNotFoundException, DataAccessException, IOException, TimeoutException {
		List<Token> t = new ArrayList<Token>();
		tokens = new ArrayList<TokenRepresentation>();

		if (tokenAmount >= 1 && tokenAmount <= 5) {
			if (repository.getCustomer(customerId) == null) {
				throw new RequestRejected("No customer with customerId is created, request rejected");
			}
			t = repository.getTokensForCustomerId(customerId);
		} else {
			throw new RequestRejected("Your request was rejected due to requesting less than 1 or more than 5 tokens");
		}

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
				checkToken = repository.getToken(tokenId);
				if (checkToken == null) {
					unique = true;
				}
			}
			
			try {
				Token token = new Token(tokenId, customerId, true, Status.ACTIVE, "");
				File path = generateToken(token.getTokenId(), tokenPath + tokenId + ".png");
				System.out.println("Token created: " + path.toString());
				token.setPath(path.toString());
				repository.createToken(token);
				tokens.add(new TokenRepresentation(token.getTokenId(), token.getPath()));

				String message = token.getTokenId() + "," + token.getCustomerId() + "," + token.getValidationStatus();
				channel.basicPublish("", TOKENID_TO_MERCHANTSERVICE_QUEUE, null, message.getBytes());

			} catch (Exception e) {
				throw new DataAccessException("Token could not be created");
			}
		}
		channel.close();
		connection.close();
		return tokens;
	}

	public boolean isTokenValid(String tokenId) throws TokenNotFoundException {
		token = repository.getToken(tokenId);
		if (token == null) {
			throw new TokenNotFoundException("Could not find the token based on tokenId");
		}
		return token.getValidationStatus() == true;
	}

	public List<TokenRepresentation> getAllTokens() {
		List<Token> list;
		List<TokenRepresentation> tokens = null;
		list = repository.getTokens();
		for (Token t : list) {
			tokens.add(new TokenRepresentation(t.getTokenId(), t.getPath()));
		}
		return tokens;
	}

	public boolean isTokenInvalid(String tokenId) {
		token = repository.getToken(tokenId);
		return token.getValidationStatus() == false;
	}

	public Token getToken(String tokenId) throws TokenNotFoundException {
		Token token = repository.getToken(tokenId);
		if (token == null) {
			throw new TokenNotFoundException("Token not found");
		}
		return token;
	}

	public boolean updateToken(String tokenId, Boolean validationStatus, Status status) throws TokenNotFoundException {
		boolean response;
		try {
			token = repository.getToken(tokenId);

			if (token != null) {
				token.setStatus(status);
				token.setValidtionStatus(validationStatus);

				response = repository.updateToken(token);

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

	public String generateRandomTokenNumber(int length) {
		int m = (int) Math.pow(10, length - 1);
		return Integer.toString(m + new Random().nextInt(9 * m));
	}

    public File generateToken(String msg, String path) {
        File file = new File(path);
        try {
            generate(msg, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    public byte[] generate(String msg) {
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        generate(msg, ous);
        return ous.toByteArray();
    }

    public void generate(String msg, OutputStream ous) {
        if (StringUtils.isEmpty(msg) || ous == null) {
            return;
        }

        Code39Bean bean = new Code39Bean();

        // accuracy
        final int dpi = 150;
        // module width
        final double moduleWidth = UnitConv.in2mm(1.0f / dpi);

        // configuration object
        bean.setModuleWidth(moduleWidth);
        bean.setWideFactor(3);
        bean.doQuietZone(false);

        String format = "image/png";

        try {

            // output to the stream
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi, BufferedImage.TYPE_BYTE_BINARY,
                    false, 0);

            // Generate a bar-code
            bean.generateBarcode(canvas, msg);

            // end drawing
            canvas.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
