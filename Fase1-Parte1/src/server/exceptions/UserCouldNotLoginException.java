package server.exceptions;

public class UserCouldNotLoginException extends Exception{
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	public UserCouldNotLoginException() {
		super("The user could not login, password could be wrong, try again");
	}
}