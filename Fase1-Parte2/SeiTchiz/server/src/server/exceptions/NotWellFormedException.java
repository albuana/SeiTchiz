package server.exceptions;

/**
 * Exception for when a command is baddly called
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class NotWellFormedException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public NotWellFormedException() {
		super("Check again how this command is called");
	}

}
