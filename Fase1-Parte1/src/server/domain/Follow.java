package server.domain;

import java.io.IOException;
import java.util.ArrayList;

import client.Client;
import server.catalog.UserCatalog;
import server.exceptions.UserNotExistException;

public class Follow {
	

	private static Follow INSTANCE = null;
	private static ArrayList<User> userIDfollowersList;

	public Follow() {
		userIDfollowersList = new ArrayList<User>();
	}
	
	public static Follow getInstance() {
		if(INSTANCE == null)
			INSTANCE = new Follow();
		return INSTANCE;
	}
	
	public static boolean follow(User userID) throws UserNotExistException, IOException {
		 if(UserCatalog.getInstance().getUser(userID.getUsername())==null || UserCatalog.getInstance().getUser(userID.getUsername()).equals(Client.getInstance().getUserID()))
	            throw new UserNotExistException();

	        return userIDfollowersList.add(userID);
	    }

}
