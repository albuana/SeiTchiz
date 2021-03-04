package server.exceptions.group;

public class CantRemoveOwnerExeption extends GroupException {
	private static final long serialVersionUID = 1L;

	public CantRemoveOwnerExeption() {
		super("The owner can't be removed.");
	}
	

}