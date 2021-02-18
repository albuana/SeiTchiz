package client.handler;

import client.Client;

public class FollowHandler {

	public Object addFollower(String userID) {
		Client.getInstance().send("Follow",userID);
		return Client.getInstance().receive();
		
	}

}
