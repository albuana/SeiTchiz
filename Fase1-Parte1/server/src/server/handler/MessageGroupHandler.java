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

	private User sender;

	public MessageGroupHandler(String groupId, String msg, User sender) throws GroupNotExistException {
		Group group = GroupCatalog.getInstance().getGroup(groupId);
		if(group == null)
			throw new GroupNotExistException();
		message = new Message(sender,msg,group); 
		this.sender=sender;
	}


	public String sendmsg() throws UserDoesNotBelongToGroupException, IOException, ClassNotFoundException{
		if(!message.getGroup().hasMember(message.getSender()))
			throw new UserDoesNotBelongToGroupException();

		FileManager fileManager = message.getGroup().getGroupCollectFileManager();

		if(!(message.getViewers().size()==1 && message.getViewers().get(0).getUsername().equals(message.getSender().getUsername()))) {
			fileManager.writeFile(message.getSender().getUsername()+":"+message.getContent()+":"+message.viewersToString()+"\n");
		}		
		FileManager fileHistory = message.getGroup().getHistoryFile(sender);
		fileHistory.writeFile("Sender: "+message.getSender().getUsername()+" Msg: "+message.getContent()+"\n");

		return "Message Sent";	
	}
}
