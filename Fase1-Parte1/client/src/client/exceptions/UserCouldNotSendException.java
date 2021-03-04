package client.exceptions;

/**
 * Exception for when there's a problem related with a server connection
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class UserCouldNotSendException extends Exception {
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	public UserCouldNotSendException(String message) {
		super("Something went wrong with your connexion to the server.\n " + message);
	}
}
