package server.exceptions.group;

/**
 * REVISTA
 * Exception for when a user tries to perform an action that it is only available to a group owner 
 */
public class UserNotOwnerException extends GroupException{
	private static final long serialVersionUID = 1L;
	
	public UserNotOwnerException() {
		super("Unfortunately you cannot execute this action, you're not the owner of this group");
	}

}
