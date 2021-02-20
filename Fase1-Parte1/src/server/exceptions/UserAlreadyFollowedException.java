package server.exceptions;

public class UserAlreadyFollowedException extends Exception {
	
	public UserAlreadyFollowedException() {
		super("The user is already being followed");
	}
}
