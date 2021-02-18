package server.domain;

import java.io.IOException;
import java.util.ArrayList;

import client.Client;
import server.catalog.UserCatalog;
import server.exceptions.UserNotExistException;

public class Follow {
	

	private static Follow INSTANCE = null;
	private static ArrayList<String> userIDfollowersList;

	private Follow() {
		userIDfollowersList = new ArrayList<String>();
	}
	
	public static Follow getInstance() {
		if(INSTANCE == null)
			INSTANCE = new Follow();
		return INSTANCE;
	}
	
	public void follow(String userID) throws UserNotExistException, IOException {
        if(UserCatalog.getInstance().getUser(userID)==null || UserCatalog.getInstance().getUser(userID).equals(Client.getInstance().getUserID()))
            throw new UserNotExistException();

        userIDfollowersList.add(userID);
    }

}
