package server.exceptions;

/**
 * Exception for when a command is baddly called
 */
public class NotWellFormedException extends Exception{
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	public NotWellFormedException() {
		super("Check again how this command is called");
	}

}
