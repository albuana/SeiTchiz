package client.handler;

import client.Client;
import client.exceptions.UserCouldNotSendException;

public class InfoHandler {
	private String groupId;

	public InfoHandler(String groupId) {
		this.groupId=groupId;
	}

	public InfoHandler() {
		
	}

	public String getInfo() throws UserCouldNotSendException {
		if(groupId == null) {
			Client.getInstance().send("ginfo");
			String res=(String) Client.getInstance().receive();
			return res;
		}
		Client.getInstance().send("ginfo",groupId);
		String res=(String) Client.getInstance().receive();
		return res;
	}

}
