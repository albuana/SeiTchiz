package server.exceptions.group;

/**
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class UserDoesNotBelongToGroupException extends GroupException{
	private static final long serialVersionUID = 1L;

	public UserDoesNotBelongToGroupException() {
		super("The user does not belong to this group therefore he cannot acess to this information.");
	}
}