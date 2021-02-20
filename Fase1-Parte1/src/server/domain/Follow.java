package server.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import server.catalog.UserCatalog;
import server.exceptions.UserAlreadyFollowedException;
import server.exceptions.UserFollowingHimSelfException;
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
	public boolean follow(User userID, String currentUserID) throws UserNotExistException, IOException, UserAlreadyFollowedException, UserFollowingHimSelfException {


		//This condition verifies if the user exists
		if(UserCatalog.getInstance().getUser(userID.getUsername())==null)
			throw new UserNotExistException();
		
		//This checks if the user to follow is not him self
		if(userID.getUsername().equals(currentUserID))
			throw new UserFollowingHimSelfException();



		if(Follow.userIDfollowersList.containsKey(currentUserID)) {
			//if the user is already in the list it checks if the user to follow is not already being followed
			if(Follow.userIDfollowersList.get(currentUserID).contains(userID)) {
				try {
					throw new UserAlreadyFollowedException();
				} catch (Exception e) {

					e.printStackTrace();
				}
			}

			//If it is not it will add the new following to the the list
			Follow.userIDfollowersList.get(currentUserID).add(userID);

		}else {

			//If it is the first following of the current user it will create a new entry on de hasMap with the new follwed
			ArrayList<User> followerList = new ArrayList<User>();
			followerList.add(userID);
			Follow.userIDfollowersList.put(currentUserID, followerList);
		}
		return true;

	}

}
