package server.exceptions.post;

public class NoPostExeption extends Exception {
	private static final long serialVersionUID = 1L;
	
	public NoPostExeption() {
		super("Sorry, you can't like a photo that doesn't exist.\n");
	}

}
