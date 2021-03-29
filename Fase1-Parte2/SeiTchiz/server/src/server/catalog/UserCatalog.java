package server.catalog;


import java.io.IOException;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.security.cert.Certificate;

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
	
	public static final String CERTIFICATE_FILE_PATH = Server.DATA_PATH + "pubKeys/";
	


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
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	private UserCatalog () throws IOException, ClassNotFoundException {
		userList = new HashMap<>();
		List<String> currentUsersList = new FileManager(USERS_FILE_PATH,"users.txt").loadContent();
	
		for(int i=0;i<currentUsersList.size();i++) {
			
			String username = currentUsersList.get(i).substring(0,currentUsersList.get(i).indexOf(":"));
			String certPath = currentUsersList.get(i).substring(currentUsersList.get(i).indexOf(":") + 1);
			Certificate certificateClient = (Certificate) new FileManager(certPath).loadObjects().get(0);
			PublicKey pubKey = certificateClient.getPublicKey();
			
			userList.put(username,new User(username,pubKey));
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
//	public void addUser(User user, String name) throws IOException {
//		String str = name + ":" + user.getUsername() + ":" + user.getPassword() + "\n";
//		file.writeFile(str);
//		userList.put(user.getUsername(), user);
//	}


	public void addUser(User user, Certificate userCert) throws ClassNotFoundException, IOException{
		userList.put(user.getUsername(), user);

		FileManager fileCertificates = new FileManager(CERTIFICATE_FILE_PATH+user.getUsername()+".cert");
		fileCertificates.writeObjects(userCert);

		new FileManager(USERS_FILE_PATH).writeContent(user.getUsername() + ":" + CERTIFICATE_FILE_PATH+user.getUsername()+".cert");
	}


}
