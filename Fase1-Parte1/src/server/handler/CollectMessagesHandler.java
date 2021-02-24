package server.handler;

import java.util.List;

import server.FileManager;
import server.catalog.GroupCatalog;
import server.domain.Group;
import server.domain.User;
import server.exceptions.group.GroupNotExistException;
import server.exceptions.group.UserDoesNotBelongToGroupException;

public class CollectMessagesHandler {
	private Group group;
	private User user;
	
	public CollectMessagesHandler(String groupID,User currentUser) throws GroupNotExistException, UserDoesNotBelongToGroupException {
		this.group = GroupCatalog.getInstance().getGroup(groupID);
		this.user = currentUser;
		if(group ==  null)
			throw new GroupNotExistException();
		
		if(!group.getUsers().contains(currentUser))  {
			throw new UserDoesNotBelongToGroupException();
		}
	}
	
	public List<Object> collect(){
		FileManager groupFile = group.getGroupCollectFileManager();
		
		GroupCatalog.getInstance().getGroup(group).getGroupCollectFileManager();
		return user.seeMessagesGroup(group);
	}
	
	

}
