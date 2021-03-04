package server.catalog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import server.FileManager;
import server.Server;
import server.domain.Group;
import server.domain.User;
import server.exceptions.group.UserAlreadyInGroupException;
import server.exceptions.group.GroupAlreadyExistException;

/**
 * Catalog knows all the information related to the groups
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class GroupCatalog {
	
	private static GroupCatalog INSTANCE = new GroupCatalog();
	private static ArrayList<Group> groupsList;
	
	private static final String GROUPS_DIRECTORY = Server.DATA_PATH+"groups/";
	private static final String GROUP_INFO_FILE_NAME = "groupinfo.txt";
	private static final String GROUP_COLLECT_FILE_NAME = "groupcollect.txt";
	
	/**
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
			e.printStackTrace();
		}

	}

	/**
	 * Initialize the groups folder, if have no folder create one
	 * @throws UserAlreadyInGroupException
	 * @throws IOException
	 */
	private void initializeGroupCatalog() throws UserAlreadyInGroupException, IOException{
		String [] groupFolders = new File(GROUPS_DIRECTORY).list();
		if(groupFolders!=null) {
			for (String g:groupFolders) {
				groupsList.add(initializeGroup(g));
			}
		}
	}

	/**
	 * Initiatlize the members of groups 
	 * @param gFolderName
	 * @return 
	 * @throws IOException
	 * @throws UserAlreadyInGroupException
	 */
	private Group initializeGroup(String gFolderName) throws IOException, UserAlreadyInGroupException{
		FileManager groupInfo=new FileManager(GROUPS_DIRECTORY+gFolderName,GROUP_INFO_FILE_NAME);
		ArrayList<String> members=groupInfo.fileToList();
		UserCatalog users = UserCatalog.getInstance();
		Group group=new Group(gFolderName, users.getUser(members.get(0)));
		for(int i=1;i<members.size();i++) {
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
		Group grupo=new Group(groupID, UserCatalog.getInstance().getUser(owner));
		User user = UserCatalog.getInstance().getUser(owner);
		
		if(groupFolders==null) { //if the group that user want to create don't exist
			createGroupFiles(groupID, user); //create the folder with the given group name
			grupo.createHistory(user); //and create the history file of the user
			groupsList.add(grupo);
			return true;
		}
		
		for (String g:groupFolders) //search in all folders
			if(g.equals(groupID)) //if the group that user want to create already exist
				throw new GroupAlreadyExistException();

		try {
			createGroupFiles(groupID, user);
			grupo.createHistory(user);
			groupsList.add(grupo);
		} catch (IOException e) {
			throw new GroupAlreadyExistException();
		}
		return true;
	}

	/**
	 * Create the group files (group info and collect)
	 * @param groupID
	 * @param user
	 * @throws IOException
	 */
	private void createGroupFiles(String groupID, User user) throws IOException {
		String path=GROUPS_DIRECTORY+groupID;
		FileManager groupInfo=new FileManager(path,GROUP_INFO_FILE_NAME);
		new FileManager(path,GROUP_COLLECT_FILE_NAME);
		groupInfo.writeFile(user.getUsername()+"\n"); //Primeiro nome a ser escrito no ficheiro eh o do dono
	}

	/**
	 * Returns group by their ID
	 * @param groupID group ID to find
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


	public String infoUser(User user) {
		StringBuilder pertence=new StringBuilder();
		StringBuilder ehDono=new StringBuilder();
		for(Group g: groupsList) {
			if(g.hasMember(user)){
				pertence.append(g.getGroupID()+"; ");
			}
			if(g.getOwner().equals(user)){
				ehDono.append(g.getGroupID()+"; ");
			}
		}
		StringBuilder retorno=new StringBuilder();

		if(ehDono.length()==0) {
			retorno.append("You're not the owner of any group.\n");
		} else {
			retorno.append(user + " it's the owner of: " + ehDono);
		}

		if(pertence.length()==0) {
			retorno.append("You're not member of any group.");
		} else {
			retorno.append("\n" + user + " it's member of: " + pertence + "\n");
		}

		return retorno.toString();
	}
}	
