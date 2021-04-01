package server.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import server.FileManager;
import server.Server;
import server.exceptions.follow.CantUnfollowException;
import server.exceptions.follow.UserAlreadyBeingFollowedException;
import server.exceptions.follow.UserHaveNoFollowersException;

/**
 * 
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class Follow {

	@SuppressWarnings("unused")
	private User userToFollow;

	private static Follow INSTANCE;
	private static final String FOLLOWS_DIRECTORY = Server.DATA_PATH+"follows/";
	private static final String FOLLOWERS_INFO_FILE_NAME = "followers.txt";
	private static final String FOLLOWING_INFO_FILE_NAME = "following.txt";


	/**
	 * Follow private constructor
	 */
	private Follow() {
	}

	public static Follow getInstance() {
		if(INSTANCE == null)
			INSTANCE = new Follow();
		return INSTANCE;
	}


	/**
	 * Follow an user
	 * @param userToFollow
	 * @param currentUserID
	 * @return the user that has been followed
	 * @throws IOException
	 * @throws UserAlreadyBeingFollowedException 
	 * @throws ClassNotFoundException 
	 */
	public String follow(User userToFollow, User currentUserID) throws IOException, UserAlreadyBeingFollowedException, ClassNotFoundException  {

		
		FileManager followingCurrentUserID=new FileManager(FOLLOWS_DIRECTORY+currentUserID.getUsername()+"/",FOLLOWING_INFO_FILE_NAME);
		FileManager followersUserToFollow=new FileManager(FOLLOWS_DIRECTORY+userToFollow.getUsername()+"/",FOLLOWERS_INFO_FILE_NAME);;


		List<String> followingList=followingCurrentUserID.loadContent();
		
		if(followingList.contains(userToFollow.getUsername()))
			throw new UserAlreadyBeingFollowedException();
		else {
			followingCurrentUserID.writeFile(userToFollow.getUsername()+"\n");
			followersUserToFollow.writeFile(currentUserID.getUsername()+"\n");
		}
		return userToFollow.getUsername()+" foi seguido/a.";

	}
	
	
	/**
	 * Unfollow an user
	 * @param userID
	 * @param currentUserID
	 * @return the user that has been unfollowed
	 * @throws IOException
	 * @throws CantUnfollowException 
	 * @throws UserHaveNoFollowersException 
	 * @throws ClassNotFoundException 
	 */
	public String unfollow(User userToUnfollow, User currentUserID) throws IOException, CantUnfollowException, UserHaveNoFollowersException, ClassNotFoundException{

		FileManager followingCurrentUserID=new FileManager(FOLLOWS_DIRECTORY+currentUserID.getUsername()+"/",FOLLOWING_INFO_FILE_NAME);;
		FileManager followersUserToUnFollow=new FileManager(FOLLOWS_DIRECTORY+userToUnfollow.getUsername()+"/",FOLLOWERS_INFO_FILE_NAME);

		List<String> followingList=followingCurrentUserID.loadContent();
		
		if(followingList.isEmpty())
			throw new CantUnfollowException();
		
		if(followingList.contains(userToUnfollow.getUsername())) {
			followingCurrentUserID.removeFromFile(userToUnfollow.getUsername());
			followersUserToUnFollow.removeFromFile(currentUserID.getUsername());
		}
		else 
			throw new CantUnfollowException();
		
		return userToUnfollow.getUsername()+" has been unfollowed.";
	}


	/**
	 * Return a string of followed users
	 * @return
	 * @throws UserHaveNoFollowersException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public String viewFollowers(User userID) throws UserHaveNoFollowersException, IOException, ClassNotFoundException {

		StringBuilder ret = new StringBuilder();

		FileManager followers=new FileManager(FOLLOWS_DIRECTORY+userID.getUsername()+"/",FOLLOWERS_INFO_FILE_NAME);
		List<String> followersList=followers.loadContent();
		
		if(followersList.isEmpty())
			throw new UserHaveNoFollowersException();
		
		ret.append("Followers: ");
		
		for(String s:followersList) {
			ret.append(s+", ");
		}
		
		ret.deleteCharAt(ret.length() - 1);
		ret.deleteCharAt(ret.length() - 1);

		return ret.toString();
	}
}
