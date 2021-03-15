package server.domain;

import java.util.ArrayList;

/**
 * 
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
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
	 * @return content of the message
	 */
	public final String getContent() {
		return content;
	}

	/**
	 * @return the sender
	 */
	public final User getSender() {
		return sender;
	}
	
	/**
	 * @return the group 
	 */
	public final Group getGroup() {
		return group;
	}
	
	/**
	 * Gets the users that view the message
	 * @return
	 */
	public final ArrayList<User> getViewers() {
		return viewers;
	}

	public String viewersToString() {
        StringBuilder retorno=new StringBuilder();
        for(User u:viewers) {
            if(u.getUsername()!=sender.getUsername())
                retorno.append(u.getUsername()+":");
        }
        if(retorno.length()!=0) {
            retorno.setLength(retorno.length() - 1);

        }
        return retorno.toString();
    }
}