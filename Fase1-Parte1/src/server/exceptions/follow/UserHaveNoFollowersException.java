package server.exceptions.follow;

public class UserHaveNoFollowersException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public UserHaveNoFollowersException() {
		super("That user don't have followers.");
	}

}
