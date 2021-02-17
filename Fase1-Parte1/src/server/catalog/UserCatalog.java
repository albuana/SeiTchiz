package server.catalog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
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
		createFile();
		initiateUserList();


	}

	private void createFile(){
		File loginInfo = new File("loginInfo.txt");

		if(!loginInfo.exists())
			try {
				loginInfo.createNewFile();
				System.out.println("inicializa2");

			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	private void writeFile(String userID2, String password) throws IOException {
	    String str = "\n"+userID2+ " " + password;
	    FileOutputStream outputStream = new FileOutputStream("loginInfo.txt");
	    byte[] strToBytes = str.getBytes();
	    outputStream.write(strToBytes);
	    outputStream.close();
	}
	
	private void initiateUserList() throws IOException {
	    BufferedReader read = new BufferedReader( new FileReader ("loginInfo.txt"));
	    String line;
	    String username;
	    String pass;
	    read.readLine();
	    while((line=read.readLine()) != null) {
	    	String[] parts = line.split(" ");
	    	username=parts[0];
	    	pass=parts[1];
	    	//N�o se usa o addUser porque no LoginUserHandler usa-se o addUser para registar(estaria a dobrar no ficheiro)
	    	userList.put(username, new User(username,pass));
	    }
	    read.close();
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
	public void addUser(User user) throws IOException {
		writeFile(user.getUsername(),user.getPassword());
		userList.put(user.getUsername(), user);
	}
}
