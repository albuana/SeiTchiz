package server.handler;

import java.io.IOException;

import server.catalog.GroupCatalog;
import server.catalog.UserCatalog;
import server.domain.Group;
import server.domain.User;
import server.exceptions.UserNotExistException;
import server.exceptions.group.GroupException;
import server.exceptions.group.GroupNotExistException;
import server.exceptions.group.UserNotOwnerException;

public class RemoveMemberGroupHandler {
	private String groupId;
	private User owner;
	private String newUser;
	/**
	 * RemoveMemberGroupHandler constructor
	 * @param groupId id of the group
	 * @param owner owner of the group that is adding a new member
	 * @param newUser user that will be added
	 */
	public RemoveMemberGroupHandler(String groupId, String newUser, User owner) {
		this.groupId= groupId;
		this.owner = owner;
		this.newUser = newUser;
	}
	
	public boolean removeMember() throws GroupException,IOException, UserNotExistException, ClassNotFoundException {
		Group group = GroupCatalog.getInstance().getGroup(groupId);
		if(group == null) 
			throw new GroupNotExistException(); 
		if(!owner.equals(group.getOwner()))
			throw new UserNotOwnerException();
		User newbie = UserCatalog.getInstance().getUser(newUser);
		if(newbie == null)
			throw new UserNotExistException();
		
		group.removeMember(newbie);
		return true;
	}
}
