package client.handler;

import client.Client;
import client.exceptions.UserCouldNotSendException;

/**
 * Handles the Group Info operation
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
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