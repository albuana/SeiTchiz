package client.handler;

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
	 */
	public static Object newgroup(String groupID) {
		return new CreateNewGroupHandler(groupID).newgroup();

	}

}
