
package server.handler;
import java.io.IOException;
import java.util.ArrayList;

import server.FileManager;
import server.catalog.GroupCatalog;
import server.domain.Group;
import server.domain.User;
import server.exceptions.group.GroupNotExistException;
import server.exceptions.group.UserDoesNotBelongToGroupException;

public class CollectMessagesHandler {
	private Group group;
	private User user;

	/**
	 * 
	 * @param groupID
	 * @param currentUser
	 * @throws GroupNotExistException
	 * @throws UserDoesNotBelongToGroupException
	 */
	public CollectMessagesHandler(String groupID,User currentUser) throws GroupNotExistException, UserDoesNotBelongToGroupException {
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
	public String collect() throws IOException {
		FileManager groupFile = group.getGroupCollectFileManager();
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
		boolean notSeen = false;
		//corre cada linha do file 
		for(String s:fileToList) {
			//separa cada string e fica do tipo sender:msg:(goncalo:tiago)->viewers mas numa matriz
			String[] split = s.split(":");
			notSeen = false;
			//for dá skip ao split[0] e split[1} porque são o sender e o msg, irrelevantes
			for(int i = 2; i<split.length; i++) {
				//percorre a matriz e se for igual quer dizer que o user ainda não viu a mensagem
				if(split[i].equals(user.getUsername()))
					notSeen = true;
			}
			if(notSeen) {
				//Se ainda não viu ele devolve o sender mais a mensagem que ainda nao viu
				retorno.append("sender: " + split[0] + " Msg: " + split[1]+"\n");
				FileManager collect=group.getGroupCollectFileManager();
				FileManager history=group.getGroupHistoryFileManager();
				//Remove o viewer user de certa linha s
				collect.collectRemoveViewer(s, user.getUsername());
				//adiciona mensagem ao history se ela ainda não existe
				addMsgToHistory(history,split[0],split[1]);
				String historyMsg=split[0]+":"+split[1];
				//adiciona a uma linha do history um viewer user
				history.historyAddViewer(historyMsg,user.getUsername());
			}
		}
		return retorno.toString();
	}


	/**
	 * 
	 * @param history
	 * @param sender
	 * @param msg
	 * @return
	 * @throws IOException 
	 */
	private void addMsgToHistory(FileManager history,String sender, String msg) throws IOException {
		ArrayList<String> list=history.fileToList();
		String[] split=null;
		boolean msgExists=false;
		for(String s:list) {
			split=s.split(":");
			//Vê se uma mensagem sender+msg já existe 
			msgExists=split[0].equals(sender) && split[1].equals(msg);
		}
		if(!msgExists) {
			//se não exister ele escreve no groupHistory.txt só o sender+msg e começa sem viewers
			String write=sender+":"+msg;
			history.writeFile(write);
		}
	}

}







