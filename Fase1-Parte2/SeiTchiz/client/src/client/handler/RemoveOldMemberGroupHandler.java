package client.handler;

import client.Client;
import client.exceptions.UserCouldNotSendException;

/**
 * Handles remove user operations
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class RemoveOldMemberGroupHandler {
	
	private String groupID;
	private String oldUser;
	
	public RemoveOldMemberGroupHandler(String groupID, String oldUser) {
		this.groupID = groupID;
		this.oldUser=oldUser;
	}

	public Object removeMember() throws UserCouldNotSendException {
		Client.getInstance().send("removeu",groupID , oldUser);
		Object result = Client.getInstance().receive();
		return result;
	}
}