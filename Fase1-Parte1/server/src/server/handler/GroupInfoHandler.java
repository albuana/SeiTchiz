package server.handler;

import server.catalog.GroupCatalog;
import server.domain.Group;
import server.domain.User;
import server.exceptions.group.UserDoesNotBelongToGroupException;
import server.exceptions.group.UserNotOwnerException;
import server.exceptions.group.GroupNotExistException;

/**
 * Handles the group info information
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class GroupInfoHandler {

	private Group group;
	private User user;

	public GroupInfoHandler(User user) {
		this.user = user; 
	}

	/**
	 * GroupInfoHandler constructor
	 * @param groupId id of the group 
	 * @param user user who  the function  
	 * @throws GroupNotExistException if the group does not exist
	 * @throws UserDoesNotBelongToGroupException if user does not belong to group
	 * @throws UserNotOwnerException 
	 */
	public GroupInfoHandler(String groupId, User user) throws GroupNotExistException, UserDoesNotBelongToGroupException, UserNotOwnerException {
		this.group = GroupCatalog.getInstance().getGroup(groupId);
		if(this.group == null)
			throw new GroupNotExistException(); //esse grupo nao existe
		if(!group.getUsers().contains(user))
			throw new UserDoesNotBelongToGroupException(); //nao eh membro
		this.user = user;
	}

	public String getInfo() {
		StringBuilder sb = new StringBuilder();

		if(group != null) {
			sb.append("Owner: " + (group.getOwner() == null ? "Inexistente" : group.getOwner().getUsername()) + "\n");
			sb.append("List of members: "+group.getUsers() + "\n");
		} else {
			return GroupCatalog.getInstance().infoUser(user);
		}
		return sb.toString();
	}
}
