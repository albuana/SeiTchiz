package server.exceptions;

/**
 * Exception when user can't login in system
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 *
 */
public class UserCouldNotLoginException extends Exception{
	private static final long serialVersionUID = 1L;
	 
	public UserCouldNotLoginException() {
		super("The user could not login, password could be wrong, try again.");
	}
}