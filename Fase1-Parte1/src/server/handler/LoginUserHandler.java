package server.handler;

import java.io.FileNotFoundException;
import java.io.IOException;

import server.catalog.UserCatalog;
import server.domain.User;
import server.exceptions.UserCouldNotLoginException;


/**
 * 
 * Handles login or registration of user
 *
 */
public class LoginUserHandler {

	private String userID;

	//DEVE RECEBER PASSWORD?
	private String passwordUser;

	public LoginUserHandler(String userID, String passwordUser) throws FileNotFoundException{
		this.userID = userID;
		this.passwordUser = passwordUser;

	}

	/**
	 * 
	 * @return flag for if the is already registered
	 * @throws IOException 
	 */
	public boolean loginGetFlag() throws IOException {
		Boolean flag = false;

		UserCatalog users = UserCatalog.getInstance();
		if(users.getUser(userID) == null) {
//			System.out.println(users.getUser(userID) == null);
			flag = true;
		}
//		System.out.println("Se esta flag der false entao deve fazer login: " + flag);
		return flag;
	}

	public boolean register(String userID2, String password) throws IOException {
		UserCatalog users = UserCatalog.getInstance();
		User newUser=new User(userID2, password);
		if(users.getUser(userID2)==null) {
			users.addUser(newUser);
			return true;
		}
		return false;
	}


	public boolean login(String userID, String password) throws IOException, UserCouldNotLoginException {
		UserCatalog users = UserCatalog.getInstance();
		User loginUser=users.getUser(userID);
		if(loginUser!=null) {
//			System.out.println(loginUser.getPassword());
//			System.out.println(password);

			if(loginUser.getPassword().equals(password)) {
				return true;
			} else {
				throw new UserCouldNotLoginException();
			}
		}
		return false;
	}

}
