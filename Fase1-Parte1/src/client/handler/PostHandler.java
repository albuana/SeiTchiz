package client.handler;

import java.io.File;

import client.Client;

public class PostHandler {
	
	/**
	 * Create a post
	 * @param photo
	 * @return
	 */
	public Object createPost(File photo) {
		Client.getInstance().send("post",photo);
		return Client.getInstance().receive();
	}

}
