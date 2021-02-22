package server.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import server.catalog.GroupCatalog;
import server.catalog.UserCatalog;
import server.domain.Group;
import server.domain.User;
import server.exceptions.group.UserDoesNotBelongToGroupException;
import server.exceptions.UserNotExistException;
import server.exceptions.group.GroupException;
import server.exceptions.group.GroupNotExistException;
import server.exceptions.group.UserNotOwnerException;

public class GroupInfoHandler {

	private Group group;
	private User user;
	
	/**
	 * GroupInfoHandler constructor
	 * @param groupId id of the group 
	 * @param user user who  the function  
	 * @throws GroupNotExistException if the group does not exist
	 * @throws UserDoesNotBelongToGroupException if user does not belong to group
	 */
	public GroupInfoHandler(String groupId, User user) throws GroupNotExistException, UserDoesNotBelongToGroupException{
		this.group = GroupCatalog.getInstance().getGroup(groupId);
		if(this.group == null)
			throw new GroupNotExistException();
		if(!group.getUsers().contains(user))
			throw new UserDoesNotBelongToGroupException();
		this.user = user;
	}

	/**
	 * Gets owner and total number of members of this group, if currentUser is owner of group
	 * returns also user names of members
	 * @return string in group info
	 * @since 1.0
	 */
	public String getInfo() {
		StringBuilder sb = new StringBuilder();
		ArrayList<User> hash = group.getUsers();

		
		sb.append("Owner:" + (group.getOwner() == null ? "Inexistente" : group.getOwner().getUsername()) + "\n");
		sb.append("Number of users: " + hash.size());

		if(user.equals(group.getOwner()))
			hash.stream().forEach(u -> sb.append("\n" + u.getUsername()));
		
		return sb.toString();
	}
}
