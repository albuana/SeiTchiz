package server.handler;

import java.util.ArrayList;

import server.catalog.GroupCatalog;
import server.catalog.UserCatalog;
import server.domain.Group;
import server.domain.User;
import server.exceptions.group.GroupException;

public class UserInfoHandler {
	private User user;	
	private UserInfoHandler (User user) {
		this.user=user;	
	}
	public String getInfo() throws GroupException {
		GroupCatalog catalog = GroupCatalog.getInstance();
		return catalog.infoUser(user);
	}
}
