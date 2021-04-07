package server.handler;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.List;

import server.FileManager;
import server.catalog.GroupCatalog;
import server.domain.Group;
import server.domain.User;
import server.exceptions.group.GroupNotExistException;
import server.exceptions.group.NothingToReadException;
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
	 * @throws NothingToReadException 
	 */
	public List<Object[]> getHistory() throws IOException, ClassNotFoundException, NothingToReadException {
		FileManager historyFile = group.getHistoryFile(user);
		List<String> load=historyFile.loadContent();
		if(load.size()==0)
			throw new NothingToReadException();
		return group.formatMessagesToSendHistory(historyFile.loadObjects());
	}

}
