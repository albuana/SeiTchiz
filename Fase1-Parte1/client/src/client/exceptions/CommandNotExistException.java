package client.exceptions;

/**
 * Exception for when there's a bad command input
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class CommandNotExistException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	public CommandNotExistException() {
		super("This command does not exist. Try another one");
	}
	
}
