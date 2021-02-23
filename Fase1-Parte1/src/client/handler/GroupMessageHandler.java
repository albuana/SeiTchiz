package client.handler;

import client.Client;

public class GroupMessageHandler {
	
	private String content;
	private String groupId;

	public GroupMessageHandler(String groupId,String content) {
		this.content= content;
		this.groupId = groupId;
	}
	
	public Object sendmsg() {
		Client.getInstance().send("msg",groupId,content);
		String res=(String) Client.getInstance().receive();
		return res;
	}
}

