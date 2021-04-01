package server.exceptions.follow;

/**
 * Exception related to follow functionality
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class FollowException extends Exception{

	private static final long serialVersionUID = 1L;

	public FollowException(String message) {
		super(message + "\n");
	}
}