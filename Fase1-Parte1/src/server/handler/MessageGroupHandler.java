package server.handler;

import java.io.IOException;

import server.FileManager;
import server.catalog.GroupCatalog;
import server.domain.Group;
import server.domain.Message;
import server.domain.User;
import server.exceptions.group.GroupNotExistException;
import server.exceptions.group.UserDoesNotBelongToGroupException;

public class MessageGroupHandler {

	private Message message;

	public MessageGroupHandler(String msg, String groupId, User sender) throws GroupNotExistException {
		Group group = GroupCatalog.getInstance().getGroup(groupId);
		if(group == null)
			throw new GroupNotExistException();
		message = new Message(sender,msg,group); 
	}


	public boolean sendmsg() throws UserDoesNotBelongToGroupException, IOException, ClassNotFoundException{
		if(!message.getGroup().hasMember(message.getSender()))
			throw new UserDoesNotBelongToGroupException();
		FileManager fileManager = message.getGroup().getGroupCollectFileManager();
		//TODO
		return true;	
	}
}

//	public String groupId;
//	public User user;
//	
//	public MessageGroupHandler(String groupId,User currentUser) {
//		this.groupId= groupId;
//		this.user = currentUser;
//	}
//	
//	public boolean message(Object object){
//		if(object==null) {
//			return false;
//		}
//		Group group = GroupCatalog.getInstance().getGroup(groupId);
//		for(int i=0;i<group.getUsers().size();i++) {
//			if(group.getUsers().get(i).getUsername()!=user.getUsername())
//				group.getUsers().get(i).sentMessageToGroup(groupId, object);
//		}
//		return true;
//	}
