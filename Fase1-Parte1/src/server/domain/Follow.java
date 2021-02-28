package server.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;


import server.Server;
import server.exceptions.UserNotExistException;
import server.exceptions.follow.CantUnfollowException;
import server.exceptions.follow.UserAlreadyBeingFollowedException;
import server.exceptions.follow.UserHaveNoFollowersException;

public class Follow {

	private User userToFollow;

	private ArrayList<User> membersList;

	private static Follow INSTANCE;
	private static final String FOLLOWS_DIRECTORY = Server.DATA_PATH+"follows/";
	private static final String FOLLOWERS_INFO_FILE_NAME = "followers.txt";
	private static final String FOLLOWING_INFO_FILE_NAME = "following.txt";


	/**
	 * Follow private constructor
	 */
	private Follow() {
		this.membersList=new ArrayList<>();
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
	 * @return
	 * @throws UserNotExistException
	 * @throws IOException
	 * @throws UserAlreadyBeingFollowedException 
	 * @throws UserAlreadyFollowedException
	 * @throws UserFollowingHimSelfException 
	 */
	public String follow(User userToFollow, User currentUserID) throws IOException, UserNotExistException, UserAlreadyBeingFollowedException  {

		boolean currentUserHasFolder = false;
		boolean userToFollowHasFolder = false;

		File followsDirTest = new File(FOLLOWS_DIRECTORY);
		File[] directoryListingTeste = followsDirTest.listFiles();

		for(File userFolder: directoryListingTeste) {
			if(userFolder.getName().equals(userToFollow.getUsername())) {
				userToFollowHasFolder = true;
			}

			if(userFolder.getName().equals(currentUserID.getUsername())) {
				currentUserHasFolder = true;
			}
		}

		if(!currentUserHasFolder) {
			createDirectory(Paths.get(FOLLOWS_DIRECTORY+"/"+currentUserID));
			createFile(FOLLOWS_DIRECTORY+"/"+currentUserID+"/followers.txt");
			createFile(FOLLOWS_DIRECTORY+"/"+currentUserID+"/following.txt");
		}

		if(!userToFollowHasFolder) {
			createDirectory(Paths.get(FOLLOWS_DIRECTORY+"/"+userToFollow.getUsername()));
			createFile(FOLLOWS_DIRECTORY+"/"+userToFollow.getUsername()+"/followers.txt");
			createFile(FOLLOWS_DIRECTORY+"/"+userToFollow.getUsername()+"/following.txt");
		}

		File followsDir = new File(FOLLOWS_DIRECTORY);
		File[] directoryListing = followsDir.listFiles();


		for(File userFolder: directoryListing) {
			if(userFolder.getName().equals(userToFollow.getUsername())) {

				File userToFollowDir = new File(FOLLOWS_DIRECTORY+"/"+userToFollow.getUsername());
				File[] directoryListingFollow = userToFollowDir.listFiles();


				Scanner sc = new Scanner(directoryListingFollow[0]);

				//Search through the file
				while (sc.hasNextLine()) {
					String line = sc.nextLine();

					//Checks if userID is already being followed
					if(line.equals(currentUserID.getUsername())) {
						sc.close();
						throw new UserAlreadyBeingFollowedException();
					}

				}

				Files.write(Paths.get(directoryListingFollow[0].getPath()),(currentUserID+"\n").getBytes(), StandardOpenOption.APPEND);
			}
		}

		for(File userFolder: directoryListing) {
			if(userFolder.getName().equals(userToFollow.getUsername())) {

				File currentUserDir = new File(FOLLOWS_DIRECTORY+"/"+currentUserID);
				File[] directoryListingCurrent = currentUserDir.listFiles();

				Scanner sc = new Scanner(directoryListingCurrent[1]);

				//Search through the file
				while (sc.hasNextLine()) {
					String line = sc.nextLine();

					//Checks if userID is already being followed
					if(line.equals(currentUserID.getUsername())) {
						sc.close();
						throw new UserAlreadyBeingFollowedException();
					}

				}

				Files.write(Paths.get(directoryListingCurrent[1].getPath()),(userToFollow.getUsername()+"\n").getBytes(), StandardOpenOption.APPEND);
			}
		}

		return userToFollow.getUsername()+" foi seguido/a";


	}


	/**
	 * unfollow an user
	 * @param userID
	 * @param currentUserID
	 * @return
	 * @throws IOException
	 * @throws CantUnfollowException 
	 * @throws UserHaveNoFollowersException 
	 */
	public String unfollow(User userID, User currentUserID) throws IOException, CantUnfollowException, UserHaveNoFollowersException  {


		File followsDir = new File(FOLLOWS_DIRECTORY);
		File[] directoryListing = followsDir.listFiles();
		StringBuilder newFile = new StringBuilder();
		boolean deleted = false;
		boolean folderExist = false;


		for(File userFolder: directoryListing) {

			if(userFolder.getName().equals(currentUserID.getUsername())) {

				File currentUserDir = new File(FOLLOWS_DIRECTORY+"/"+currentUserID);
				File[] directoryListingCurrent = currentUserDir.listFiles();
				folderExist = true;

				if(directoryListingCurrent[1].length() == 0)
					throw new UserHaveNoFollowersException();

				Scanner sc = new Scanner(directoryListingCurrent[1]);


				//Search through the file
				while (sc.hasNextLine()) {
					String line = sc.nextLine();

					//Delete line
					if(line.equals(userID.getUsername()) && !deleted) {
						deleted=true;
					}else {
						newFile.append(line+"\n");
					}
				}

				sc.close();

				//Rewrite the file without the use to unfollow
				Files.write(directoryListingCurrent[1].toPath(), newFile.toString().getBytes());

			}
		}



		if(!folderExist)
			throw new UserHaveNoFollowersException();
		
		if(!deleted)
			throw new CantUnfollowException();



		for(File userFolder: directoryListing) {
			if(userFolder.getName().equals(userID.getUsername())) {

				File currentUserDir = new File(FOLLOWS_DIRECTORY+"/"+userID.getUsername());
				File[] directoryListingCurrent = currentUserDir.listFiles();


				Scanner sc = new Scanner(directoryListingCurrent[0]);

				//Search through the file
				while (sc.hasNextLine()) {
					String line = sc.nextLine();

					//Delete line
					if(line.equals(currentUserID.getUsername())) {

					}else {
						newFile.append(line+"\n");
					}
				}

				sc.close();

				//Rewrite the file without the use to unfollow
				Files.write(directoryListingCurrent[0].toPath(), newFile.toString().getBytes());

			}
		}
		return userID.getUsername()+" has been unfollowed";
	}


	/**
	 * return a string of followed users
	 * @return
	 * @throws FileNotFoundException 
	 * @throws UserHaveNoFollowersException 
	 */
	public String viewFollowers(User userID) throws FileNotFoundException, UserHaveNoFollowersException {

		StringBuilder ret = new StringBuilder();
		File dir = new File(FOLLOWS_DIRECTORY);
		File[] directoryListing = dir.listFiles();

		ret.append("Followers: ");

		//Iterate on directory follows
		for(File userFolder: directoryListing) 

			if(userFolder.getName().equals(userID.getUsername())) {

				File currentUserDir = new File(FOLLOWS_DIRECTORY+"/"+userID);
				File[] directoryListingCurrent = currentUserDir.listFiles();

				Scanner sc = new Scanner(directoryListingCurrent[0]);

				//Search through the file
				while (sc.hasNextLine()) {
					String line = sc.nextLine();

					ret.append(line+", ");
				}
			}
		ret.append(" ");

		if(ret.toString().equals("Followers:  "))
			throw new UserHaveNoFollowersException();

		return ret.toString();
	}


	private void createDirectory(Path path) throws IOException {
		Files.createDirectories(path);
	}

	private File createFile(String path) throws IOException {
		File newFile = new File(path);
		newFile.createNewFile();
		return  newFile;
	}


}
