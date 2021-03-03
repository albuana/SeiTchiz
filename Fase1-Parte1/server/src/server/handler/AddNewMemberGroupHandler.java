package server.handler;

import java.io.IOException;

import server.catalog.GroupCatalog;
import server.catalog.UserCatalog;
import server.domain.Group;
import server.domain.User;
import server.exceptions.UserNotExistException;
import server.exceptions.group.GroupException;
import server.exceptions.group.GroupNotExistException;
import server.exceptions.group.UserAlreadyInGroupException;
import server.exceptions.group.UserNotOwnerException;
/**
 * Handles addition of new user to group 
 * Handles the necessary procedures for adding a new member to a group
 */
public class AddNewMemberGroupHandler {
	private String groupId;
	private User owner;
	private String newUser;
	
	/**
	 * AddNewMemberGroupHandler constructor
	 * @param groupId id of the group
	 * @param owner owner of the group that is adding a new member
	 * @param newUser user that will be added
	 */
	public AddNewMemberGroupHandler(String groupId, String newUser, User owner) {
		this.groupId= groupId;
		this.owner = owner;
		this.newUser = newUser;
	}
	
	/**
	 * 
	 * @return true if it was possible to add member to group
	 * @throws GroupException if group does not exist 
	 * @throws IOException if an error occurs while trying to add the member to the group
	 * @throws UserNotExistException if the new user does not exist
	 * @throws ClassNotFoundException 
	 */
	public String addMember() throws GroupException,IOException, UserNotExistException, ClassNotFoundException {
		Group group = GroupCatalog.getInstance().getGroup(groupId);
		if(group == null) 
			throw new GroupNotExistException(); 
		if(!owner.equals(group.getOwner()))
			throw new UserNotOwnerException();
		User newbie = UserCatalog.getInstance().getUser(newUser);
		if(newbie == null)
			throw new UserNotExistException();
		if(group.getUsers().contains(newUser))
			throw new UserAlreadyInGroupException();
		group.createHistory(newbie);
		group.addMember(newbie);
		return newbie + " was added to the group " + groupId + ".";
	}

}