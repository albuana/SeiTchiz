package client.handler;

import client.Client;

public class RemoveOldMemberGroupHandler {
	
	private String groupID;
	private String oldUser;
	
	public RemoveOldMemberGroupHandler(String groupID, String oldUser) {
		this.groupID = groupID;
		this.oldUser=oldUser;
	}

	public String removeMember() {
		Client.getInstance().send("removeu",groupID , oldUser);
		boolean res=(boolean) Client.getInstance().receive();
		if(res) 
			return oldUser+ " removido ao grupo " + groupID;
		return "Não foi possivel remover" + oldUser + " a " + groupID;
	}

}
