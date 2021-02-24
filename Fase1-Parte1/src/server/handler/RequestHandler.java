package server.handler;

import java.io.File;
import java.io.IOException;
import java.util.List;

import client.handler.FollowHandler;
import server.catalog.UserCatalog;
import server.domain.Follow;
import server.domain.User;
import server.exceptions.UserNotExistException;
import server.exceptions.group.GroupException;
import server.exceptions.group.GroupNotExistException;
import server.exceptions.group.UserDoesNotBelongToGroupException;
import server.exceptions.group.GroupAlreadyExistException;
/**
 * 
 * Handles every request from server, calls right handler for every request
 *
 */
public final class RequestHandler {
	
	/**
	 * Follow Handler
	 * @param userID
	 * @param currentUserID
	 * @return
	 * @throws IOException
	 */
	public static String follow(String userID, String currentUserID) throws IOException{
		return  new FollowerHandler().follow(UserCatalog.getInstance().getUser(userID), currentUserID);
	}

	/**
	 * unFollow Handler
	 * @param userID
	 * @param currentUserID
	 * @return
	 * @throws IOException
	 */
	public static String unfollow(String userID, String currentUserID) throws IOException{
		return  new FollowerHandler().unfollow(UserCatalog.getInstance().getUser(userID), currentUserID);
	}
	
	/**
	 * returns string with list of follows
	 * @param userID
	 * @return
	 * @throws IOException
	 */
	public static String viewFollowers(String userID) throws IOException{
		return  new FollowerHandler().viewFollowers(userID);
	}
	
	//post
	//wall
	//like
	
	/**
	 * Creation of group
	 * @param groupId - group is created with this name
	 * @param user - String with userID
	 * @return true if group is created with success
	 * @throws GroupAlreadyExistException - creation of group failed, mostly because groupID already exists for another group
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 * @see server.handlers.CreateGroupHandler
	 */
	public static boolean newgroup(String groupId, User user) throws GroupAlreadyExistException, IOException {
		return new CreateGroupHandler(groupId, user).newgroup();
	}
	
	/**
	 * Adds user to group
	 * @param newUser - user to add
	 * @param groupId
	 * @param owner - of group
	 * @return true if user is added with success
	 * @throws UserNotExistException - if newUser doesn't correspond to any  
	 * @throws server.exceptions.group.GroupException 
	 * @throws IOException 
	 * @throws GroupException - if something went wrong with the group
	 * @throws ClassNotFoundException 
	 * @see server.handlers.AddNewMemberGroupHandler
	 */
	public static boolean addu(String newUser, String groupId, User owner) throws UserNotExistException, ClassNotFoundException, GroupException, IOException {
		return new AddNewMemberGroupHandler(newUser,groupId,owner).addMember();
	}

	
	/**
	 * Remove user from group 
	 * @param oldUser - user to remove
	 * @param groupId
	 * @param owner  of group
	 * @return true if user is removed with success
	 * @throws GroupException - if something went wrong with the group
	 * @throws IOException 
	 * @throws UserNotExistException - if userID (oldUser) does not exist
	 * @throws ClassNotFoundException 
	 */
	
	public static boolean removeu(String oldUser, String groupId, User owner) throws GroupException, 
																		IOException, UserNotExistException, ClassNotFoundException {
		return new RemoveMemberGroupHandler(oldUser, groupId,  owner).removeMember();
	}
	
	/**
	 * Shows group info 
	 * @param groupId
	 * @param currentUser - current logged user
	 * @return owner and total number of members of this group, if currentUser is owner of group
	 * returns also user names of members
	 * @throws GroupException 
	 * @throws UserDoesNotBelongToGroupException - in case user does not belong to group
	 * @see server.handlers.GroupInfoHandler
	 */
	public static String ginfo (String groupId, User currentUser) throws GroupException {
		return new GroupInfoHandler(groupId,currentUser).getInfo();
	}
	
	public static String ginfo (User currentUser) throws GroupException {
		return new GroupInfoHandler(currentUser).getInfo();
	}
	
	
	/**
	 * Shows every not seen yet message
	 * @param groupId
	 * @param user
	 * @return true if message is sent && false if message is not sent
	 * @throws GroupNotExistException - if something went wrong with the group
	 * @throws UserDoesNotBelongToGroupException - if user doesn't belong to group
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 * @see server.handlers.CollectMessagesHandler
	 */
	public static String msg(String groupId, String content, User sender) throws GroupNotExistException, IOException, ClassNotFoundException, UserDoesNotBelongToGroupException {
		return new MessageGroupHandler(groupId, content, sender).sendmsg();
	}
	
	/**
	 * Shows every not seen yet message
	 * @param groupId
	 * @param user
	 * @throws GroupNotExistException - if something went wrong with the group
	 * @throws UserDoesNotBelongToGroupException - if user doesn't belong to group
	 * @throws IOException
	 * @throws NoSuchAlgorithmException 
	 * @throws ClassNotFoundException 
	 * @see server.handlers.CollectMessagesHandler
	 */
	public static String collect (String groupID, User user) throws GroupNotExistException, IOException, ClassNotFoundException, UserDoesNotBelongToGroupException {
		return new CollectMessagesHandler(groupID,user).collect();
	}
	
	/**
	 * Create post
	 * @param object
	 * @param username
	 * @return
	 * @throws IOException
	 */
	public static Object post(Object object, String username) throws IOException {
		return  new PostHandler().createPost((File) object, username);
	}
	 
	
	/**
	 * Shows every message that is already archived
	 * @param groupId
	 * @param user
	 * @return every test Message in history file
	 * @throws GroupNotExistException - if something went wrong with the group
	 * @throws UserDoesNotBelongToGroupException - if user doesn't belong to group
	 * @throws IOException 
	 * @throws UserAlreadyFollowedException 
	 * @throws UserFollowingHimSelfException 
	 * @throws ClassNotFoundException 
	 * @see server.handlers.HistoryHandler
	 */
	public static String history(String groupId, User user) throws GroupNotExistException, IOException, ClassNotFoundException, UserDoesNotBelongToGroupException {
		return new HistoryHandler(groupId, user).getHistory();
	}
	
	

}
