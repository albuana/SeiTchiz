package server.handler;

import java.io.IOException;
import java.util.ArrayList;

import server.FileManager;
import server.catalog.GroupCatalog;
import server.domain.Group;
import server.domain.User;
import server.exceptions.group.GroupNotExistException;
import server.exceptions.group.UserDoesNotBelongToGroupException;

/**
 * Handles the history information
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class HistoryHandler {
	private Group group;
	private User user;
	/**
	 * 
	 * @param groupID
	 * @param currentUser
	 * @throws GroupNotExistException
	 * @throws UserDoesNotBelongToGroupException
	 */
	public HistoryHandler(String groupID,User currentUser) throws GroupNotExistException, UserDoesNotBelongToGroupException {
		this.group = GroupCatalog.getInstance().getGroup(groupID);
		this.user = currentUser;
		if(group ==  null)
			throw new GroupNotExistException();

		if(!group.getUsers().contains(currentUser))  {
			throw new UserDoesNotBelongToGroupException();
		}
	}
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getHistory() throws IOException {
		//adiciona mensagem ao history se ela ainda n�o existe se n�o existirem viewers
		FileManager history=group.getHistoryFile(user);
		String retorno = getMessages(history.fileToList());

		
		if(retorno.length()==0)
			return "Nothing to see";
		
		return retorno;
	}

	
	/**
	 * 
	 * @param fileToList
	 * @return
	 * @throws IOException
	 */
	private String getMessages(ArrayList<String> fileToList) throws IOException {
		StringBuilder retorno = new StringBuilder();
		
		for(String s:fileToList) {
			retorno.append(s + "\n");
		}
		return retorno.toString();
	}
}
