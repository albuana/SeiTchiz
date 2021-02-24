package client.handler;

import client.Client;
import client.exceptions.UserCouldNotSendException;

public class CollectMessageHandler {
	
	private String groupID;

	public CollectMessageHandler(String groupID) {
		this.groupID = groupID;
	}

	public Object collect() throws UserCouldNotSendException {
		Client.getInstance().send("collect",groupID);
		Object result = Client.getInstance().receive();
		return result;
	}
}
