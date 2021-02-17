package server.catalog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import server.domain.User;

public class UserCatalog {
	private static UserCatalog INSTANCE = null;
	private static Map<String,User> userList;

	/**
	 * 
	 * @return the user catalog singleton
	 */
	public static UserCatalog getInstance() {
		if(INSTANCE == null)
			INSTANCE = new UserCatalog();
		return INSTANCE;
	}

	/**
	 * Creates the user's catalog instance
	 */
	private UserCatalog () {
		userList = new HashMap<>();
	}

	/**
	 * 
	 * @param username the user's username to search
	 * @return the user that has an username equal to the given username. If there is any it returns null
	 */
	public User getUser(String username) {
		return userList.get(username);
	}

	/**
	 * 
	 * @param user the user's username
	 * @param passwd the user's password
	 * @throws ClassNotFoundException 
	 * @throws IOException if an error occurs whilst writing in the user's database
	 */
	public void addUser(User user) {
		userList.put(user.getUsername(), user);
	}
}
