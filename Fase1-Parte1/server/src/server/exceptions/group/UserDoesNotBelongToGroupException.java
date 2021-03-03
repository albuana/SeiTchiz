package server.exceptions.group;

public class UserDoesNotBelongToGroupException extends GroupException{
	private static final long serialVersionUID = 1L;

	public UserDoesNotBelongToGroupException() {
		super("The user does not belong to this group therefore he cannot acess to this information.");
	}
}