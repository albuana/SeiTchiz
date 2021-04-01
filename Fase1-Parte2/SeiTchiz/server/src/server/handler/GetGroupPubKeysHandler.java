package server.handler;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import server.catalog.GroupCatalog;
import server.domain.Group;
import server.domain.User;
import server.exceptions.group.GroupNotExistException;
import server.exceptions.group.UserNotOwnerException;

public class GetGroupPubKeysHandler {

	private Group group;
	private User user;

	public GetGroupPubKeysHandler(String groupID, User user) throws GroupNotExistException {
		this.group = GroupCatalog.getInstance().getGroup(groupID);
		this.user = user;
		if(group == null)
			throw new GroupNotExistException();  
	}
	
	public List<PublicKey> getPubKeys() throws UserNotOwnerException{
		if(group.getOwner() != null && !user.equals(group.getOwner())){
			throw new UserNotOwnerException();
		}
		List<PublicKey> result = new ArrayList<PublicKey>();
		group.getUsers().stream().forEach(u -> result.add(u.getPublicKey()));
		return result;
	}

}
