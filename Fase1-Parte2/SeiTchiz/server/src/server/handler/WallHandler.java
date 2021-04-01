package server.handler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import server.domain.Post;
import server.domain.User;
import server.exceptions.post.HaveNoPhotosExeption;

/**
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class WallHandler {

	private int nPhotos;
	private User username;

	/**
	 * @param nPhotos
	 * @param username
	 */
	public WallHandler(String nPhotos, User username) {
		this.nPhotos = Integer.parseInt(nPhotos);
		this.username = username;
	}
	
	/**
	 * @return
	 * @throws HaveNoPhotosExeption
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 */
	public Object wall() throws HaveNoPhotosExeption, NoSuchAlgorithmException, IOException {
		return Post.getInstance().wall(nPhotos, username);
	}

}
