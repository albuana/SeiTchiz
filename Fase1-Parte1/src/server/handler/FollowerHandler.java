package server.handler;

import java.io.IOException;

import server.domain.Follow;
import server.exceptions.UserNotExistException;

public class FollowerHandler {

	public FollowerHandler() {
		
	}
	
	public String follow(String toFollowId) throws UserNotExistException, IOException {
		Follow.getInstance().follow(toFollowId);
		return "User com ID "+toFollowId+" foi seguido";
	}

}
