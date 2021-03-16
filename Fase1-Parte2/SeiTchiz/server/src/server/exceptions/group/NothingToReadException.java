package server.exceptions.group;

/**
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class NothingToReadException extends GroupException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	public NothingToReadException() {
		super("Sorry, you have no new messages.");
	}

}
