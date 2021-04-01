package client.handler;

import client.Client;
import client.exceptions.UserCouldNotSendException;

/**
 * Handles the collect messages operations
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
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
