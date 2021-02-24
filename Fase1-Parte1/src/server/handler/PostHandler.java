package server.handler;

import java.io.File;
import java.io.IOException;

import server.domain.Post;

public class PostHandler {

	/**
	 * Cosnstrucotr
	 */
	public PostHandler() {

	}
	
	/**
	 * Create a Post
	 * @param file
	 * @param userID
	 * @return
	 * @throws IOException
	 */
	public String createPost(File file, String userID) throws IOException {

		return Post.getInstance().createPost(file, userID);
	}

}
