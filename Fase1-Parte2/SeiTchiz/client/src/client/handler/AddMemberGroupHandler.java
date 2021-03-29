package client.handler;

import client.Client;
import client.exceptions.UserCouldNotSendException;

/**
 * Handles add user operations
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class AddMemberGroupHandler {
	private String groupID;
	private String newUser;

	/**
	 * Add Member Handler Constructor
	 * @param groupId
	 * @param newUser
	 */
	public AddMemberGroupHandler(String groupID, String newUser) {
		this.groupID = groupID;
		this.newUser=newUser;
	}

	/**
	 * Executes the client side operation of the create new Group operation
	 * @return the result of the operation
	 * @throws UserCouldNotSendException
	 */
	public Object addMember() throws UserCouldNotSendException {
		Client.getInstance().send("addu",groupID , newUser);
		Object result = Client.getInstance().receive();
		return result;
	}
}
