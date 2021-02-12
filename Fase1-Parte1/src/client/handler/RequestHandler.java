package client.handler;

import client.exceptions.UserCouldNotSendException;

/**
 * 
 * Handles the requests
 *
 */
public class RequestHandler {
	
	/**
	 * Calls new group handler
	 * @param groupID
	 * @return
	 * @throws UserCouldNotSendException 
	 */
	public static Object newgroup(String groupID) throws UserCouldNotSendException {
		return new CreateNewGroupHandler(groupID).newgroup();

	}

}
