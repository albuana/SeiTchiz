package client.handler;

import client.Client;
import client.exceptions.UserCouldNotSendException;

public class GroupInfoHandler {

	private String groupId; 
	/**
	 * Group Info Handler Constructor
	 * @param groupId
	 */
	public GroupInfoHandler(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * 
	 * @return the group information
	 * @throws UserCouldNotSendException
	 */
	public Object get() throws UserCouldNotSendException {
		Client.getInstance().send("ginfo",groupId);
		return Client.getInstance().receive();
	}

}