package beijing.tokenservice.exception;

public class TokenNotFoundException extends Exception {
	
	public TokenNotFoundException(String message) {
        super(message);
    }

    public TokenNotFoundException(Throwable cause) {
        super(cause);
    }

    public TokenNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}