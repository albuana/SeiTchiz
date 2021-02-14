package server.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import server.exceptions.group.UserAlreadyInGroupException;
import server.exceptions.group.UserDoesNotInGroupException;


public class Group {
	public User owner;
	public String groupID;
	public ArrayList<User> members;

	/**
	 * 
	 * @param groupID the name of the name
	 * @param owner the owner of the group
	 * @param key the group key
	 * @throws IOException if anything happens whilst writing in the group groupInfo file
	 * @throws ClassNotFoundException 
	 */
	public Group(String groupID, User owner) {
		this.owner=owner;
		this.groupID=groupID;
		this.members=new ArrayList<>();
	}

	/**
	 * 
	 * @return the name of this group
	 */
	public String getGroupID() {
		return groupID;
	}

	/**
	 * 
	 * @return the owner of this group
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * 
	 * @return the members of this group
	 */
	public ArrayList<User> getUsers() {
		return members;
	}

	/**
	 * adds a new member to the group
	 * @param user the new user
	 * @throws IOException if it wasn't possible to write in the groupInfo file
	 * @throws UserAlreadyInGroupException if the given user is already in the group
	 */
	public void addMember(User user) throws IOException, UserAlreadyInGroupException {
		if(!members.add(user))
			throw new UserAlreadyInGroupException();
		else
			members.add(user);
	}

	/**
	 * remove a member to the group
	 * @param user the user
	 * @throws UserDoesNotInGroupException 
	 * @throws IOException if an error occurs whilst writing in the groupInfo file
	 * @throws ClassNotFoundException
	 * @trowns UserDoesNotBelongToGroupException if the given user is not in the group
	 */
	public void removeMember(User user) throws UserDoesNotInGroupException {
		if(!members.remove(user))
			throw new UserDoesNotInGroupException();
		else
			members.remove(user);
	}

	/**
	 * 
	 * @param user the user
	 * @return true if the given user is in this group
	 */
	public boolean hasMember(User user) {
		return members.contains(user);
	}


	public String info() {
		StringBuilder ret=new StringBuilder("O grupo tem: "+owner.getUsername()+" como dono /n");
		ret.append("Os utilizadores pertencentes ao grupo saho: /n");
		for (int i = 0; i < members.size(); i++){
			ret.append("Utilizador: "+members.get(i).getUsername());
		}
		return ret.toString();

	}
	
	public void sendMessage(Object object) {
		
		
	}

}
