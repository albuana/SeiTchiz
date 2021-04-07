package server.catalog;

import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import server.FileManager;
import server.domain.Group;
import server.domain.GroupKey;
import server.domain.User;
import server.exceptions.group.UserAlreadyInGroupException;
import server.exceptions.group.GroupAlreadyExistException;

/**
 * Catalog knows all the information related to the groups
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class GroupCatalog {
	
	private static GroupCatalog INSTANCE;
	private static ArrayList<Group> groupsList;
	
	private static final String GROUPS_DIRECTORY = "Fase1-Parte2/SeiTchiz/server/Data/groups/";
	private static final String GROUP_INFO_FILE_NAME = "groupinfo.txt";
	private static final String GROUP_KEY_FILE_NAME = "groupkeys.txt";

	
	/**
	 * @return GroupCatalog singleton instance
	 * @throws CertificateException 
	 * @throws ClassNotFoundException 
	 */
	public static GroupCatalog getInstance() throws ClassNotFoundException, CertificateException {
		if(INSTANCE == null)
			INSTANCE = new GroupCatalog();
		return INSTANCE;
	}

	/**
	 * GroupCatalog constructor that does not need parameters
	 * @throws ClassNotFoundException 
	 * @throws CertificateException 
	 * @throws IOException 
	 * @throws UserAlreadyInGroupException 
	 */
	private GroupCatalog () throws ClassNotFoundException, CertificateException{
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
	 * @throws ClassNotFoundException 
	 * @throws CertificateException 
	 */
	private void initializeGroupCatalog() throws UserAlreadyInGroupException, IOException, ClassNotFoundException, CertificateException{
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
	 * @throws ClassNotFoundException 
	 * @throws CertificateException 
	 */
	private Group initializeGroup(String gFolderName) throws IOException, UserAlreadyInGroupException, ClassNotFoundException, CertificateException{
		FileManager groupInfo=new FileManager(GROUPS_DIRECTORY+gFolderName+"/"+GROUP_INFO_FILE_NAME);
        FileManager groupKeys=new FileManager(GROUPS_DIRECTORY+gFolderName+"/"+GROUP_KEY_FILE_NAME);
		List<String> load=groupInfo.loadContent();
		List<String> members=new ArrayList<String>();
		List<Object> keys=groupKeys.loadObjects();
		//Faz load do ficheiro com as keys
		//Faz load do ficheiro com <user,Id da chave>
		int currentGroupKeyId=0;
		//Poe os memsbros do grupo numa lista
		for(String s:load) {
			String[] split=s.split(",");
			members.add(split[0]);
			currentGroupKeyId=Integer.parseInt(split[1]);
		}
		UserCatalog users = UserCatalog.getInstance();
		GroupKey ownerkey=new GroupKey(currentGroupKeyId,(byte[]) keys.get(0));
		//Cria o grupo com o dono e com a sua chave
		Group group=new Group(gFolderName, ownerkey,users.getUser(members.get(0)));
		//Adiciona os membros do grupo ao grupo com as suas chaves respectivas
		for(int i=1;i<members.size();i++) {
			GroupKey key=new GroupKey(currentGroupKeyId,(byte[]) keys.get(i));
			group.getUsersAndKeys().put(users.getUser(members.get(i)),key);
		}
		return group;
	}

	/**
	 * Adds a group to the catalog
	 * @param groupID Id of the group to add
	 * @param owner who created the group
	 * @param encryptedKey 
	 * @return true if successful
	 * @throws GroupAlreadyExistException if group id already in use
	 * @throws IOException if an error occurs whilst writing in the user's database
	 * @throws ClassNotFoundException 
	 * @throws CertificateException 
	 */
	public boolean addGroup(String groupID, String owner, byte[] encryptedKey) throws GroupAlreadyExistException, IOException, ClassNotFoundException, CertificateException {

		String [] groupFolders = new File(GROUPS_DIRECTORY).list();
		User user = UserCatalog.getInstance().getUser(owner);
		
		if(groupFolders==null) { //if the group that user want to create don't exist
			Group grupo=new Group(groupID, encryptedKey,UserCatalog.getInstance().getUser(owner));
			grupo.createHistory(user); //and create the history file of the user
			groupsList.add(grupo);
			return true;
		}
		
		for (String g:groupFolders) //search in all folders
			if(g.equals(groupID)) //if the group that user want to create already exist
				throw new GroupAlreadyExistException();

		try {
			Group grupo=new Group(groupID, encryptedKey, UserCatalog.getInstance().getUser(owner));
			grupo.createHistory(user);
			groupsList.add(grupo);
		} catch (IOException e) {
			throw new GroupAlreadyExistException();
		}
		return true;
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
