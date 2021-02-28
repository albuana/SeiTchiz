package client.handler;


import java.io.File;
import client.exceptions.UserCouldNotSendException;


/**
 * 
 * Handles the requests
 *
 */
public class RequestHandler {
	private static final String USERFILES_DIRECTORY = "./UserFiles/";
	/**
	 * Calls new group handler
	 * @param groupID
	 * @return
	 * @throws UserCouldNotSendException 
	 */
	
	public static String follow(String userID){
		return new FollowHandler().addFollower(userID);
	}

	public static Object unfollow(String userID){
		return new FollowHandler().unfollow(userID);
	}

	public static Object viewfollowers(){
		return new FollowHandler().viewFollowers();
	}

	public static Object post(String photo){
		File f = new File(USERFILES_DIRECTORY+photo);
		if(!f.exists())
			return "Foto nao encontrada";

		return new PostHandler().createPost(f);
	}

	public static Object wall(String nPhoto){
		return new PostHandler().wall(nPhoto);
	}

	public static Object like(String photoid){
		return new PostHandler().like(photoid);
	}
	
	public static Object newgroup(String groupID) throws UserCouldNotSendException {
		return new CreateNewGroupHandler(groupID).newgroup();
	}

	public static Object addu(String newUser, String groupId) throws UserCouldNotSendException {
		return new AddMemberGroupHandler(groupId,newUser).addMember();
	}

	public static Object removeu(String oldUser, String groupId) throws UserCouldNotSendException {
		return new RemoveOldMemberGroupHandler(groupId,oldUser).removeMember();
	}

	public static Object ginfo(String groupId){
		return new InfoHandler(groupId).getInfo();
	}

	public static Object ginfo(){
		return new InfoHandler().getInfo();
	}

	public static Object msg(String groupId, String content){
		return new GroupMessageHandler(groupId, content).sendmsg();
	}

	public static Object collect(String groupId) throws UserCouldNotSendException{
		return new CollectMessageHandler(groupId).collect();
	}

	public static Object history(String groupId) throws UserCouldNotSendException{
		return new HistoryMessageHandler(groupId).history();
	}






}
