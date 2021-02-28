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
	
	/**
	 * Returns list with posts of follows
	 * @param n
	 * @return
	 */
	public Object wall(String n) {
		Client.getInstance().send("wall",n);
		return Client.getInstance().receive();
	}
	
	/**
	 * Give a like on a post
	 * @param photoid
	 * @return
	 */
	public Object like(String photoid) {
		Client.getInstance().send("like",photoid);
		return Client.getInstance().receive();
	}

}
