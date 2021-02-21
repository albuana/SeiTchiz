package client.handler;

import java.io.IOException;

import client.exceptions.UserCouldNotSendException;
import server.domain.User;
import server.exceptions.UserNotExistException;
import server.exceptions.group.GroupException;
import server.handler.AddNewMemberGroupHandler;

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
	public static String newgroup(String groupID) throws UserCouldNotSendException {
		return new CreateNewGroupHandler(groupID).newgroup();
	}
	
	public static String addu(String newUser, String groupId) throws UserCouldNotSendException {
		return new AddMemberGroupHandler(groupId,newUser).addMember();
	}
	
	public static String removeu(String oldUser, String groupId) throws UserCouldNotSendException {
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


}
