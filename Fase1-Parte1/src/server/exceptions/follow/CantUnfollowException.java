package server.exceptions.follow;

public class CantUnfollowException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public CantUnfollowException() {
		super("Sorry, you can't unfollow an user that you didn't follow before.");
	}

}
