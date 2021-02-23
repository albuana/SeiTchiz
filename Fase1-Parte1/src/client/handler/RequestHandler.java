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
	public static Object newgroup(String groupID) throws UserCouldNotSendException {
		return new CreateNewGroupHandler(groupID).newgroup();
	}
	
	public static Object addu(String newUser, String groupId) throws UserCouldNotSendException {
		return new AddMemberGroupHandler(groupId,newUser).addMember();
	}
	
	public static Object removeu(String oldUser, String groupId) throws UserCouldNotSendException {
		return new RemoveOldMemberGroupHandler(groupId,oldUser).removeMember();
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
	
	public static Object ginfo(String groupId){
		return new InfoHandler(groupId).getInfo();
	}
	
	public static Object ginfo(){
		return new InfoHandler().getInfo();
	}


}
