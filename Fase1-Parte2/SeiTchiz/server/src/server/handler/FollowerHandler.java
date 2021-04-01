package server.handler;

import java.io.IOException;
import java.security.cert.CertificateException;

import server.catalog.UserCatalog;
import server.domain.Follow;
import server.domain.User;
import server.exceptions.UserNotExistException;
import server.exceptions.follow.CantUnfollowException;
import server.exceptions.follow.UserAlreadyBeingFollowedException;
import server.exceptions.follow.UserCantFollowHimselfException;
import server.exceptions.follow.UserHaveNoFollowersException;

/**
 * Handles the following, unfollow and seeing followers of a user
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 *
 */
public class FollowerHandler {
	
	private User userID; //abrange os que se vai seguir e deixar de seguir
	private User currentUserID;

	/**
	 * Follow Handler
	 * @param userID
	 * @param currentUserID
	 * @return 
	 * @throws UserNotExistException
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 * @throws CertificateException 
	 * @throws UserAlreadyBeingFollowedException 
	 * @throws UserAlreadyFollowedException 
	 * @throws UserFollowingHimSelfException
	 */
	public FollowerHandler(String userID, User currentUserID) throws UserNotExistException, IOException, UserCantFollowHimselfException, ClassNotFoundException, CertificateException {
		
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

	
	public String follow() throws  IOException, UserNotExistException, UserAlreadyBeingFollowedException, ClassNotFoundException {
		return Follow.getInstance().follow(userID, currentUserID);
	}

	public String unfollow() throws  IOException, UserNotExistException, CantUnfollowException, UserHaveNoFollowersException, ClassNotFoundException {

		return Follow.getInstance().unfollow(userID, currentUserID);
	}
	
	public String viewFollowers() throws  IOException, UserHaveNoFollowersException, ClassNotFoundException {

		return Follow.getInstance().viewFollowers(currentUserID);
	}


}

