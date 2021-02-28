package server.exceptions;

/**
 * 
 * Exception for when a user does not exist for example when adding or removing a user from a group
 */
public class UserNotExistException extends Exception{
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	public UserNotExistException() {
		super("The user does not exist. Check if you wrote the correct userID.");
	}

}
