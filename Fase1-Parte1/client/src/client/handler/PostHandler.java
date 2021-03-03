package client.handler;

import java.io.File;

import client.Client;
import client.exceptions.UserCouldNotSendException;

public class PostHandler {
	
	/**
	 * Create a post
	 * @param photo
	 * @return
	 * @throws UserCouldNotSendException 
	 */
	public Object createPost(File photo) throws UserCouldNotSendException {
		Client.getInstance().send("post",photo);
		return Client.getInstance().receive();
	}
	
	/**
	 * Returns list with posts of follows
	 * @param n
	 * @return
	 * @throws UserCouldNotSendException 
	 */
	public Object wall(String n) throws UserCouldNotSendException {
		Client.getInstance().send("wall",n);
		return Client.getInstance().receive();
	}
	
	/**
	 * Give a like on a post
	 * @param photoid
	 * @return
	 * @throws UserCouldNotSendException 
	 */
	public Object like(String photoid) throws UserCouldNotSendException {
		Client.getInstance().send("like",photoid);
		return Client.getInstance().receive();
	}

}
