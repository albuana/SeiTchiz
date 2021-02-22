package server.exceptions.group;

/**
 * REVISTA
 * Exception for when a group id is used but the group does not exist
 */
public class GroupNotExistException extends GroupException{

	private static final long serialVersionUID = 1L;

	public GroupNotExistException() {
		super("This groups does not exist. Check if you wrote the correct groupID or create a new group ");
	}

}
