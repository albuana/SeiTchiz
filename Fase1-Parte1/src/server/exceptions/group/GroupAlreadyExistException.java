package server.exceptions.group;

/**
 * Exception for when creating a group  occurs an error probably related to group is already in use
 */
public class GroupAlreadyExistException extends GroupException{
	private static final long serialVersionUID = 1L;

	public GroupAlreadyExistException() {
		super("The group could not be created. This could be due to the fact that the you're probably using a groupID that already exists. ");
	}
	

}
