
package server.handler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
			//for d� skip ao split[0] e split[1} porque s�o o sender e o msg, irrelevantes
			for(int i = 1; i<split.length; i++) {
				//percorre a matriz e se for igual quer dizer que o user ainda n�o viu a mensagem
				if(split[i].equals(user.getUsername()))
					notSeen = true;
			}

			if(notSeen) {
				//Se ainda n�o viu ele devolve o sender mais a mensagem que ainda nao viu
				retorno.append("sender: " + split[0] + " Msg: " + split[1]+"\n");
				FileManager collect=group.getGroupCollectFileManager();
				//Remove o viewer user de certa linha s
				collect.collectRemoveViewer(s, user.getUsername());

				//Quando uma mensagem eh lida por todos os utilizadores, esta eh removida da caixa de mensagens e passa para o group history
				FileManager history=group.getGroupHistoryFileManager();
				CheckAddMsgToHistory(history, collect);

			}
		}
		return retorno.toString();
	}

//	public List<Object> collect2() throws IOException {
//
//		FileManager groupFile = group.getGroupCollectFileManager();
//		ArrayList<String> fileContent = groupFile.fileToList();
//		ArrayList<String> newFileContent = (ArrayList<String>) fileContent.clone();
//
//		String username = user.getUsername();
//		List<Object> result = new ArrayList<>();
//
//		int count = 0;
//
//		for(int i = 0; i < fileContent.size(); i+=4) {
//
//			List<String> users = new ArrayList<String>(Arrays.asList(fileContent.get(i).split(":")));
//
//			String message = fileContent.get(i + 2);
//			if(users.contains(username)) {
//
//				result.add(new Object [] {message});
//				users.remove(username);
//
//				if(users.isEmpty()) {
//					
//					
//					
//					newFileContent.remove(count);
//					newFileContent.remove(count);
//					newFileContent.remove(count);
//					count -= 4;
//				} else {
//					newFileContent.set(count, String.join(" ", users));
//
//				}
//				count +=4;
//			}
//			
//			
//			
//		}
//		return result;
//
//	}




	/**
	 * 
	 * @param history
	 * @param collect
	 * @throws IOException
	 */
	private void CheckAddMsgToHistory(FileManager history, FileManager collect) throws IOException {
		//V� se no collect existe mensagens do tipo sender:msg sem viewers e se houver remove e adiciona ao history
		ArrayList<String> collectMessages=collect.fileToList();
		ArrayList<String> collectHistory=history.fileToList();
		for(String c:collectMessages) {
			String[] split=c.split(":");
			if(split.length==2 && !collectHistory.contains(c)) {
				history.writeFile(c+"\n");
				collect.removeFromFile(c);
			}
		}
	}


}







