package server.catalog;

import java.util.ArrayList;
import java.util.List;

import server.domain.User;

public class UserCatalog {
	private static UserCatalog INSTANCE = null;
	private static ArrayList<User> userList = null;
	
	private UserCatalog () {
		userList = new ArrayList<User>();
	}
	
	public static UserCatalog getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new UserCatalog();
		}
		return INSTANCE;
	}
	
	public static User getUser(String username) {
		for(User a: userList) {
			if(a.getUsername().equals(username))
				return a;
		}
		return null;
	}

}	