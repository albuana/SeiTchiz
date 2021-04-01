package server.exceptions.follow;

/**
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class UserHaveNoFollowersException extends FollowException {
	private static final long serialVersionUID = 1L;
	
	public UserHaveNoFollowersException() {
		super("Sorry, you don't have followers.");
	}

}
