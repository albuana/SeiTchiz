package server.catalog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import server.FileManager;
import server.Server;
import server.domain.Group;
import server.domain.User;
import server.exceptions.group.UserAlreadyInGroupException;
import server.exceptions.group.GroupAlreadyExistException;

public class GroupCatalog {
	private static GroupCatalog INSTANCE = new GroupCatalog();
	private static ArrayList<Group> groupsList;
	private static final String GROUPS_DIRECTORY = Server.DATA_PATH+"groups/";
	private static final String GROUP_INFO_FILE_NAME = "groupinfo.txt";
	private static final String GROUP_COLLECT_FILE_NAME = "groupcollect.txt";
	private static final String GROUP_HISTORY_FILE_NAME = "grouphistory.txt";
	/**
	 * 
	 * @return GroupCatalog singleton instance
	 */
	public static GroupCatalog getInstance() {
		return INSTANCE;
	}

	/**
	 * GroupCatalog constructor that does not need parameters
	 * @throws IOException 
	 * @throws UserAlreadyInGroupException 
	 */
	private GroupCatalog (){
		groupsList = new ArrayList<Group>();
		try {
			initializeGroupCatalog();
		} catch (UserAlreadyInGroupException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initializeGroupCatalog() throws UserAlreadyInGroupException, IOException{
		String [] groupFolders = new File(GROUPS_DIRECTORY).list();
		if(groupFolders!=null) {
			for (String g:groupFolders) {
				groupsList.add(initializeGroup(g));
			}
		}

		
	}

	private Group initializeGroup(String g) throws IOException, UserAlreadyInGroupException{
		FileManager groupInfo=new FileManager(GROUPS_DIRECTORY+g,GROUP_INFO_FILE_NAME);
		ArrayList<String> members=groupInfo.fileToList();
		UserCatalog users = UserCatalog.getInstance();
		Group group=new Group(g, users.getUser(members.get(0)));
		for(int i=0;i<members.size();i++) {
			group.getUsers().add(users.getUser(members.get(i)));
		}
		return group;
	}

	/**
	 * Adds a group to the catalog
	 * @param groupID Id of the group to add
	 * @param owner who created the group
	 * @return true if successful
	 * @throws GroupAlreadyExistException if group id already in use
	 * @throws IOException if an error occurs whilst writing in the user's database
	 */
	public boolean addGroup(String groupID, String owner) throws GroupAlreadyExistException, IOException {

		String [] groupFolders = new File(GROUPS_DIRECTORY).list();
		if(groupFolders==null) {
			createGroupFiles(groupID, UserCatalog.getInstance().getUser(owner));
			groupsList.add(new Group(groupID, UserCatalog.getInstance().getUser(owner)));
			return true;
		}
		for (String g:groupFolders)
			if(g.equals(groupID)) 
				throw new GroupAlreadyExistException();
		
		try {
			createGroupFiles(groupID, UserCatalog.getInstance().getUser(owner));
			groupsList.add(new Group(groupID, UserCatalog.getInstance().getUser(owner)));
		} catch (IOException e) {
			throw new GroupAlreadyExistException();
		}

		return true;

	}

	private void createGroupFiles(String groupID, User user) throws IOException {
		String path=GROUPS_DIRECTORY+groupID;
		FileManager groupInfo=new FileManager(path,GROUP_INFO_FILE_NAME);
		new FileManager(path,GROUP_COLLECT_FILE_NAME);
		new FileManager(path,GROUP_HISTORY_FILE_NAME);	
		groupInfo.writeFile(user.getUsername()+"\n"); //Primeiro nome da lista � o dono
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

//	public static String infoUser(User user) {
//		StringBuilder ret=new StringBuilder();
//		ArrayList<String> ehDono=new ArrayList<String>();
//		ArrayList<String> pertence=new ArrayList<String>();
//
//		for (int i = 0; i < groupsList.size(); i++){
//			if(groupsList.get(i).getOwner()==user) {
//				ehDono.add(groupsList.get(i).getGroupID());
//			}
//			if(groupsList.get(i).getUsers().contains(user)) {
//				pertence.add(user.getUsername());
//			}
//		}
//
//		if(ehDono==null)
//			ret.append("Naho eh dono de nenhum grupo /n");
//		else {
//			ret.append("Eh dono de: \n");
//			for (int i = 0; i < ehDono.size(); i++){
//				ret.append(ehDono.get(i)+"\n");
//			}
//		}
//		if(pertence==null) {
//			ret.append("Naho pertence a nenhum grupo /n");
//		}
//		else {
//			ret.append("Pertence a: \n");
//			for (int i = 0; i < pertence.size(); i++){
//				ret.append(pertence.get(i)+"\n");
//			}
//		}
//
//		return ret.toString();
//
//	}

}	

