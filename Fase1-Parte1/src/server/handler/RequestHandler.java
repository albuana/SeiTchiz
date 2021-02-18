package server.handler;

import java.io.IOException;
import java.util.List;

import server.catalog.UserCatalog;
import server.domain.Follow;
import server.domain.User;
import server.exceptions.UserNotExistException;
import server.exceptions.group.GroupException;
import server.exceptions.group.GroupNotExistException;
import server.exceptions.group.UserCouldNotCreateGroupException;
/**
 * 
 * Handles every request from server, calls right handler for every request
 *
 */
public final class RequestHandler {
	
	/**
	 * Creation of group
	 * @param groupId - group is created with this name
	 * @param user - String with userID
	 * @return true if group is created with success
	 * @throws UserCouldNotCreateGroupException - creation of group failed, mostly because groupID already exists for another group
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 * @see server.handlers.CreateGroupHandler
	 */
	public static boolean create(String groupId, User user) throws UserCouldNotCreateGroupException, IOException {
		return new CreateGroupHandler(groupId, user).create();
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
		return new AddNewMemberGroupHandler(groupId,newUser,owner).addMember();
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
	
	/**
	 * Shows every not seen yet message
	 * @param groupId
	 * @param user
	 * @return every photo and text message that the user hasn't seen yet
	 * @throws GroupNotExistException - if something went wrong with the group
	 * @throws UserDoesNotBelongToGroupException - if user doesn't belong to group
	 * @throws IOException
	 * @throws NoSuchAlgorithmException 
	 * @throws ClassNotFoundException 
	 * @see server.handlers.CollectMessagesHandler
	 */
	public static List<Object> collect (String groupId, User user) throws GroupNotExistException, IOException, ClassNotFoundException {
		return new CollectMessagesHandler(groupId,user).collect();
	}
	 
	/**
	 * Shows every not seen yet message
	 * @param groupId
	 * @param user
	 * @return true if message is sent && false if message is not sent
	 * @throws GroupNotExistException - if something went wrong with the group
	 * @throws UserDoesNotBelongToGroupException - if user doesn't belong to group
	 * @throws IOException
	 * @throws NoSuchAlgorithmException 
	 * @throws ClassNotFoundException 
	 * @see server.handlers.CollectMessagesHandler
	 */
	public static boolean messageGroup (String groupId, User user, Object object) throws GroupNotExistException, IOException, ClassNotFoundException {
		return new MessageGroupHandler(groupId,user).message(object);
	}
	
	/**
	 * Shows every message that is already archived
	 * @param groupId
	 * @param user
	 * @return every test Message in history file
	 * @throws GroupNotExistException - if something went wrong with the group
	 * @throws UserDoesNotBelongToGroupException - if user doesn't belong to group
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @see server.handlers.HistoryHandler
	 */
//	public static List<Object> history(String groupId, User user) throws GroupNotExistException, IOException, ClassNotFoundException {
//		return new HistoryHandler(groupId, user).getHistory();
//	}
//	
	@SuppressWarnings("static-access")
	public static boolean follow(String userID) throws UserNotExistException, IOException{
		return  new Follow().follow(UserCatalog.getInstance().getUser(userID));
	}
	
	

}
