
package server.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import server.FileManager;
import server.catalog.GroupCatalog;
import server.domain.Group;
import server.domain.User;
import server.exceptions.group.GroupNotExistException;
import server.exceptions.group.UserDoesNotBelongToGroupException;

public class CollectMessagesHandler {
	private Group group;
	private User user;

	public CollectMessagesHandler(String groupID,User currentUser) throws GroupNotExistException, UserDoesNotBelongToGroupException {
		this.group = GroupCatalog.getInstance().getGroup(groupID);
		this.user = currentUser;
		if(group ==  null)
			throw new GroupNotExistException();

		if(!group.getUsers().contains(currentUser))  {
			throw new UserDoesNotBelongToGroupException();
		}
	}

	public String collect() throws IOException {
		FileManager groupFile = group.getGroupCollectFileManager();
		String retorno = getMessages(groupFile.fileToList());
		
		if(retorno == null)
			return "Nothing to see"; //TODO
		
		return retorno;
	}

	/**
	 * 
	 * @param fileToList
	 * @return
	 */
	private String getMessages(ArrayList<String> fileToList) {
		StringBuilder retorno = new StringBuilder();
		boolean notSeen = false;
		
		for(String s:fileToList) {
			String[] split = s.split(":");
			for(int i = 2; i<split.length; i++) {
				//
				if(split[i] == user.getUsername())
					notSeen = true;
			}
			if(notSeen) {
				retorno.append("sender: " + split[0] + " Msg: " + split[1]);
				notSeen=false;
				//remover viewer da groupCollect.txt
				//adicionar viewer ao groupHistory.txt
			}
		}
		return retorno.toString();
	}

}






