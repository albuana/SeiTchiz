package server.exceptions.group;

/**
 * REVISTA
 * Exception for when a owner tries to add a user that is already part of the group
 */
public class UserAlreadyInGroupException extends GroupException{
	private static final long serialVersionUID = 1L;
	
	public UserAlreadyInGroupException() {
		super("This user is already in this group ");
	}

}
