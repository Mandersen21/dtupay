package beijing.tokenservice.domain;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import beijing.tokenservice.exception.DataAccessException;
import beijing.tokenservice.exception.RequestRejected;
import beijing.tokenservice.exception.TokenNotFoundException;
import beijing.tokenservice.repository.DataAccessRepository;

public class TokenManager {

	public static DataAccessRepository dataAccess;
	private final int length = 6;
	private final String path = "tokens/";
	private Customer customer;
	private Token token;

	private List<Token> tokens;

	public TokenManager() {
		//dataAccess = new TestDatabase();
	}

	public List<Token> requestToken(String customerId, String customerName, int tokensRequested)
			throws RequestRejected, TokenNotFoundException, DataAccessException {
		tokens = new ArrayList<Token>();
		Customer c;
		if (tokensRequested >= 1 && tokensRequested <= 5) {
			try {
				c = dataAccess.getCustomer(customerId);
			} catch (Exception e) {
				throw new RequestRejected("Getting customer failed");
			}
			if (c == null) {
				c = new Customer(customerName, UUID.randomUUID().toString());
				try {
					dataAccess.createCustomer(c);
				} catch (Exception e) {
					System.out.print(e);
				}
			} else {
				tokens = dataAccess.getTokensForCustId(c.getUniqueId());
			}
		} else {
			throw new RequestRejected("Your request was rejected due to requesting less than 1 or more than 5 tokens");
		}
		
		if (tokens.size() > 1) {
			throw new RequestRejected("Your request was rejected due to having more than one valid token left");
		}

		// Generate tokens
		for (int i = 0; i < tokensRequested; i++) {
			boolean alreadyUsedToken = false;
			boolean unique = false;
			String tokenId = "";
			Token checkToken;
			while (!unique) {
				tokenId = generateRandomTokenNumber(length);
				checkToken = dataAccess.getToken(tokenId);
				if (checkToken == null) {
					unique = true;
				}
			}
			Token t = new Token(tokenId, c.getUniqueId(), true);
			try {
				dataAccess.createToken(t);
				tokens.add(t);
			} catch (Exception e) {
				throw new DataAccessException("Could not add Token to Database");
			}
			generateToken(tokenId, path + tokenId + ".png");
		}

		return tokens;
	}

	public boolean isTokenValid(String tokenid) throws TokenNotFoundException {
		token = dataAccess.getToken(tokenid);
		if (token == null) {
			throw new TokenNotFoundException("Could not find the token in the database");
		}
		if (token.isValid() == true) {
			return true;
		}
		return false;
	}

	public boolean isTokenInvalid(String tokenid) {
		token = dataAccess.getToken(tokenid);

		if (token.isValid() == false) {
			return true;
		}
		return false;
	}

	public boolean useToken(String tokenid) throws TokenNotFoundException {
		int response = 0;
		try {
			if (isTokenValid(tokenid)) {
				response = dataAccess.useToken(tokenid); // Token can be used, as it is valid
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new TokenNotFoundException("Could not find the token in the database");
		}
		
			if (response == 1) {
				return true;
			} else {
				return false;
			}
	}

	public boolean createToken(Token token) throws TokenNotFoundException {
		int response = 0;

		try {
			response = dataAccess.createToken(token); // Token can be created, as it is new
		} catch (Exception e) {
			throw new TokenNotFoundException("The token has already been created");
		}

		if (response == 1) {
			return true;
		} else {
			return false;
		}
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
