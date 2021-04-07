package server.domain;

/**
 * 
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class Message {
	
	private String sender;
	private byte[] content;
	private Group group;
	
	/**
	 * Message Class constructor
	 * @param sender sender of the message
	 * @param content content of the message
	 */
	public Message(String sender,byte[] content, Group group) {
		this.sender = sender;
		this.content = content;
		this.group = group;
	}
	
	/**
	 * @return content of the message
	 */
	public final byte[] getContent() {
		return content;
	}
	
	/**
	 * @return the sender
	 */
	public final String getSender() {
		return sender;
	}
	
	/**
	 * @return the group 
	 */
	public final Group getGroup() {
		return group;
	}
	public final String toString() {
		return sender+","+content;
	}
}