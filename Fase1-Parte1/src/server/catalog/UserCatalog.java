package server.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.FileManager;
import server.Server;
import server.catalog.UserCatalog;
import server.domain.User;

public class UserCatalog {
	public static final String USERS_FILE_PATH = Server.DATA_PATH + "users/" + "users.txt";

	private static UserCatalog INSTANCE = new UserCatalog();
	private static Map<String,User> userList;

	/**
	 * 
	 * @return the user catalog singleton
	 */
	public static UserCatalog getInstance() {
		return INSTANCE;
	}

	/**
	 * Creates the user's catalog instance
	 */
	private UserCatalog () {
		//load current Users
		userList = new HashMap<>();
		List<String> currentUsers;
		try {
			currentUsers = new FileManager(USERS_FILE_PATH).loadContent();

			for(int i = 0; i < currentUsers.size(); i++) {
				String username = currentUsers.get(i).substring(0,currentUsers.get(i).indexOf(":"));
				String password = currentUsers.get(i).substring(currentUsers.get(i).indexOf(":") + 1);
				userList.put(username,new User(username,password));
			}
		} catch (FileNotFoundException e) {
			return;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * 
	 * @param username the user's username to search
	 * @return the user that has an username equal to the given username. If there is any it returns null
	 */
	public User getUser(String username) {
		synchronized(userList) {
			return userList.get(username);
		}	
	}

	/**
	 * 
	 * @param user the user's username
	 * @param passwd the user's password
	 * @throws ClassNotFoundException 
	 * @throws IOException if an error occurs whilst writing in the user's database
	 */
	public void addUser(User user) throws ClassNotFoundException, IOException {
		synchronized(userList) {
			userList.put(user.getUsername(), user);
			new FileManager(USERS_FILE_PATH).writeObjects(user.getUsername(), user.getPassword());
		}
	}

}
