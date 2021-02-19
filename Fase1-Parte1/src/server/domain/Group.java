package server.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import server.FileManager;
import server.Server;
import server.catalog.UserCatalog;
import server.exceptions.group.UserAlreadyInGroupException;
import server.exceptions.group.UserDoesNotInGroupException;


public class Group {
	public User owner;
	public String groupID;
	public ArrayList<User> membersList;

	public FileManager messages;
	public FileManager members;
	public ArrayList<String> messagesHistory;


	/**
	 * 
	 * @param groupID the name of the name
	 * @param owner the owner of the group
	 * @param key the group key
	 * @throws IOException if anything happens whilst writing in the group groupInfo file
	 * @throws ClassNotFoundException 
	 */
	public Group(String groupID, User owner) throws IOException {
		this.owner=owner;
		this.groupID=groupID;
		this.membersList=new ArrayList<>();
		String pathMessages= Server.DATA_PATH + "groups/" + groupID + "/" + "message.txt";
		String pathMembers= Server.DATA_PATH + "groups/" + groupID + "/" + "members.txt";
		this.messages=new FileManager(pathMessages);
		this.members=new FileManager(pathMembers);
		initializeMembersList();
		initializeMessages();

	}

	private void initializeMessages() throws IOException {
		ArrayList<String> list=messages.fileToList();
		for(int i=0;i<list.size();i++) {
			String message=list.get(i);
			messagesHistory.add(message);
				
		}
		
	}

	private void initializeMembersList() throws IOException {
		ArrayList<String> list=members.fileToList();
		UserCatalog users = UserCatalog.getInstance();
		for(int i=0;i<list.size();i++) {
			String user=list.get(i);
			membersList.add(users.getUser(user));		
		}
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
		return membersList;
	}

	/**
	 * adds a new member to the group
	 * @param user the new user
	 * @throws IOException if it wasn't possible to write in the groupInfo file
	 * @throws UserAlreadyInGroupException if the given user is already in the group
	 */
	public void addMember(User user) throws IOException, UserAlreadyInGroupException {
		if(!membersList.add(user))
			throw new UserAlreadyInGroupException();
		else {
			membersList.add(user);
			members.writeFile(user.getUsername());
		}
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
		if(!membersList.remove(user))
			throw new UserDoesNotInGroupException();
		else
			membersList.remove(user);
	}

	/**
	 * 
	 * @param user the user
	 * @return true if the given user is in this group
	 */
	public boolean hasMember(User user) {
		return membersList.contains(user);
	}


	public String info() {
		StringBuilder ret=new StringBuilder("O grupo tem: "+owner.getUsername()+" como dono /n");
		ret.append("Os utilizadores pertencentes ao grupo saho: /n");
		for (int i = 0; i < membersList.size(); i++){
			ret.append("Utilizador: "+membersList.get(i).getUsername());
		}
		return ret.toString();

	}
	
	public void sendMessage(Object object) {
		
		
	}

}
