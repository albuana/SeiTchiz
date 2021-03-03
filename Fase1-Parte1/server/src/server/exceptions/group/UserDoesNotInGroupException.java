package server.exceptions.group;

/**
 * 
 * Exception for when a user tries to send a message to a group it does not belong to 
 */
public class UserDoesNotInGroupException extends GroupException{
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	public UserDoesNotInGroupException() {
		super("The user is not in the group therefore you can't remove him.");
	}
}
