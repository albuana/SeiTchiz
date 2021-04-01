
package server.handler;
import java.io.IOException;
import java.util.ArrayList;
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
	 * @throws NothingToReadException 
	 * @throws ClassNotFoundException 
	 */
	public String collect() throws IOException, NothingToReadException, ClassNotFoundException {
		FileManager groupFile = group.getGroupCollectFileManager();
		String retorno = getMessages(groupFile.loadContent());

		if(retorno.length()==0)
			throw new NothingToReadException();

		return retorno;
	}

	/**
	 * 
	 * @param list
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	private String getMessages(List<String> list) throws IOException, ClassNotFoundException {
		StringBuilder retorno = new StringBuilder();
		boolean notSeen = false;
		//corre cada linha do file 
		for(String s:list) {
			//separa cada string e fica do tipo sender:msg:(goncalo:tiago)->viewers mas numa matriz
			String[] split = s.split(":");
			notSeen = false;
			//for d� skip ao split[0] e split[1} porque s�o o sender e o msg, irrelevantes
			for(int i = 1; i<split.length; i++) {
				//percorre a matriz e se for igual quer dizer que o user ainda n�o viu a mensagem
				if(split[i].equals(user.getUsername()))
					notSeen = true;
			}

			if(notSeen) {
				//Se ainda n�o viu ele devolve o sender mais a mensagem que ainda nao viu
				retorno.append("\nsender: " + split[0] + " Msg: " + split[1]+"\n");
				FileManager collect=group.getGroupCollectFileManager();
				//Remove o viewer user de certa linha s
				collect.collectRemoveViewer(s, user.getUsername());
				
				//quando uma mensagem eh lida por um determinado utilizador esta passa para o seu historico pessoal
				group.getHistoryFile(user).writeFile("sender: " + split[0] + " Msg: " + split[1]+"\n");


				//Quando uma mensagem eh lida por todos os utilizadores, esta eh removida da caixa de mensagens e passa para o group history
				FileManager history=group.getHistoryFile(user);
				CheckAddMsgToHistory(history, collect);

			}
		}
		return retorno.toString();
	}

	/**
	 * 
	 * @param history
	 * @param collect
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	private void CheckAddMsgToHistory(FileManager history, FileManager collect) throws IOException, ClassNotFoundException {
		//V� se no collect existe mensagens do tipo sender:msg sem viewers e se houver remove e adiciona ao history
		List<String> collectMessages=collect.loadContent();
		List<String> collectHistory=history.loadContent();
		for(String c:collectMessages) {
			String[] split=c.split(":");
			if(split.length==2 && !collectHistory.contains(c)) {
				collect.removeFromFile(c);
			}
		}
	}


}







