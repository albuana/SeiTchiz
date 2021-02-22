package server.domain;

import java.util.ArrayList;

import server.catalog.GroupCatalog;

public class Message {
	private String msg;	
	private ArrayList<User> viewers;

	/**
	 * Message constructor
	 * @param msg
	 */
	public Message(String msg, String groupId) {
		this.msg = msg;
		this.viewers = GroupCatalog.getInstance().getGroup(groupId).membersList;
	}
	
}
