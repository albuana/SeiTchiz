package server.exceptions.group;

/**
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class GroupNotExistException extends GroupException{

	private static final long serialVersionUID = 1L;

	public GroupNotExistException() {
		super("That group does not exist. Check if you wrote the correct groupID or create a new group. ");
	}

}
