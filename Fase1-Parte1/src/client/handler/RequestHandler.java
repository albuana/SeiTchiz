package client.handler;


import java.io.File;
import java.io.IOException;

import com.sun.org.apache.xalan.internal.xsltc.runtime.MessageHandler;

import client.exceptions.UserCouldNotSendException;
import server.domain.User;
import server.exceptions.group.GroupNotExistException;
import server.exceptions.group.UserDoesNotBelongToGroupException;

/**
 * 
 * Handles the requests
 *
 */
public class RequestHandler {
	private static final String USERFILES_DIRECTORY = "./UserFiles/";
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
	
	public static Object follow(String userID){
		return new FollowHandler().addFollower(userID);
	}
	
	public static Object unfollow(String userID){
		return new FollowHandler().unfollow(userID);
	}
	
	public static Object viewfollowers(){
		return new FollowHandler().viewFollowers();
	}
	
	public static Object ginfo(String groupId){
		return new InfoHandler(groupId).getInfo();
	}
	
	public static Object ginfo(){
		return new InfoHandler().getInfo();
	}
	
	public static Object msg(String groupId, String content){
		return new GroupMessageHandler(groupId, content).sendmsg();
	}
	
	public static Object post(String photo){
		File f = new File(USERFILES_DIRECTORY+photo);
		if(!f.exists())
			return "Foto nao encontrada";
		
		return new PostHandler().createPost(f);
	}
	

}
