package server.handler;

import java.io.IOException;
import java.util.ArrayList;

import server.FileManager;
import server.catalog.GroupCatalog;
import server.domain.Group;
import server.domain.User;
import server.exceptions.group.GroupNotExistException;
import server.exceptions.group.UserDoesNotBelongToGroupException;

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
		FileManager groupFile = group.getGroupHistoryFileManager();
		String retorno = getMessages(groupFile.fileToList());
		
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
		boolean alreadySeen = false;
		
		for(String s:fileToList) {
			String[] split = s.split(":");
			alreadySeen = false;
			for(int i = 2; i<split.length; i++) {
				if(split[i].equals(user.getUsername()))
					alreadySeen = true;
			}
			if(alreadySeen) {
				retorno.append("sender: " + split[0] + " Msg: " + split[1]+"\n");
			}
		}
		return retorno.toString();
	}
}
