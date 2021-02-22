package server.handler;

import java.util.ArrayList;

import server.catalog.GroupCatalog;
import server.domain.Group;
import server.domain.User;
import server.exceptions.group.UserDoesNotBelongToGroupException;
import server.exceptions.group.GroupNotExistException;

public class GroupInfoHandler {

	private Group group;
	private User user;
	
	/**
	 * GroupInfoHandler constructor
	 * @param groupId id of the group 
	 * @param user user who  the function  
	 * @throws GroupNotExistException if the group does not exist
	 * @throws UserDoesNotBelongToGroupException if user does not belong to group
	 * @throws  
	 */
	public GroupInfoHandler(String groupId, User user) throws GroupNotExistException, UserDoesNotBelongToGroupException {
		this.group = GroupCatalog.getInstance().getGroup(groupId);
		if(this.group == null)
			throw new GroupNotExistException(); //esse grupo nao existe
		if(!group.getUsers().contains(user))
			throw new UserDoesNotBelongToGroupException(); //nao eh membro
		this.user = user;
	}

//	/**
//	 * Gets owner and total number of members of this group, if currentUser is owner of group
//	 * returns also user names of members
//	 * @return string in group info
//	 * @since 1.0
//	 */
//	public String getInfo() {
//		StringBuilder sb = new StringBuilder();
//		ArrayList<User> hash = group.getUsers();
//
//		
//		sb.append("Owner:" + (group.getOwner() == null ? "Inexistente" : group.getOwner().getUsername()) + "\n");
//		sb.append("Number of users: " + hash.size());
//
//		if(user.equals(group.getOwner()))
//			hash.stream().forEach(u -> sb.append("\n" + u.getUsername()));
//		
//		return sb.toString();
//	}
	
	public String getInfo() {
	StringBuilder sb = new StringBuilder();
	ArrayList<User> hash = group.getUsers();

	sb.append("Owner:" + (group.getOwner() == null ? "Inexistente" : group.getOwner().getUsername()) + "\n");
	sb.append("Number of users: " + hash.size() + "\n");
	sb.append("List of users: "+group.getUsers());

	return sb.toString();
}

	
}
