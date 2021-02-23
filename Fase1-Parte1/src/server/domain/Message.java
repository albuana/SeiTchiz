package server.domain;



public class Message {
	
	private User sender;
	private String content;
	private Group group;
	
	/**
	 * Message Class constructor
	 * @param sender sender of the message
	 * @param content content of the message
	 */
	public Message(User sender,String content, Group group) {
		this.sender = sender;
		this.content = content;
		this.group = group;
	}
	
	/**
	 * 
	 * @return content of the message
	 */
	public final Object getContent() {
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
