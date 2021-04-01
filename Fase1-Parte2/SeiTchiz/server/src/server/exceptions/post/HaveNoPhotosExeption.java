package server.exceptions.post;

/**
 * 
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 *
 */
public class HaveNoPhotosExeption extends Exception {
	private static final long serialVersionUID = 1L;
	
	public HaveNoPhotosExeption() {
		super("There is no photos to show.\n");
	}

}
