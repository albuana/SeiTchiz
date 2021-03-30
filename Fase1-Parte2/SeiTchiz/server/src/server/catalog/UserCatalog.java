package server.catalog;


import java.io.IOException;
import java.security.PublicKey;
import java.security.cert.Certificate;
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
	public static final String CERTIFICATE_FILE_PATH = Server.DATA_PATH + "publicKeys/";
	private static UserCatalog INSTANCE;
	private static Map<String,User> userList;
	private static FileManager file;

	/**
	 * @return the user catalog singleton
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static UserCatalog getInstance() throws IOException, ClassNotFoundException {
		if(INSTANCE == null)
			INSTANCE = new UserCatalog();
		return INSTANCE;
	}

	/**
	 * Creates the user's catalog instance
	 * Inicializa the user.txt file
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	private UserCatalog () throws IOException, ClassNotFoundException {
		userList = new HashMap<>();
		file = new FileManager(USERS_FILE_PATH,"users.txt");
		initiateUserList();
	}

	/**
	 * Initialize the users list
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	private void initiateUserList() throws IOException, ClassNotFoundException {
		ArrayList<String> list=file.fileToList();
		for(int i=0;i<list.size();i++) {
			String[] userAndPass=list.get(i).split(",");
			String user = userAndPass[1];
			String certPath = userAndPass[2];
			Certificate certClient = (Certificate) new FileManager(CERTIFICATE_FILE_PATH + certPath).loadObjects();
			System.out.println("certClient: " + certClient);
			PublicKey pubKey = certClient.getPublicKey();
			userList.put(user,new User(user,pubKey));
		}
		//TODO
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
	 * @param userCert the user's name
	 * @throws ClassNotFoundException 
	 * @throws IOException if an error occurs whilst writing in the user's database
	 */
	public void addUser(User user, Certificate userCert) throws IOException {
		String str = user.getUsername() + "," + user.getUsername() + ".cert" + "\n";
        file.writeFile(str);
        userList.put(user.getUsername(), user);
	}
	
	
}
