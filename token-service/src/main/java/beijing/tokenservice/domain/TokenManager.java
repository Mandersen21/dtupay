package beijing.tokenservice.domain;

import beijing.tokenservice.exception.DataAccessException;
import beijing.tokenservice.exception.RequestRejected;
import beijing.tokenservice.exception.TokenNotFoundException;
import beijing.tokenservice.repository.ITokenRepository;
import beijing.tokenservice.repository.TokenRepository;
import org.apache.commons.lang3.StringUtils;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TokenManager {

    public static ITokenRepository repository;
    private final int tokenLength = 6;
    private final String tokenPath = "tokens/";
    private Token token;

    private List<Token> tokens;

    public TokenManager(ITokenRepository _repository) {
        repository = _repository;
    }

    public List<Token> requestToken(String customerId, int tokenAmount) throws RequestRejected, TokenNotFoundException, DataAccessException {
        tokens = new ArrayList<Token>();

        if (tokenAmount >= 1 && tokenAmount <= 5) {
            try {
                //call customer service to verify customerId
            } catch (Exception e) {
                throw new RequestRejected("RENAME");
            }
            tokens = repository.getTokensForCustomerId(customerId);
        } else {
            throw new RequestRejected("Your request was rejected due to requesting less than 1 or more than 5 tokens");
        }

        if (tokens.size() > 1) {
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
            Token token = new Token(tokenId, customerId, true, Status.ACTIVE);

            try {
                repository.createToken(token);
                tokens.add(token);
            } catch (Exception e) {
                throw new DataAccessException("Could not add Token to Database");
            }
        }
        return tokens;
    }

    public boolean isTokenValid(String tokenId) throws TokenNotFoundException {
        token = repository.getToken(tokenId);
        if (token == null) {
            throw new TokenNotFoundException("Could not find the token in the database");
        }
        return token.getValidationStatus() == true;
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
            // Get token
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
