package server.exceptions.group;

/**
 * 
 * Exception related to group functionality
 */
public class GroupException extends Exception{

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	public GroupException(String message) {
		super(message);
	}
}
