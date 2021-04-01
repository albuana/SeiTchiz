package server.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.FileManager;
import server.Server;
import server.exceptions.group.UserAlreadyInGroupException;
import server.exceptions.group.UserDoesNotInGroupException;

/**
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class Group {
	private User owner;
	private String groupID;
	private ArrayList<User> membersList;
	private Map<User,List<GroupKey>> groupKeys;
	private Map<User,Integer> userGroupId;
	private static final String GROUPS_DIRECTORY = Server.DATA_PATH+"groups/";
	private static final String GROUP_INFO_FILE_NAME = "groupinfo.txt";
	private static final String GROUP_COLLECT_FILE_NAME = "groupcollect.txt";
	private static final String GROUPS_HISTORY_DIRECTORY = "history/";
	
	private int currentGroupKeyId;
	private FileManager groupInfoFM, groupCollectFM, groupHistoryFM;


	/**
	 * 
	 * @param groupID the name of the name
	 * @param owner the owner of the group
	 * @param encryptedKey 
	 * @param key the group key
	 * @throws IOException if anything happens whilst writing in the group groupInfo file
	 * @throws ClassNotFoundException 
	 */
	public Group(String groupID, byte[] encryptedKey,  User owner) throws ClassNotFoundException, IOException{
        this.groupID=groupID;
        currentGroupKeyId = 0;
        this.owner = owner;
        membersList=new ArrayList<User>();
        membersList.add(owner);
        groupKeys=new HashMap<User,List<GroupKey>>();
        groupKeys.put(owner, new ArrayList<>());
        groupKeys.get(owner).add(new GroupKey(0,encryptedKey));
        userGroupId=new HashMap<User,Integer>();
        userGroupId.put(owner, currentGroupKeyId);
        //add the secret key to the owner of the group (yes he is going to have a key only 
        //for himself)
		Path path = Paths.get(GROUPS_DIRECTORY+groupID);
		Files.createDirectories(path);
        //groupInfo
        //owner
        //currentKeyId
        //group members
        groupInfoFM=new FileManager("Fase1-Parte2/SeiTchiz/server/Data/groups/"+groupID+"/groupinfo.txt");
        groupInfoFM.writeContent(owner == null ? "" : owner.getUsername() + "," + new GroupKey(currentGroupKeyId,encryptedKey));
        //TODO verificar porque está em duas linhas não prioritário
    }
	//Construtos para inicializar grupos e nao criar pelo newgroup como o de cima
	public Group(String gFolderName, GroupKey key, User owner) {
		currentGroupKeyId = key.getIdentifier();
		this.groupID=gFolderName;
		this.owner = owner;
        membersList=new ArrayList<User>();
        membersList.add(owner);
        groupKeys.put(owner, new ArrayList<>());
        groupKeys.get(owner).add(new GroupKey(0,key.getEncryptedKey()));
        userGroupId.put(owner, currentGroupKeyId);
	}

	/**
	 * @return the name of this group
	 */
	public String getGroupID() {
		return groupID;
	}

	/**
	 * @return the owner of this group
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * @return the members of this group
	 */
	public ArrayList<User> getUsers() {
		return membersList;
	}

	/**
	 * Adds a new member to the group
	 * @param user the new user
	 * @throws IOException if it wasn't possible to write in the groupInfo file
	 * @throws UserAlreadyInGroupException if the given user is already in the group
	 */
	public void addMember(User user) throws IOException, UserAlreadyInGroupException {
		if(membersList.contains(user))
			throw new UserAlreadyInGroupException();
		else {			
			if(membersList.add(user)) {
				FileManager groupMembers=new FileManager(GROUPS_DIRECTORY+groupID,GROUP_INFO_FILE_NAME);
				String str =user.getUsername()+"\n";
				groupMembers.writeFile(str);
			}

		}
	}

	/**
	 * Remove a member to the group
	 * @param user the user
	 * @throws UserDoesNotInGroupException 
	 * @throws IOException if an error occurs whilst writing in the groupInfo file
	 * @throws ClassNotFoundException
	 * @throws UserDoesNotBelongToGroupException if the given user is not in the group
	 */
	public void removeMember(User user) throws UserDoesNotInGroupException, IOException, ClassNotFoundException {
		if(!membersList.remove(user))
			throw new UserDoesNotInGroupException();
		else {
			FileManager groupMembers=new FileManager(GROUPS_DIRECTORY+groupID,GROUP_INFO_FILE_NAME);
			groupMembers.removeFromFile(user.getUsername());
			membersList.remove(user);
		}
	}

	/**
	 * 
	 * @param user the user
	 * @return true if the given user is in this group
	 */
	public boolean hasMember(User user) {
		return membersList.contains(user);
	}

	/**
	 * @return the group's collect file manager
	 */
	public FileManager getGroupCollectFileManager() {
		return groupCollectFM;
	}
	
	/**
	 * @return the group's history file manager
	 */
	public FileManager getGroupHistoryFileManager() {
		return groupHistoryFM;
	}
	
	public FileManager getGroupInfoFileManager() {
		return groupInfoFM;
	}
	
	/**
	 * Creates the history file of the given user
	 * @param newbie
	 */
	public void createHistory(User newbie) {
		String path=GROUPS_DIRECTORY+groupID+"/"+GROUPS_HISTORY_DIRECTORY ;
		new FileManager(path,newbie.getUsername()+"history.txt");
	}
	
	/**
	 * Gets the history file of the given user
	 * @param newbie
	 * @return
	 */
	public FileManager getHistoryFile(User newbie) {
		String path=GROUPS_DIRECTORY+groupID+"/"+GROUPS_HISTORY_DIRECTORY;
		return new FileManager(path,newbie.getUsername()+"history.txt");
	}
}