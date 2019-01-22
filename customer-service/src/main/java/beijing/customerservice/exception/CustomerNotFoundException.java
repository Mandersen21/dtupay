package beijing.customerservice.exception;


/**
 * 
 * CustomerNotFoundException is raised whenever a customer is not found (either to get or remove it from the customerRepository)
 * @author BotezatuCristian
 *
 */

public class CustomerNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(Throwable cause) {
        super(cause);
    }

    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}