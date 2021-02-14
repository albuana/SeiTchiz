package client.handler;

import client.Client;
import client.exceptions.UserCouldNotSendException;

public class LoginUserHandler {
	
	private String userID;
	private String pass;

	/**
	 * 
	 * @param userID the user's username
	 * @param password the user's password
	 */
	public LoginUserHandler(String userID, String pass) {
		this.userID = userID;
		this.pass = pass;
	}
	
	/**
	 * Sends a call for the login function in the server and waits for the response
	 * @return true if the user was able to login and a string if an error occurred if anything failed
	 * @throws UserCouldNotSendException
	 */
	public Object login(Client newClient) throws UserCouldNotSendException {
		Client client = newClient.getInstance();
		client.send("login",userID, pass);
		return newClient.getInstance().receive();
	}
	

}
