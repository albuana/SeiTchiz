package server.handler;

import server.domain.User;
import server.exceptions.group.GroupAlreadyExistException;

import java.io.IOException;

import server.catalog.GroupCatalog;

/**
 * Handles creation of new group 
 */
public class CreateGroupHandler {
	private String groupID;
	private User user;
	
	/**
	 * CreateGroupHandler constructor
	 * @param groupID the name of the group to create
	 * @param user the user that is creating the group
	 */
	public CreateGroupHandler(String groupID, User user) {
		this.user = user;
		this.groupID = groupID;
	}
	
	/**
	 * @return true if the group was successfully created
	 * @throws GroupAlreadyExistException if the group already exists
	 * @throws IOException if an error occurs while creating the group
	 */
	public boolean newgroup() throws GroupAlreadyExistException, IOException {
		return GroupCatalog.getInstance().addGroup(groupID, user.getUsername());	
	}
	
}
