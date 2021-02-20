package server.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import server.catalog.UserCatalog;
import server.exceptions.UserNotExistException;

public class Follow {


	private static Follow INSTANCE = null;
	private static HashMap<String, ArrayList<User>> userIDfollowersList;

	/**
	 * Follow private constructor
	 */
	private Follow() {
		userIDfollowersList = new HashMap<String, ArrayList<User>>();
	}

	public static Follow getInstance() {
		if(INSTANCE == null)
			INSTANCE = new Follow();
		return INSTANCE;
	}

	/**
	 * Follow an user
	 * @param userID
	 * @param currentUserID
	 * @return
	 * @throws UserNotExistException
	 * @throws IOException
	 * @throws UserAlreadyFollowedException
	 * @throws UserFollowingHimSelfException 
	 */
	public String follow(User userID, String currentUserID) throws IOException  {


		//This condition verifies if the user exists
		if(userID == null || UserCatalog.getInstance().getUser(userID.getUsername())==null) {
			return "O user nao existe";
		}else
			//This checks if the user to follow is not him self
			if(userID.getUsername().equals(currentUserID)) {
				return "O user nao se pode seguir a ele proprio";


			}else
				if(Follow.userIDfollowersList.containsKey(currentUserID)) {
					//if the user is already in the list it checks if the user to follow is not already being followed
					if(Follow.userIDfollowersList.get(currentUserID).contains(userID)) {
						try {
							return userID.getUsername()+" já esta a ser seguido";
						} catch (Exception e) {

							e.printStackTrace();
						}
					}

					//If it is not it will add the new following to the the list
					Follow.userIDfollowersList.get(currentUserID).add(userID);
					return userID.getUsername()+" foi seguido";

				}else {

					//If it is the first following of the current user it will create a new entry on de hasMap with the new follwed
					ArrayList<User> followerList = new ArrayList<User>();
					followerList.add(userID);
					Follow.userIDfollowersList.put(currentUserID, followerList);
					return userID.getUsername()+" foi seguido";
				}

	}

}
