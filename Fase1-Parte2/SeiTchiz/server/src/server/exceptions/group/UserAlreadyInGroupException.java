package server.exceptions.group;

/**
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class UserAlreadyInGroupException extends GroupException{
	private static final long serialVersionUID = 1L;
	
	public UserAlreadyInGroupException() {
		super("That user is already in this group. ");
	}

}
