package server.exceptions.follow;

/**
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class UserCantFollowHimselfException extends FollowException {
	private static final long serialVersionUID = 1L;
	
	public UserCantFollowHimselfException() {
		super("The user can´t follow himself.");
	}
}