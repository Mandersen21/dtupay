package beijing.customerservice.exception;

/**
 * 
 * DataAccessException is raised the there is no acces to the desired data.
 * @author BotezatuCristian
 *
 */

public class DataAccessException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}