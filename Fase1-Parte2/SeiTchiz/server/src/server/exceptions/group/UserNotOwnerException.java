package server.exceptions.group;

/**
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class UserNotOwnerException extends GroupException{
	private static final long serialVersionUID = 1L;
	
	public UserNotOwnerException() {
		super("Unfortunately you cannot execute this action, you're not the owner of this group.");
	}

}
