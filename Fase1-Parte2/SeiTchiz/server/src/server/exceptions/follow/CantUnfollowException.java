package server.exceptions.follow;

/**
 * Exception when the user cant unfollow a user that dont follow beffore
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 *
 */
public class CantUnfollowException extends FollowException {
	private static final long serialVersionUID = 1L;
	
	public CantUnfollowException() {
		super("Sorry, you can't unfollow an user that you didn't follow before.");
	}

}
