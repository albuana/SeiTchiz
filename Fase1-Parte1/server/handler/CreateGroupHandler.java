package server.handler;

import java.util.ArrayList;
import java.util.Hashtable;

import server.domain.Group;
import server.domain.User;
import server.catalog.GroupCatalog;

public class CreateGroupHandler {
	public Group group;
	private String groupId;
	private User user;
	
	public CreateGroupHandler(String groupId, User user) {
		this.user=user;
		this.groupId=groupId;
	}
	public boolean create() {
		this.group=new Group(groupId, user);	
		if(GroupCatalog.addGroup(group)) {
			return true;
		}
		return false;
	}
	
}
