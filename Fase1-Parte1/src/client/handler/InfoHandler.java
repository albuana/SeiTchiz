package client.handler;

import client.Client;

public class InfoHandler {
	private String groupId;
	
	public InfoHandler(String groupId) {
		this.groupId=groupId;
	}

	public String getInfo() {
		Client.getInstance().send("ginfo",groupId);
		String res=(String) Client.getInstance().receive();
		return res;
	}

}
