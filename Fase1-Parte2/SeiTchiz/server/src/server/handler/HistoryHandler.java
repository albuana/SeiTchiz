package server.handler;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

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
	 * @throws CertificateException 
	 * @throws ClassNotFoundException 
	 */
	public HistoryHandler(String groupID,User currentUser) throws GroupNotExistException, UserDoesNotBelongToGroupException, ClassNotFoundException, CertificateException {
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
	 * @throws ClassNotFoundException 
	 */
	public String getHistory() throws IOException, ClassNotFoundException {
		//adiciona mensagem ao history se ela ainda n�o existe se n�o existirem viewers
		FileManager history=group.getHistoryFile(user);
		String retorno = getMessages(history.loadContent());

		
		if(retorno.length()==0)
			return "Nothing to see";
		
		return retorno;
	}

	
	/**
	 * 
	 * @param list
	 * @return
	 * @throws IOException
	 */
	private String getMessages(List<String> list) throws IOException {
		StringBuilder retorno = new StringBuilder();
		
		for(String s:list) {
			retorno.append(s + "\n");
		}
		return retorno.toString();
	}
}
