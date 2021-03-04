package server.exceptions.group;

/**
 * Exception related to group functionality
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class GroupException extends Exception{

	private static final long serialVersionUID = 1L;

	public GroupException(String message) {
		super(message + "\n");
	}
}
