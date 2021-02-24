package server.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import server.Server;
import server.catalog.UserCatalog;
import server.exceptions.UserNotExistException;

public class Follow {


	private static Follow INSTANCE = null;
	private static final String FOLLOWS_DIRECTORY = Server.DATA_PATH+"follows/";

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
	 * @param userID
	 * @param currentUserID
	 * @return
	 * @throws UserNotExistException
	 * @throws IOException
	 * @throws UserAlreadyFollowedException
	 * @throws UserFollowingHimSelfException 
	 */
	public String follow(User userID, String currentUserID) throws IOException  {

		if(verifyUserExist(userID) && verifyUserFollowingHimSelf(userID, currentUserID)) {
			File dir = new File(FOLLOWS_DIRECTORY);
			File[] directoryListing = dir.listFiles();

			//Iterate on directory follows
			for(File userFile: directoryListing) {

				//Checks if any file has his name
				if(userFile.getName().equals(currentUserID+".txt")){

					Scanner sc = new Scanner(userFile);

					//Search through the file
					while (sc.hasNextLine()) {
						String line = sc.nextLine();

						//Checks if userID is already being followed
						if(line.equals(userID.getUsername())) {
							sc.close();
							return userID.getUsername()+" já esta a ser seguido/a";
						}
					}
					
					//Write on file if the user to follow is not already being followed
					Files.write(userFile.toPath(), (userID.getUsername()+"\n").getBytes(), StandardOpenOption.APPEND);
					sc.close();
					return userID.getUsername()+" foi seguido/a";
				}

			}
			//At this point it has been conclude that the current user does not have a file

			File newFile = new File(FOLLOWS_DIRECTORY+currentUserID+".txt");
			newFile.createNewFile();
			Files.write(newFile.toPath(), (userID.getUsername()+"\n").getBytes(), StandardOpenOption.APPEND);
			return userID.getUsername()+" foi seguido/a";
		}
		return userID.getUsername()+" foi seguido/a";
	}

	/**
	 * unfollow an user
	 * @param userID
	 * @param currentUserID
	 * @return
	 * @throws IOException
	 */
	public String unfollow(User userID, String currentUserID) throws IOException  {

		if(verifyUserExist(userID) && verifyUserFollowingHimSelf(userID, currentUserID)) {


			File dir = new File(FOLLOWS_DIRECTORY);
			File[] directoryListing = dir.listFiles();
			StringBuilder newFile = new StringBuilder();
			boolean deleted = false;

			//Iterate on directory follows
			for(File userFile: directoryListing) {
				
				//Checks if any file has his name
				if(userFile.getName().equals(currentUserID+".txt")){

					Scanner sc = new Scanner(userFile);
					
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
					Files.write(userFile.toPath(), newFile.toString().getBytes());

					if(deleted) 
						return userID.getUsername()+" já nao esta a ser seguido/a";
				}
			}
			return "O user nao tem seguidores"; 
		}
		return "O user nao tem seguidores";
	}
	/**
	 * return a string of followed users
	 * @return
	 * @throws FileNotFoundException 
	 */
	public String viewFollowers(String userID) throws FileNotFoundException {
		
		StringBuilder ret = new StringBuilder();
		File dir = new File(FOLLOWS_DIRECTORY);
		File[] directoryListing = dir.listFiles();

		ret.append("[ ");

		//Iterate on directory follows
		for(File userFile: directoryListing) {
			
			String fname = userFile.getName();
			int pos = fname.lastIndexOf(".");
			fname = fname.substring(0, pos);

			if(!fname.equals(userID)) {
				Scanner sc = new Scanner(userFile);
				
				//Search through the file
				while(sc.hasNextLine()) {
					String line = sc.nextLine();
					if(line.equals(userID)) {
						ret.append(fname+", ");
					}
				}
				
				sc.close();
			}
		}
		ret.append("]");

		if(ret.toString().equals("[ ]"))
			return "O user nao tem ninguem a o seguir";

		return ret.toString();
	}

	
	
	
	private boolean verifyUserExist(User userID) throws IOException {
		//This condition verifies if the user exists
		if(userID == null || UserCatalog.getInstance().getUser(userID.getUsername())==null) 
			return false;

		return true;
	}

	private boolean verifyUserFollowingHimSelf(User userID, String currentUserID) {
		//This checks if the user to unfollow is not him self
		if(userID.getUsername().equals(currentUserID)) 
			return false;

		return true;
	}
}
