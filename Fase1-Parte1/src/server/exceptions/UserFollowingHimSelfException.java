package server.exceptions;

public class UserFollowingHimSelfException extends Exception {
	
	public UserFollowingHimSelfException() {
		super("The User tried to follow him self");
	}
}
