package server.domain;

import java.util.ArrayList;

public class Message {
	
	private User sender;
	private String content;
	private Group group;
	private ArrayList<User> viewers;
	
	/**
	 * Message Class constructor
	 * @param sender sender of the message
	 * @param content content of the message
	 */
	public Message(User sender,String content, Group group) {
		this.sender = sender;
		this.content = content;
		this.group = group;
		this.viewers=group.getUsers();
	}
	
	/**
	 * 
	 * @return content of the message
	 */
	public final String getContent() {
		return content;
	}

	/**
	 * 
	 * @return the sender
	 */
	public final User getSender() {
		return sender;
	}
	
	/**
	 * 
	 * @return the group 
	 */
	public final Group getGroup() {
		return group;
	}
	
	public final ArrayList<User> getViewers() {
		return viewers;
	}

	public String viewersToString() {
		StringBuilder retorno=new StringBuilder();
		for(User u:viewers) {
			retorno.append(u.getUsername()+":");
		}
		retorno.setLength(retorno.length() - 1);
		return retorno.toString();
	}
	
	
	
//	private String msg;	
//	private ArrayList<User> viewers;
//
//	/**
//	 * Message constructor
//	 * @param msg
//	 */
//	public Message(String msg, String groupId) {
//		this.msg = msg;
//		this.viewers = GroupCatalog.getInstance().getGroup(groupId).getUsers();
//	} 
	
}
