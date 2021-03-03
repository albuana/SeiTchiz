package server.exceptions.post;

public class HaveNoPhotosExeption extends Exception {
	private static final long serialVersionUID = 1L;
	
	public HaveNoPhotosExeption() {
		super("There is no photos to show.");
	}

}
