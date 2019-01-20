package beijing.bankservice.exception;

public class RequestRejected extends Exception{
	
	private static final long serialVersionUID = 1L;

	public RequestRejected(String message) {
        super(message);
    }
	
    public RequestRejected(Throwable cause) {
        super(cause);
    }

    public RequestRejected(String message, Throwable cause) {
        super(message, cause);
    }
}
