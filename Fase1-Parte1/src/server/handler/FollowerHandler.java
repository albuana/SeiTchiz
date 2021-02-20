package server.handler;

import java.io.IOException;

import server.domain.Follow;
import server.domain.User;
import server.exceptions.UserNotExistException;

public class FollowerHandler {
	
	/**
	 * Constructor
	 */
	public FollowerHandler() {
		
	}
	
	/**
	 * Follow Handler
	 * @param userID
	 * @param currentUserID
	 * @return
	 * @throws UserNotExistException
	 * @throws IOException
	 * @throws UserAlreadyFollowedException
	 * @throws UserFollowingHimSelfException
	 */
	public String follow(User userID, String currentUserID) throws  IOException {
		Follow.getInstance().follow(userID, currentUserID);
		return "User com ID "+userID+" foi seguido";
	}

}
