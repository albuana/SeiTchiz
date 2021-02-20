package client.handler;

import client.Client;

public class FollowHandler {
	
	/**
	 * add follower
	 * @param userID
	 * @return
	 */
	public String addFollower(String userID) {
		Client.getInstance().send("Follow",userID);
		return (String) Client.getInstance().receive();
		
	}
	
	/**
	 * unfollow user
	 * @param userID
	 * @return
	 */
	public String unfollow(String userID) {
		Client.getInstance().send("unfollow",userID);
		return (String) Client.getInstance().receive();
		
	}
	
	/**
	 * return list with follows
	 * @return
	 */
	public String viewFollowers() {
		Client.getInstance().send("viewFollowers");
		return (String) Client.getInstance().receive();
		
	}


}
