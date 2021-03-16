package server.exceptions.follow;

/**
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class UserAlreadyBeingFollowedException extends FollowException {
	private static final long serialVersionUID = 1L;
	
	public UserAlreadyBeingFollowedException() {
		super("You had follow that user before.");
	}
}