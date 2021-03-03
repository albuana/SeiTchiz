package client.handler;

import java.util.Scanner;

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
	public Object login() throws UserCouldNotSendException {
		Client client = Client.getInstance();
		client.send("login",userID, pass);
		boolean newUser=(boolean) Client.getInstance().receive();
		if(newUser) {
			Scanner sc=new Scanner(System.in);
			System.out.print("Type your name: ");
			String name=sc.nextLine();
			Client.getInstance().send(name);
			System.out.println("\nSuccessfully registration.\n");
			return true;
		}
		//        System.out.println("\nSuccessfully login.");
		return Client.getInstance().receive();
	}
}
