package client.handler;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import client.exceptions.UserCouldNotSendException;

/**
 * Handles the requests
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class RequestHandler {

	/**
	 * Calls follow handler
	 * @param groupID
	 * @return
	 * @throws UserCouldNotSendException 
	 */
	public static String follow(String userID) throws UserCouldNotSendException{
		return new FollowHandler().addFollower(userID);
	}

	/**
	 * Calls unfollow handler
	 * @param userID
	 * @return
	 * @throws UserCouldNotSendException
	 */
	public static Object unfollow(String userID) throws UserCouldNotSendException{
		return new FollowHandler().unfollow(userID);
	}

	/**
	 * Calls viewfollowers handler
	 * @return
	 * @throws UserCouldNotSendException
	 */
	public static Object viewfollowers() throws UserCouldNotSendException{
		return new FollowHandler().viewFollowers();
	}

	/**
	 * Calls post photo handler
	 * @param photo
	 * @return
	 * @throws UserCouldNotSendException
	 */
	public static Object post(String photo) throws UserCouldNotSendException{
		return new PostHandler().createPost(photo);
	}

	/**
	 * Calls wall handler
	 * @param nPhoto
	 * @return
	 * @throws UserCouldNotSendException
	 */
	public static Object wall(String nPhoto) throws UserCouldNotSendException{
		return new PostHandler().wall(nPhoto);
	}

	/**
	 * Calls like handler
	 * @param photoid
	 * @return
	 * @throws UserCouldNotSendException
	 */
	public static Object like(String photoid) throws UserCouldNotSendException{
		return new PostHandler().like(photoid);
	}

	/**
	 * Calls newgroup handler
	 * @param groupID
	 * @return
	 * @throws UserCouldNotSendException
	 * @throws IllegalBlockSizeException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public static Object newgroup(String groupID) throws UserCouldNotSendException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException {
		return new CreateNewGroupHandler(groupID).newgroup();
	}

	/**
	 * Calls add user handler
	 * @param newUser
	 * @param groupID
	 * @return
	 * @throws UserCouldNotSendException
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 */
	public static Object addu(String newUser, String groupID) throws UserCouldNotSendException, InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException {
		return new AddMemberGroupHandler(groupID,newUser).addMember();
	}

	/**
	 * Calls remove user handler
	 * @param oldUser
	 * @param groupID
	 * @return
	 * @throws UserCouldNotSendException
	 */
	public static Object removeu(String oldUser, String groupID) throws UserCouldNotSendException {
		return new RemoveOldMemberGroupHandler(groupID,oldUser).removeMember();
	}

	/**
	 * Calls group info handler
	 * @param groupID
	 * @return
	 * @throws UserCouldNotSendException
	 */
	public static Object ginfo(String groupID) throws UserCouldNotSendException{
		return new InfoHandler(groupID).getInfo();
	}

	public static Object ginfo() throws UserCouldNotSendException{
		return new InfoHandler().getInfo();
	}

	/**
	 * Calls send message handler
	 * @param groupID
	 * @param content
	 * @return
	 * @throws UserCouldNotSendException
	 */
	public static Object msg(String groupID, String content) throws UserCouldNotSendException{
		return new GroupMessageHandler(groupID, content).sendmsg();
	}

	/**
	 * Calls collect messages handler
	 */
	public static Object collect(String groupID) throws UserCouldNotSendException{
		return new CollectMessageHandler(groupID).collect();
	}

	/**
	 * Calls group history handler
	 * @param groupID
	 * @return
	 * @throws UserCouldNotSendException
	 */
	public static Object history(String groupID) throws UserCouldNotSendException{
		return new HistoryMessageHandler(groupID).history();
	}
}