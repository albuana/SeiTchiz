package server.handler;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

import server.catalog.GroupCatalog;
import server.catalog.UserCatalog;
import server.domain.Group;
import server.domain.User;
import server.exceptions.UserNotExistException;
import server.exceptions.group.CantRemoveOwnerExeption;
import server.exceptions.group.GroupException;
import server.exceptions.group.GroupNotExistException;
import server.exceptions.group.UserNotOwnerException;

/**
 * Handles the remove an user in a group
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class RemoveMemberGroupHandler {
	private String groupId;
	private User owner;
	private String oldUser;
	public ArrayList<Object[]> userKeys;
	
	/**
	 * RemoveMemberGroupHandler constructor
	 * @param groupId id of the group
	 * @param owner owner of the group that is adding a new member
	 * @param newUser user that will be added
	 * @param userKeys 
	 */
	public RemoveMemberGroupHandler(String groupId, String oldUser, ArrayList<Object[]> userKeys, User owner) {
		this.groupId= groupId;
		this.owner = owner;
		this.oldUser = oldUser;
		this.userKeys=userKeys;
	}
	
	/**
	 * @return true if the action was successfully performed
	 * @throws GroupException
	 * @throws IOException
	 * @throws UserNotExistException
	 * @throws ClassNotFoundException
	 * @throws CantRemoveOwnerExeption
	 * @throws CertificateException 
	 */
	public String removeMember() throws GroupException,IOException, UserNotExistException, ClassNotFoundException, CantRemoveOwnerExeption, CertificateException {
		Group group = GroupCatalog.getInstance().getGroup(groupId);
		if(group == null) 
			throw new GroupNotExistException(); 
		if(!owner.equals(group.getOwner()))
			throw new UserNotOwnerException();
		User newbie = UserCatalog.getInstance().getUser(oldUser);
		if(newbie == null)
			throw new UserNotExistException();
		if(newbie == owner)
			throw new CantRemoveOwnerExeption();
		group.removeMember(newbie,userKeys);
		return oldUser + " was removed of the " + groupId + ".";
	}
}
