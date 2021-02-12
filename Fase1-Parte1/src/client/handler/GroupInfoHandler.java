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

public class GroupInfoHandler {
	private String groupId;
	private User currentUser;
	
	/**
	 * AddNewMemberGroupHandler constructor
	 * @param groupId id of the group
	 * @param owner owner of the group that is adding a new member
	 * @param newUser user that will be added
	 */
	public GroupInfoHandler(String groupId,User currentUser) {
		this.groupId= groupId;
		this.currentUser = currentUser;
	}
	
	public String getInfo() throws GroupException {
		Group group = GroupCatalog.getInstance().getGroup(groupId);
		if(group == null) 
			throw new GroupNotExistException(); 

		return group.info();
	}
}
