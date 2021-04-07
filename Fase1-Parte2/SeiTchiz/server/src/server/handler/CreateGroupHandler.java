package server.handler;

import server.domain.User;
import server.exceptions.group.GroupAlreadyExistException;

import java.io.IOException;
import java.security.cert.CertificateException;

import server.catalog.GroupCatalog;

/**
 * Handles creation of new group 
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class CreateGroupHandler {
	private String groupID;
	private User user;
	private byte[] encryptedKey;
	
	/**
	 * CreateGroupHandler constructor
	 * @param groupID the name of the group to create
	 * @param encrypted 
	 * @param user the user that is creating the group
	 */
	public CreateGroupHandler(String groupID, byte[] encrypted, User user) {
		this.user = user;
		this.groupID = groupID;
		this.encryptedKey=encrypted;
	}
	
	/**
	 * @return true if the group was successfully created
	 * @throws GroupAlreadyExistException if the group already exists
	 * @throws IOException if an error occurs while creating the group
	 * @throws ClassNotFoundException 
	 * @throws CertificateException 
	 */
	public String newgroup() throws GroupAlreadyExistException, IOException, ClassNotFoundException, CertificateException {
		GroupCatalog.getInstance().addGroup(groupID, user.getUsername(), encryptedKey);
		return "Group "+ groupID + " was successfully created.\n";	
	}
	
}
