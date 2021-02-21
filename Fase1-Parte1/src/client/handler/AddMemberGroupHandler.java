package client.handler;

import client.Client;
import client.exceptions.UserCouldNotSendException;

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
	public String addMember() throws UserCouldNotSendException {
		Client.getInstance().send("addu",groupID , newUser);
		boolean res=(boolean) Client.getInstance().receive();
		if(res) 
			return newUser+ " Adicionado ao grupo " + groupID;
		return "Não foi possivel adicionar" + newUser + " a " + groupID;
	}
}
