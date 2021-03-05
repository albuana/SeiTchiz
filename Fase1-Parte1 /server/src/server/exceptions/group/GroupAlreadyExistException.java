package server.exceptions.group;

/**
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class GroupAlreadyExistException extends GroupException{
	private static final long serialVersionUID = 1L;

	public GroupAlreadyExistException() {
		super("The group could not be created. This could be due to the fact that the you're probably using a groupID that already exists.");
	}
	

}
