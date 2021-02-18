package server.handler;

import java.util.List;

import server.catalog.GroupCatalog;
import server.domain.Group;
import server.domain.User;

public class MessageGroupHandler {
	public String groupId;
	public User user;
	
	public MessageGroupHandler(String groupId,User currentUser) {
		this.groupId= groupId;
		this.user = currentUser;
	}
	
	public boolean message(Object object){
		if(object==null) {
			return false;
		}
		Group group = GroupCatalog.getInstance().getGroup(groupId);
		for(int i=0;i<group.members.size();i++) {
			if(group.members.get(i).getUsername()!=user.getUsername())
				group.members.get(i).sentMessageToGroup(groupId, object);
		}
		return true;
	}
}
