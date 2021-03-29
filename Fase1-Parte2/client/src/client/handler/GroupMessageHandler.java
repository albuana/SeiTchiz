package client.handler;

import client.Client;
import client.exceptions.UserCouldNotSendException;

/**
 * Handles the Send message to a group operation
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class GroupMessageHandler {
	
	private String content;
	private String groupId;

	public GroupMessageHandler(String groupId,String content) {
		this.content= content;
		this.groupId = groupId;
	}
	
	public Object sendmsg() throws UserCouldNotSendException {
		Client.getInstance().send("msg",groupId,content);
		String res=(String) Client.getInstance().receive();
		return res;
	}
}
