package server.handler;

import java.io.IOException;

import server.domain.Follow;
import server.domain.User;
import server.exceptions.UserNotExistException;

public class FollowerHandler {

	public FollowerHandler() {
		
	}
	
	public String follow(User userID) throws UserNotExistException, IOException {
		Follow.getInstance().follow(userID);
		return "User com ID "+userID+" foi seguido";
	}

}
