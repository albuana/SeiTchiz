package server.exceptions.group;

public class NothingToReadException extends GroupException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	public NothingToReadException() {
		super("Sorry, you have no new messages.");
	}

}
