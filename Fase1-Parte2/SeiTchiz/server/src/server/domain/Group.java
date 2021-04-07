package server.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import server.FileManager;
import server.catalog.UserCatalog;
import server.exceptions.group.UserAlreadyInGroupException;
import server.exceptions.group.UserDoesNotInGroupException;

/**
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class Group {
	private User owner;
	private String groupID;
	private Map<User,GroupKey> membersList;
	private Map<User,Integer> userGroupId;
	private static final String GROUPS_DIRECTORY = "Fase1-Parte2/SeiTchiz/server/Data/groups/";
	private static final String GROUP_INFO_FILE_NAME = "/groupinfo.txt";
	private static final String GROUP_KEY_FILE_NAME = "/groupkeys.txt";
	private static final String GROUPS_HISTORY_DIRECTORY = "/history/";
	private static final String GROUPS_COLLECT_DIRECTORY = "/collect/";
	
	private int currentGroupKeyId;
	private FileManager groupInfoFM;


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
        //cria o id da chave
        currentGroupKeyId = 0;
        this.owner = owner;
        membersList=new HashMap<User,GroupKey>();
        membersList.put(owner, new GroupKey(currentGroupKeyId,encryptedKey));
        userGroupId=new HashMap<User,Integer>();
        userGroupId.put(owner, currentGroupKeyId);
		Path path = Paths.get(GROUPS_DIRECTORY+groupID);
		Files.createDirectories(path);
		path = Paths.get(GROUPS_DIRECTORY+groupID+GROUPS_HISTORY_DIRECTORY);
		Files.createDirectories(path);
		path = Paths.get(GROUPS_DIRECTORY+groupID+GROUPS_COLLECT_DIRECTORY);
		Files.createDirectories(path);

        groupInfoFM=new FileManager(GROUPS_DIRECTORY+groupID+GROUP_INFO_FILE_NAME);
        //escreve no grupoInfo <userId,Id da chave>
        groupInfoFM.writeContent(owner == null ? "" : owner.getUsername() + "," + currentGroupKeyId);
        FileManager groupKeys=new FileManager(GROUPS_DIRECTORY+groupID+GROUP_KEY_FILE_NAME);
        //Escreve a chave cifrada o que no inicia é só uma que é a do owner
        groupKeys.writeObjects(encryptedKey);
   
        createHistory(owner);
		createCollect(owner);
    }
	//Construtos para inicializar grupos e nao criar pelo newgroup como o de cima
	public Group(String gFolderName, GroupKey encryptedKey, User owner) {
		currentGroupKeyId = encryptedKey.getIdentifier();
		this.groupID=gFolderName;
		this.owner = owner;
		membersList=new HashMap<User,GroupKey>();
        membersList.put(owner, encryptedKey);
        userGroupId=new HashMap<User,Integer>();
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
		ArrayList<User> retorno=(ArrayList<User>) membersList.keySet().stream().collect(Collectors.toList());
		return retorno;
	}
	
	public Map<User,GroupKey> getUsersAndKeys() {
		return membersList;
	}

	/**
	 * Adds a new member to the group
	 * @param user the new user
	 * @param userKeys 
	 * @throws IOException if it wasn't possible to write in the groupInfo file
	 * @throws UserAlreadyInGroupException if the given user is already in the group
	 * @throws ClassNotFoundException 
	 * @throws CertificateException 
	 */
	public void addMember(User user,List<Object[]> userKeys) throws IOException, UserAlreadyInGroupException, ClassNotFoundException, CertificateException {
		if(membersList.containsKey(user))
			throw new UserAlreadyInGroupException();
		else {			
				//incrementa o id da chave
				currentGroupKeyId++;
				FileManager groupMembers=new FileManager(GROUPS_DIRECTORY+groupID+GROUP_INFO_FILE_NAME);
				groupMembers.removeAll();
				FileManager groupKeys=new FileManager(GROUPS_DIRECTORY+groupID+GROUP_KEY_FILE_NAME);
		        groupKeys.removeAll();
		        //Limpa os ficheiros porque os membros vão todos ter chaves diferentes para encriptas as mensagens
				for(Object[] o:userKeys) {
					String username=(String) o[0];
					byte[] encryptedKey= (byte[]) o[1];
					GroupKey userGroupKey=new GroupKey(currentGroupKeyId,encryptedKey);
					//Escreve as keys no groupKeys.txt de cada membro
					groupKeys.writeObjects(encryptedKey);
					//Escreve para cada membro no grupoInfo.txt <UserId, id da chave>
					groupMembers.writeContent(username + "," + currentGroupKeyId);
					membersList.put(UserCatalog.getInstance().getUser(username), userGroupKey);
			}
			createHistory(user);
			createCollect(user);
		}
	}

	/**
	 * Remove a member to the group
	 * @param user the user
	 * @param userKeys 
	 * @throws UserDoesNotInGroupException 
	 * @throws IOException if an error occurs whilst writing in the groupInfo file
	 * @throws ClassNotFoundException
	 * @throws CertificateException 
	 * @throws UserDoesNotBelongToGroupException if the given user is not in the group
	 */
	public void removeMember(User user, ArrayList<Object[]> userKeys) throws UserDoesNotInGroupException, IOException, ClassNotFoundException, CertificateException {
		if(!membersList.containsKey(user))
			throw new UserDoesNotInGroupException();
		else {
			membersList.remove(user);
			//incrementa o id da chave
			currentGroupKeyId++;
			FileManager groupMembers=new FileManager(GROUPS_DIRECTORY+groupID+GROUP_INFO_FILE_NAME);
			groupMembers.removeAll();
			FileManager groupKeys=new FileManager(GROUPS_DIRECTORY+groupID+GROUP_KEY_FILE_NAME);
	        groupKeys.removeAll();
	        //Limpa os ficheiros porque os membros vão todos ter chaves diferentes para encriptas as mensagens
			for(Object[] o:userKeys) {
				String username=(String) o[0];
				byte[] encryptedKey= (byte[]) o[1];
				GroupKey userGroupKey=new GroupKey(currentGroupKeyId,encryptedKey);
				groupKeys.writeObjects(encryptedKey);
				groupMembers.writeContent(username + "," + currentGroupKeyId);
				membersList.put(UserCatalog.getInstance().getUser(username), userGroupKey);
			}
		}
	}

	/**
	 * 
	 * @param user the user
	 * @return true if the given user is in this group
	 */
	public boolean hasMember(User user) {
		return membersList.containsKey(user);
	}
	
	public FileManager getGroupInfoFileManager() {
		return groupInfoFM;
	}
	
	/**
	 * Creates the history file of the given user
	 * @param newbie
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void createHistory(User newbie) throws IOException, ClassNotFoundException {
		String path=GROUPS_DIRECTORY+groupID+GROUPS_HISTORY_DIRECTORY;
		FileManager history=new FileManager(path+newbie.getUsername()+"history.txt");
	}
	private void createCollect(User user) throws IOException, ClassNotFoundException {
		String path=GROUPS_DIRECTORY+groupID+GROUPS_COLLECT_DIRECTORY;
		FileManager collect= new FileManager(path+user.getUsername()+"collect.txt");
	}
	
	/**
	 * Gets the history file of the given user
	 * @param newbie
	 * @return
	 * @throws IOException 
	 */
	public FileManager getHistoryFile(User newbie) throws IOException {
		String path=GROUPS_DIRECTORY+groupID+GROUPS_HISTORY_DIRECTORY;
		return new FileManager(path+newbie.getUsername()+"history.txt");
	}
	public FileManager getCollectFile(User newbie) throws IOException {
		String path=GROUPS_DIRECTORY+groupID+GROUPS_COLLECT_DIRECTORY;
		return new FileManager(path+newbie.getUsername()+"collect.txt");
	}
	public byte[] getUserKey(User user) {
		return membersList.get(user).getEncryptedKey();
	}
	public void sendMsgCollect(Message message, User sender) throws ClassNotFoundException, IOException {
		ArrayList<User> users=getUsers();
		for(User u:users) {
			if(!u.equals(sender)) {
				FileManager collect=getCollectFile(u);
				Object[] messageToWrite= {message.getSender(),message.getContent(),this.getUserKey(u)};
				collect.writeObjects(messageToWrite);
			}
		}
		
	}
	public void sendMsgHistoryUser(String sender,byte[] content, byte[] userKey, User user) throws ClassNotFoundException, IOException {
		FileManager history=getHistoryFile(user);
		Object[] messageToWrite= {sender,content,userKey};
		history.writeObjects(messageToWrite);
		//Tem que se escrever como array de Objects para poder-se escrever bem os bytes da chave e da mensagem cifrada
		//depois deste load podemos ver que a primeira posição da array tem o nome do sender
		//segunda posição da array tem a mensagem cifrada em bytes
		//terceira posição da array tem a key cifrada em bytes
		//É o mesmo para o collect
	}
	
	public List<Object[]> formatMessagesToSend(List<Object> objects, User user) throws IOException, ClassNotFoundException{
		List<Object[]> retorno=new ArrayList<Object[]>();
		for(int i=2;i<objects.size();i=i+3) {
			Object[] obj= {objects.get(i-2),objects.get(i-1),objects.get(i)};
			String sender=(String) objects.get(i-2);
			byte[] content=(byte[]) objects.get(i-1);
			byte[] userKey=(byte[]) objects.get(i);
			sendMsgHistoryUser(sender,content,userKey,user);
			retorno.add(obj);
		}
		return retorno;
	}
	public List<Object[]> formatMessagesToSendHistory(List<Object> loadObjects) {
		List<Object[]> retorno=new ArrayList<Object[]>();
		for(int i=2;i<loadObjects.size();i=i+3) {
			Object[] obj= {loadObjects.get(i-2),loadObjects.get(i-1),loadObjects.get(i)};
			retorno.add(obj);
		}
		return retorno;
	}
}