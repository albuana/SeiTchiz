package server.catalog;

import java.util.ArrayList;
import java.util.List;

import server.domain.Group;
import server.domain.User;

public class GroupCatalog {
	private static GroupCatalog INSTANCE = null;
	private static ArrayList<Group> groupList = null;
	
	private GroupCatalog () {
		groupList = new ArrayList<Group>();
	}
	
	public static GroupCatalog getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new GroupCatalog();
		}
		return INSTANCE;
	}
	
	public static Group getGroup(String groupId) {
		for(Group a:groupList) {
			if(a.getGroupId().equals(groupId))
				return a;
		}
		return null;
	}
	public static boolean addGroup(Group grupo) {
		if(groupList.contains(grupo)) {
			return false;
		}
		groupList.add(grupo);
		return true;
		
	}
	
	public static String userIsOwner(User user) {
		StringBuilder ret=new StringBuilder();
		ArrayList<String> ehDono=new ArrayList<String>();
		ArrayList<String> pertence=new ArrayList<String>();

		for (int i = 0; i < groupList.size(); i++){
			if(groupList.get(i).getOwner()==user) {
				ehDono.add(groupList.get(i).getGroupId());
			}
			if(groupList.get(i).members.contains(user)) {
				pertence.add(user.getUsername());
			}
		}
		
		if(ehDono==null)
			ret.append("Não é dono de nenhum grupo /n");
		else {
			ret.append("É dono de: \n");
			for (int i = 0; i < ehDono.size(); i++){
				ret.append(ehDono.get(i)+"\n");
			}
		}
		if(pertence==null) {
			ret.append("Não pertence a nenhum grupo /n");
		}
		else {
			ret.append("Pertence a: \n");
			for (int i = 0; i < pertence.size(); i++){
				ret.append(pertence.get(i)+"\n");
			}
		}
		
		return ret.toString();
		
	}

}	

