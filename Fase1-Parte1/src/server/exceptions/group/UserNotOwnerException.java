package server.exceptions.group;

/**
 * 
 * Exception for when a user tries to perform an action that it is only available to a group owner 
 */
public class UserNotOwnerException extends GroupException{
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	public UserNotOwnerException() {
		super("Unfortunately you cannot execute this action, you're not the owner of this group");
	}

}
