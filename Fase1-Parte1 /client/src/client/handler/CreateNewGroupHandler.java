package client.handler;

import client.Client;
import client.exceptions.UserCouldNotSendException;

/**
 * Handles the Create New Group operation
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class CreateNewGroupHandler {

	private String groupID;

	/**
	 * Create New Group Handler Constructor
	 * @param groupId
	 */
	public CreateNewGroupHandler(String groupID) {
		this.groupID = groupID;
	}

	/**
	 * Executes the client side operation of the create new Group operation
	 * @return the result of the operation
	 * @throws UserCouldNotSendException
	 */
	public Object newgroup() throws UserCouldNotSendException {
		Client.getInstance().send("newgroup",groupID);
		return Client.getInstance().receive();
	}

}

