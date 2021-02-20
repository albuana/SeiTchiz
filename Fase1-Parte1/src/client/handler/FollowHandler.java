package client.handler;

import client.Client;

public class FollowHandler {

	public String addFollower(String userID) {
		Client.getInstance().send("Follow",userID);
		return (String) Client.getInstance().receive();
		
	}
	
	public String unfollow(String userID) {
		Client.getInstance().send("unfollow",userID);
		return (String) Client.getInstance().receive();
		
	}


}
