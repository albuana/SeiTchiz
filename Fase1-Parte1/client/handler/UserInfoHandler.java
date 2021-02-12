package server.handler;

import java.util.List;

import server.catalog.GroupCatalog;
import server.domain.Group;
import server.domain.User;

public class UserInfoHandler {
	private User user;
	
	public UserInfoHandler (User user) {
		this.user = user;	
	}
	/**
	 * Gets groups from which user is member and gets groups from which user is owner 
	 * @return string with information
	 */
	public String getInfo() {
		List<Group> groups = GroupCatalog.getInstance().getUserGroups(user);
		StringBuilder joinedGroups = new StringBuilder("Groups joined:\n");
		StringBuilder createdGroups = new StringBuilder("Groups created:\n");
		for(Group g: groups) {
			if(user.equals(g.getOwner()))
				createdGroups.append("\t" + g.getGroupID()+ "\n");
			else
				joinedGroups.append("\t" + g.getGroupID()+ "\n");
		}
		return joinedGroups.toString() + createdGroups.toString();
	}
}
