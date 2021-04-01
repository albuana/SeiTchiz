package client.handler;

import client.Client;
import client.exceptions.UserCouldNotSendException;


/**
 * Handles the Follow, unfollow and viewfollowers of an user operation
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class FollowHandler {
	
	/**
	 * add follower
	 * @param userID
	 * @return
	 * @throws UserCouldNotSendException 
	 */
	public String addFollower(String userID) throws UserCouldNotSendException {
		Client.getInstance().send("follow",userID);
		return (String) Client.getInstance().receive();
		
	}
	
	/**
	 * unfollow user
	 * @param userID
	 * @return
	 * @throws UserCouldNotSendException 
	 */
	public String unfollow(String userID) throws UserCouldNotSendException {
		Client.getInstance().send("unfollow",userID);
		return (String) Client.getInstance().receive();
		
	}
	
	/**
	 * return list with follows
	 * @return
	 * @throws UserCouldNotSendException 
	 */
	public String viewFollowers() throws UserCouldNotSendException {
		Client.getInstance().send("viewFollowers");
		return (String) Client.getInstance().receive();
		
	}


}
