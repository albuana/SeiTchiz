package client.handler;

import client.Client;
import client.exceptions.UserCouldNotSendException;

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

