package server.catalog;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import server.Server;
import server.domain.User;
import server.FileManager;

/**
 * Catalog knows all the information related to the users
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 *
 */
public class UserCatalog {
	public static final String USERS_FILE_PATH = Server.DATA_PATH + "users/";
	private static UserCatalog INSTANCE;
	private static Map<String,User> userList;
	private static FileManager file;

	/**
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

	/**
	 * Initialize the users list
	 * @throws IOException
	 */
	private void initiateUserList() throws IOException {
		ArrayList<String> list=file.fileToList();
		for(int i=0;i<list.size();i++) {
			String[] userAndPass=list.get(i).split(":");
			userList.put(userAndPass[1],new User(userAndPass[1],userAndPass[2]));
		}
	}

	/**
	 * @param username the user's username to search
	 * @return the user that has an username equal to the given username. If there is any it returns null
	 */
	public User getUser(String username) {
		return userList.get(username);
	}

	/**
	 * Add the name; username and the password of the user to the file and to the list
	 * @param user the user's username
	 * @param name the user's name
	 * @throws ClassNotFoundException 
	 * @throws IOException if an error occurs whilst writing in the user's database
	 */
	public void addUser(User user, String name) throws IOException {
		String str = name + ":" + user.getUsername() + ":" + user.getPassword() + "\n";
        file.writeFile(str);
        userList.put(user.getUsername(), user);
	}
	
	
}
