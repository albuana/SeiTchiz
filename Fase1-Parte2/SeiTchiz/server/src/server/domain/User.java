package server.domain;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * Class that handles a user's information
 */
public class User {
	private String username;	
	private String password;
	
	private PublicKey pubkey;

	
	private HashMap <String, List<Object>> groupMessages;


	
	/**
	 * User constructor
	 * @param username user's username
	 * @param password user's password
	 */
	public User(String username, PublicKey pubkey) {
		this.username = username;
		this.pubkey = pubkey;
	}
	
	/**
	 * 
	 * @return the user's username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * 
	 * @return the user's public key
	 * @since 1.0
	 */
	public String getPassword() {
		return password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}


	
	public String toString() {
		return username;
	}
	
	
	public List<Object> seeMessagesGroup(String groupId) {
		if(groupMessages.containsKey(groupId)) {
			List<Object> messages=groupMessages.get(groupId);
			for(int i=0;i<groupMessages.get(groupId).size();i++) {
				groupMessages.get(groupId).remove(i);
			}
			return messages;
		}
		else {
			System.out.println("That group doesn't exist.");
			return null;
		}
	}
	
	public void sentMessageToGroup(String groupId, Object object) {
		groupMessages.get(groupId).add(object);	
	}
	
	public void removeGroup(String groupId) {
		if(groupMessages.containsKey(groupId))
			groupMessages.remove(groupId);
	}
	
	public void addGroup(String groupId) {
		if(!groupMessages.containsKey(groupId)) {
			groupMessages.put(groupId, null);		
		}
		
	}

	public PublicKey getPublicKey() {
		return pubkey;
	}

	

}
