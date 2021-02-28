package server.handler;

import java.io.FileNotFoundException;

import server.domain.Post;
import server.domain.User;

public class WallHandler {

	private int nPhotos;
	private User username;


	public WallHandler(String nPhotos, User username) {
		this.nPhotos = Integer.parseInt(nPhotos);
		this.username = username;
	}
	
	public Object wall() throws FileNotFoundException {
		return Post.getInstance().wall(nPhotos, username);
	}

}
