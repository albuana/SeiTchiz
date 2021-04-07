
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
 * Handles the necessary procedures for collceting a new messages of a group
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class CollectMessagesHandler {
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
	public CollectMessagesHandler(String groupID,User currentUser) throws GroupNotExistException, UserDoesNotBelongToGroupException, ClassNotFoundException, CertificateException {
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
	 * @throws NothingToReadException 
	 * @throws ClassNotFoundException 
	 */
	public List<Object[]> collect() throws IOException, NothingToReadException, ClassNotFoundException {
		FileManager collectFile = group.getCollectFile(user);
		List<String> load=collectFile.loadContent();
		if(load.size()==0)
			throw new NothingToReadException();
		List<Object[]> retorno=group.formatMessagesToSend(collectFile.loadObjects(), user);
		collectFile.removeAll();
		return retorno;
	}


}







