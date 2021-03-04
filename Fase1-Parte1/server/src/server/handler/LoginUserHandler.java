package server.handler;

import java.io.FileNotFoundException;
import java.io.IOException;

import server.catalog.UserCatalog;
import server.domain.User;
import server.exceptions.UserCouldNotLoginException;


/**
 * Handles login or registration of user
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class LoginUserHandler {

	private String userID;

	private String passwordUser;

	public LoginUserHandler(String userID, String passwordUser) throws FileNotFoundException{
		this.userID = userID;
		this.passwordUser = passwordUser;

	}

	/**
	 * @return flag for if the is already registered
	 * @throws IOException 
	 */
	public boolean loginGetFlag() throws IOException {
		Boolean flag = false;

		UserCatalog users = UserCatalog.getInstance();
		if(users.getUser(userID) == null) {
			flag = true;
		}
		return flag;
	}

	/**
	 * @param userID
	 * @param password
	 * @return
	 * @throws IOException
	 */
	public boolean register(String userID, String password) throws IOException {
		UserCatalog users = UserCatalog.getInstance();
		User newUser=new User(userID, password);
		if(users.getUser(userID)==null) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param userID
	 * @param password
	 * @return
	 * @throws IOException
	 * @throws UserCouldNotLoginException
	 */
	public boolean login(String userID, String password) throws IOException, UserCouldNotLoginException {
		UserCatalog users = UserCatalog.getInstance();
		User loginUser=users.getUser(userID);
		if(loginUser!=null) {
			if(loginUser.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param name
	 * @throws IOException
	 */
	public void registerToFile(String name) throws IOException {
		UserCatalog users = UserCatalog.getInstance();
		User newUser=new User(userID, passwordUser);
		users.addUser(newUser, name);
	}
}