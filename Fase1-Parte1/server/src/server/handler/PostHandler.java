package server.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import server.domain.Post;
import server.domain.User;

public class PostHandler {
	
	private File file;
	private User userID;

	/**
	 * Cosnstrucotr
	 */
	public PostHandler(File file, User userID) {
		this.file = file;
		this.userID = userID;
	}
	
	/**
	 * Create a Post
	 * @param file
	 * @param userID
	 * @return
	 * @throws IOException
	 */
	public String createPost() throws IOException {
		return Post.getInstance().createPost(file, userID);
	}

}
