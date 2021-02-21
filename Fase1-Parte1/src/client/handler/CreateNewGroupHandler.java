package client.handler;

import client.Client;
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
	public String newgroup() throws UserCouldNotSendException {
		Client.getInstance().send("newgroup",groupID);
		boolean res=(boolean) Client.getInstance().receive();
		if(res) 
			return "Grupo Criado";
		return "Grupo j� existe";
	}

}

