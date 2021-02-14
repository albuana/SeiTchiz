package server.handler;

import server.catalog.UserCatalog;
import server.domain.User;


/**
 * 
 * Handles login or registration of user
 *
 */
public class LoginUserHandler {
	
	private String userID;
	
	//DEVE RECEBER PASSWORD?
	private String passwordUser;

	
	//TODO

	
	public LoginUserHandler(String userID, String passwordUser){
		this.userID = userID;
		this.passwordUser = passwordUser;
	}

	/**
	 * 
	 * @return flag for if the is already registered
	 */
	public boolean loginGetFlag() {
		Boolean flag = false;

		UserCatalog users = UserCatalog.getInstance();
		if(users.getUser(userID) == null) {
			flag = true;
		}

		return flag;
	}

	public boolean register(String userID2, String password) {
		UserCatalog users = UserCatalog.getInstance();
		User newUser=new User(userID2, password);
		if(users.getUser(userID2)!=null) {
			users.addUser(newUser);
			return true;
		}
		return false;
	}

	public boolean login(String userID2, String password) {
		UserCatalog users = UserCatalog.getInstance();
		User loginUser=users.getUser(userID2);
		if(loginUser!=null) {
			if(loginUser.getPassword()==password)
				return true;
		}
		return false;
	}

}
