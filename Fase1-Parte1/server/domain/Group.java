package server.domain;

import java.util.ArrayList;

public class Group {
	public User user;
	public String groupId;
	public ArrayList<User> members;
	
	public Group(String groupId, User user) {
		this.user=user;
		this.groupId=groupId;
		this.members=new ArrayList<User>();
	}

	public String getGroupId() {
		return groupId;
	}
	public User getOwner() {
		return user;
	}
	public void addMember(User newbie) {
		if(!members.contains(newbie))
			members.add(newbie);
	}

	public void removeMember(User newbie) {
		if(!members.contains(newbie))
			members.remove(newbie);
	}
	
	
	public String info() {
		StringBuilder ret=new StringBuilder("O grupo tem: "+user.getUsername()+" como dono /n");
		ret.append("Os utilizadores pertencentes ao grupo saho: /n");
		for (int i = 0; i < members.size(); i++){
			ret.append("Utilizador: "+members.get(i).getUsername());
		}
		return ret.toString();
	        
	}

}
