package client.handler;

import client.Client2;
import client.exceptions.UserCouldNotSendException;

/**
 * 
 * Handles the Create New Group operation
 *
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
	public Object newgroup() {
		Client2.getInstance().send("newgroup",groupID);
		return Client2.getInstance().receive();
	}

}
