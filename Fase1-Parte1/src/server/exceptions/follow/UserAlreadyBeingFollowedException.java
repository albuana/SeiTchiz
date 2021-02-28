package server.exceptions.follow;

public class UserAlreadyBeingFollowedException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public UserAlreadyBeingFollowedException() {
		super("You had follow that user before.");
	}
}