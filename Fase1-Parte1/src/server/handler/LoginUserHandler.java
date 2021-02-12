package server.handler;

import server.catalog.UserCatalog;


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

}
