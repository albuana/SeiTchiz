package server.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import server.catalog.UserCatalog;
import server.exceptions.UserNotExistException;

public class Follow {


	private static Follow INSTANCE = null;
	private static HashMap<String, ArrayList<User>> userIDfollowersList;
	private static ArrayList<String> userlist;
	/**
	 * Follow private constructor
	 */
	private Follow() {
		userIDfollowersList = new HashMap<String, ArrayList<User>>();
		userlist = new  ArrayList<String>();
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
							return userID.getUsername()+" já esta a ser seguido/a";
						} catch (Exception e) {

							e.printStackTrace();
						}
					}

					//If it is not it will add the new following to the the list
					Follow.userIDfollowersList.get(currentUserID).add(userID);
					return userID.getUsername()+" foi seguido/a";

				}else {

					//If it is the first following of the current user it will create a new entry on de hasMap with the new follwed
					ArrayList<User> followerList = new ArrayList<User>();
					userlist.add(currentUserID);
					followerList.add(userID);
					Follow.userIDfollowersList.put(currentUserID, followerList);
					return userID.getUsername()+" foi seguido/a";
				}

	}

	/**
	 * unfollow an user
	 * @param userID
	 * @param currentUserID
	 * @return
	 * @throws IOException
	 */
	public String unfollow(User userID, String currentUserID) throws IOException  {


		//This condition verifies if the user exists
		if(userID == null || UserCatalog.getInstance().getUser(userID.getUsername())==null) {
			return "O user nao existe";
		}else
			//This checks if the user to unfollow is not him self
			if(userID.getUsername().equals(currentUserID)) {
				return "O user nao se pode deixar de seguir a ele proprio";

			}else
				//if the user dont have a follow list it does not have follows
				if(!Follow.userIDfollowersList.containsKey(currentUserID)) {
					return "O user nao tem seguidores";


				}else if(!Follow.userIDfollowersList.get(currentUserID).contains(userID)) {
					return "o user nao segue "+userID.getUsername();
				}else {


					Follow.userIDfollowersList.get(currentUserID).remove(userID);
					return userID.getUsername()+" já não é seguido/a";


				}

	}
	/**
	 * return a string of followed users
	 * @return
	 */
	public String viewFollowers(String userID) {
		StringBuilder ret = new StringBuilder();
		ret.append("[ ");

		for(String user : userlist) {
			if(!(userIDfollowersList.get(user).size() == 0)) {
				
				ArrayList<User> list = userIDfollowersList.get(user);
				for(User a : list) {
					
					if(a.getUsername().equals(userID))
						ret.append(user+" ,");
				}
			}
		}

		ret.append("]");
		return ret.toString();
	}


	//////////Lista de pessoas a seguir////////////

	/*
	public String viewFollowers(String userID) {
		StringBuilder ret = new StringBuilder();
		ret.append("[ ");

		if(Follow.userIDfollowersList.containsKey(userID)) {
			ArrayList list = userIDfollowersList.get(userID);
			for(int i = 0; i<list.size(); i++)
				if(i == list.size()-1)
					ret.append(list.get(i).toString()+" ");
				else
					ret.append(list.get(i).toString()+", ");

		}else {
			return "Não segue ninguem";
		}
		ret.append("]");
		return ret.toString();
	}
	 */


	//////////Lista de pessoas que seguem o user////////////
}
