package client.handler;

import client.Client;
import client.exceptions.UserCouldNotSendException;

/**
 * Handles the See the history operation
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
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
