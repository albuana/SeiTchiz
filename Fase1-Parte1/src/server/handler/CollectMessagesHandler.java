package server.handler;

import java.util.List;

import server.catalog.GroupCatalog;
import server.domain.User;

public class CollectMessagesHandler {
	public String groupId;
	public User user;
	
	public CollectMessagesHandler(String groupId,User currentUser) {
		this.groupId= groupId;
		this.user = currentUser;
	}
	
	public List<Object> collect(){
		GroupCatalog.getInstance().getGroup(groupId).getGroupCollectFileManager();
		return user.seeMessagesGroup(groupId);
	}

}
