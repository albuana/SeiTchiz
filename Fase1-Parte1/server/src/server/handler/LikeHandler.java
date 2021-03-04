package server.handler;

import java.io.IOException;

import server.domain.Post;
import server.domain.User;
import server.exceptions.post.NoPostExeption;

public class LikeHandler {
	
	
	private String postID;
	private User username;

	public LikeHandler(String postID, User username) {
		this.postID = postID;
		this.username = username;
	}

	public Object like() throws IOException, NoPostExeption {
		return Post.getInstance().like(postID, username);
	}

}
