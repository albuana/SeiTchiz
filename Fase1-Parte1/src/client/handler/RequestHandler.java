package client.handler;

import client.exceptions.UserCouldNotSendException;

/**
 * 
 * Handles the requests
 *
 */
public class RequestHandler {
	
	/**
	 * Calls new group handler
	 * @param groupID
	 * @return
	 * @throws UserCouldNotSendException 
	 */
	public static boolean newgroup(String groupID) throws UserCouldNotSendException {
		return new CreateNewGroupHandler(groupID).newgroup();

	}
	
	public static String follow(String userID){
		return new FollowHandler().addFollower(userID);

	}
	
	public static String unfollow(String userID){
		return new FollowHandler().unfollow(userID);

	}
	
	public static String viewfollowers(){
		return new FollowHandler().viewFollowers();

	}

}
