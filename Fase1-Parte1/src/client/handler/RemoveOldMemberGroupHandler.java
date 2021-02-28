package client.handler;

import client.Client;

public class RemoveOldMemberGroupHandler {
	
	private String groupID;
	private String oldUser;
	
	public RemoveOldMemberGroupHandler(String groupID, String oldUser) {
		this.groupID = groupID;
		this.oldUser=oldUser;
	}

	public Object removeMember() {
		Client.getInstance().send("removeu",groupID , oldUser);
		Object result = Client.getInstance().receive();
		return result;
	}
}
