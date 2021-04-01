package client.handler;

import client.Client;
import client.exceptions.UserCouldNotSendException;

/**
 * Handles the Info operation
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
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
