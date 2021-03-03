package server.catalog;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import server.Server;
import server.domain.User;
import server.FileManager;
public class UserCatalog {
	public static final String USERS_FILE_PATH = Server.DATA_PATH + "users/";
	private static UserCatalog INSTANCE;
	private static Map<String,User> userList;
	private static FileManager file;


	/**
	 * 
	 * @return the user catalog singleton
	 * @throws IOException 
	 */
	public static UserCatalog getInstance() throws IOException {
		if(INSTANCE == null)
			INSTANCE = new UserCatalog();
		return INSTANCE;
	}

	/**
	 * Creates the user's catalog instance
	 * @throws IOException 
	 */
	private UserCatalog () throws IOException {
		userList = new HashMap<>();
		file=new FileManager(USERS_FILE_PATH,"users.txt");
		initiateUserList();


	}



	private void initiateUserList() throws IOException {
		ArrayList<String> list=file.fileToList();
		for(int i=0;i<list.size();i++) {
			String[] userAndPass=list.get(i).split(":");
			userList.put(userAndPass[0],new User(userAndPass[0],userAndPass[1]));
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
	public void addUser(User user) throws IOException {
		synchronized(userList) {
			String str =user.getUsername()+ ":" + user.getPassword() + "\n";
			file.writeFile(str);
			userList.put(user.getUsername(), user);
		}
	}
}
