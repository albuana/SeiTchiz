package server.handler;

import java.io.FileNotFoundException;

import server.domain.Post;
import server.domain.User;
import server.exceptions.post.HaveNoPhotosExeption;

public class WallHandler {

	private int nPhotos;
	private User username;


	public WallHandler(String nPhotos, User username) {
		this.nPhotos = Integer.parseInt(nPhotos);
		this.username = username;
	}
	
	public Object wall() throws FileNotFoundException, HaveNoPhotosExeption {
		return Post.getInstance().wall(nPhotos, username);
	}

}
