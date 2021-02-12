package server.catalog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import server.Server;
import server.domain.Group;
import server.domain.User;
import server.exceptions.group.UserCouldNotCreateGroupException;

public class GroupCatalog {
	public static final String GROUPS_DIRECTORY = Server.DATA_PATH+"/groups/";
	private static GroupCatalog INSTANCE = null;
	private static ArrayList<Group> groupsList = null;

	/**
	 * 
	 * @return GroupCatalog singleton instance
	 */
	public static GroupCatalog getInstance() {
		return INSTANCE;
	}

	/**
	 * GroupCatalog constructor that does not need parameters
	 */
	private GroupCatalog () {
		groupsList = new ArrayList<Group>();
	}

	/**
	 * Adds a group to the catalog
	 * @param groupID Id of the group to add
	 * @param owner who created the group
	 * @return true if successful
	 * @throws UserCouldNotCreateGroupException if group id already in use
	 * @throws IOException if an error occurs whilst writing in the user's database
	 */
	public boolean addGroup(String groupID, String owner) throws UserCouldNotCreateGroupException {

		String [] groupFolders = new File(GROUPS_DIRECTORY).list();

		for (String g:groupFolders) {
			if(!g.equals(groupID)) throw new UserCouldNotCreateGroupException();
		}
		groupsList.add(new Group(groupID, UserCatalog.getInstance().getUser(owner)));
		return true;
	}

	/**
	 * Returns group by their Id
	 * @param groupID group Id to find
	 * @return group
	 */
	public Group getGroup(String groupID) {
		for(Group g: groupsList) {
			if(g.getGroupID().equals(groupID)) {
				return g;
			}
		}
		return null;	
	}

	/**
	 * 
	 * @return List with all the group currently in the catalog
	 */
	public List<Group> getAllGroups() {
		return groupsList;
	}

	/**
	 * 
	 * @param user user to which we need to find the groups
	 * @return List of groups that a certain user belongs to
	 */
	public List<Group> getUserGroups(User user){
		return groupsList.stream().filter(g -> g.hasMember(user)).collect(Collectors.toList());
	}

	public static String infoUser(User user) {
		StringBuilder ret=new StringBuilder();
		ArrayList<String> ehDono=new ArrayList<String>();
		ArrayList<String> pertence=new ArrayList<String>();

		for (int i = 0; i < groupsList.size(); i++){
			if(groupsList.get(i).getOwner()==user) {
				ehDono.add(groupsList.get(i).getGroupID());
			}
			if(groupsList.get(i).members.contains(user)) {
				pertence.add(user.getUsername());
			}
		}

		if(ehDono==null)
			ret.append("Naho eh dono de nenhum grupo /n");
		else {
			ret.append("Eh dono de: \n");
			for (int i = 0; i < ehDono.size(); i++){
				ret.append(ehDono.get(i)+"\n");
			}
		}
		if(pertence==null) {
			ret.append("Naho pertence a nenhum grupo /n");
		}
		else {
			ret.append("Pertence a: \n");
			for (int i = 0; i < pertence.size(); i++){
				ret.append(pertence.get(i)+"\n");
			}
		}

		return ret.toString();

	}

}	

