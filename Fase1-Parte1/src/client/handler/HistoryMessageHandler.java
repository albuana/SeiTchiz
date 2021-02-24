package client.handler;

import client.Client;
import client.exceptions.UserCouldNotSendException;

public class HistoryMessageHandler {
	private String groupID;

	public HistoryMessageHandler(String groupID) {
		this.groupID = groupID;
	}

	public Object history() throws UserCouldNotSendException {
		Client.getInstance().send("history",groupID);
		Object result = Client.getInstance().receive();
		return result;
	}
}
