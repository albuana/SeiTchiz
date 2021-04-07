package server.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import server.FileManager;
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

	/**
	 * Follow private constructor
	 * @throws IOException 
	 */
	private Follow() throws IOException {
		Path path = Paths.get("Fase1-Parte2/SeiTchiz/server/Data/follows/");
		Files.createDirectories(path);
		path = Paths.get("Fase1-Parte2/SeiTchiz/server/Data/follows/");
		Files.createDirectories(path);
	}

	public static Follow getInstance() throws IOException {
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

		Path path = Paths.get("Fase1-Parte2/SeiTchiz/server/Data/follows/"+currentUserID.getUsername()+"/");
		Files.createDirectories(path);
		path = Paths.get("Fase1-Parte2/SeiTchiz/server/Data/follows/"+userToFollow.getUsername()+"/");
		Files.createDirectories(path);
		FileManager followingCurrentUserID=new FileManager("Fase1-Parte2/SeiTchiz/server/Data/follows/"+currentUserID.getUsername()+"/"+"following.txt");
		FileManager followersUserToFollow=new FileManager("Fase1-Parte2/SeiTchiz/server/Data/follows/"+userToFollow.getUsername()+"/"+"follower.txt");


		List<String> followingList=followingCurrentUserID.loadContent();
		
		if(followingList.contains(userToFollow.getUsername()))
			throw new UserAlreadyBeingFollowedException();
		else {
			followingCurrentUserID.writeContent(userToFollow.getUsername());
			followersUserToFollow.writeContent(currentUserID.getUsername());
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

		FileManager followingCurrentUserID=new FileManager("Fase1-Parte2/SeiTchiz/server/Data/follows/"+currentUserID.getUsername()+"/"+"following.txt");
		FileManager followersUserToUnFollow=new FileManager("Fase1-Parte2/SeiTchiz/server/Data/follows/"+userToUnfollow.getUsername()+"/"+"follower.txt");

		List<String> followingList=followingCurrentUserID.loadContent();
		List<String> followerList=followersUserToUnFollow.loadContent();
		
		if(followingList.isEmpty())
			throw new CantUnfollowException();
		
		if(followingList.contains(userToUnfollow.getUsername())) {
			followingCurrentUserID.removeAll();
			followersUserToUnFollow.removeAll();
			for(String s:followingList) {
				if(!s.equals(userToUnfollow.getUsername())) {
					followingCurrentUserID.writeContent(s);
				}
			}
			for(String s:followerList) {
				if(!s.equals(currentUserID.getUsername())) {
					followersUserToUnFollow.writeContent(s);
				}
			}
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

		FileManager followers=new FileManager("Fase1-Parte2/SeiTchiz/server/Data/follows/"+userID.getUsername()+"/"+"follower.txt");
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