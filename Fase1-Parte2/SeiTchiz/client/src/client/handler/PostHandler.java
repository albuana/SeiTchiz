package client.handler;

import java.io.File;

import client.Client;
import client.exceptions.UserCouldNotSendException;

/**
 * Handles post, like photo and wall operation
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class PostHandler {
	
	private static final String USERFILES_DIRECTORY = "./UserFiles/";
	
	/**
	 * Create a post
	 * @param photo
	 * @return
	 * @throws UserCouldNotSendException 
	 */
	public Object createPost(String photo) throws UserCouldNotSendException {
	    File f = new File(USERFILES_DIRECTORY+"/"+photo);
        if(!f.exists())
            return "Photo not found. Please check if you wrote the photo name correctly or if you have the photo on the UserFiles folder.";

		Client.getInstance().sendImage(f);
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
