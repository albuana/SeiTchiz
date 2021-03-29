package server.exceptions.group;

/**
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class UserDoesNotInGroupException extends GroupException{
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	public UserDoesNotInGroupException() {
		super("The user is not in the group therefore you can't remove him.");
	}
}
