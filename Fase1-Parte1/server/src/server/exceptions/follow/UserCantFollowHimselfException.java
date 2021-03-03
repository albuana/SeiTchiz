package server.exceptions.follow;

public class UserCantFollowHimselfException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public UserCantFollowHimselfException() {
		super("The user can´t follow himself.");
	}
}