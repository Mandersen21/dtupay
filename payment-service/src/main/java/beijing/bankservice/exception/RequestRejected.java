package beijing.bankservice.exception;

public class RequestRejected extends Exception{


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
