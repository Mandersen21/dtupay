package beijing.customerservice.exception;


/**
 * 
 * ConnectionException is raised whenever the connection can not be established.
 * @author BotezatuCristian
 *
 */

public class ConnectionException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConnectionException(String message) {
        super(message);
    }
	
    public ConnectionException(Throwable cause) {
        super(cause);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
