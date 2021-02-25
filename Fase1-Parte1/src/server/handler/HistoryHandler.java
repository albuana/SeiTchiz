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
		//adiciona mensagem ao history se ela ainda não existe se não existirem viewers
		FileManager history=group.getGroupHistoryFileManager();
		FileManager collect=group.getGroupCollectFileManager();
		CheckAddMsgToHistory(history, collect);
		String retorno = getMessages(history.fileToList());

		
		if(retorno.length()==0)
			return "Nothing to see";
		
		return retorno;
	}
	/**
	 * 
	 * @param history
	 * @param collect
	 * @throws IOException
	 */
	private void CheckAddMsgToHistory(FileManager history, FileManager collect) throws IOException {
		//Vê se no collect existe mensagens do tipo sender:msg sem viewers e se houver remove e adiciona ao history
		ArrayList<String> collectMessages=collect.fileToList();
		ArrayList<String> collectHistory=history.fileToList();
		for(String c:collectMessages) {
			String[] split=c.split(":");
			if(split.length==2 && !collectHistory.contains(c)) {
				history.writeFile(c+"/n");
				collect.removeFromFile(c);
			}
		}

		
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
			retorno.append(s);
		}
		return retorno.toString();
	}
}
