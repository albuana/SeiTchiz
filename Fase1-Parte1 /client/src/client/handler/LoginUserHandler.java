package client.handler;

import java.util.Scanner;

import client.Client;
import client.exceptions.UserCouldNotSendException;

/**
 * Handles user login with server
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class LoginUserHandler {

	private String userID;
	private String pass;

	/**
	 * 
	 * @param userID the user's username
	 * @param pass the user's password
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
			Client.getInstance().receive();
			Client.getInstance().receive();
			System.out.println("\nSuccessfully registration.\n");
			return true;
		}
		Object res=Client.getInstance().receive();
        if(res.getClass().isInstance(true)) {
            boolean resBool=(boolean) res;
            if(resBool) {
                System.out.println("Login Succesfull \n");
            }
        }
        return res;
	}
}