package server.handler;

import java.io.IOException;

import server.catalog.UserCatalog;
import server.domain.Follow;
import server.domain.User;
import server.exceptions.UserNotExistException;
import server.exceptions.follow.CantUnfollowException;
import server.exceptions.follow.UserAlreadyBeingFollowedException;
import server.exceptions.follow.UserCantFollowHimselfException;
import server.exceptions.follow.UserHaveNoFollowersException;

public class FollowerHandler {
	
	private User userID; //abrange os que se vai seguir e deixar de seguir
	private User currentUserID;

	/**
	 * Constructor
	 * @throws UserNotExistException 
	 * @throws IOException 
	 * @throws UserCantFollowHimselfException 
	 */
	public FollowerHandler(String userID, User currentUserID) throws UserNotExistException, IOException, UserCantFollowHimselfException {
		
		this.userID = UserCatalog.getInstance().getUser(userID);
		this.currentUserID = currentUserID;
		
		if(this.userID == null){
			throw new UserNotExistException();
		}
		
		if(this.userID.equals(currentUserID)) {
			throw new UserCantFollowHimselfException() ;
		}
	}

	public FollowerHandler(User currentUserID) {
		this.currentUserID = currentUserID;
	}

	/**
	 * Follow Handler
	 * @param userID
	 * @param currentUserID
	 * @return 
	 * @return
	 * @throws UserNotExistException
	 * @throws IOException
	 * @throws UserAlreadyBeingFollowedException 
	 * @throws UserAlreadyFollowedException 
	 * @throws UserFollowingHimSelfException
	 */
	public String follow() throws  IOException, UserNotExistException, UserAlreadyBeingFollowedException {
		return Follow.getInstance().follow(userID, currentUserID);
	}

	public String unfollow() throws  IOException, UserNotExistException, CantUnfollowException, UserHaveNoFollowersException {

		return Follow.getInstance().unfollow(userID, currentUserID);
	}
	
	public String viewFollowers() throws  IOException, UserHaveNoFollowersException {

		return Follow.getInstance().viewFollowers(currentUserID);
	}


}

