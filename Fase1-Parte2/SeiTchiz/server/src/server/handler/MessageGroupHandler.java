package server.handler;

import java.io.IOException;
import java.security.cert.CertificateException;

import server.catalog.GroupCatalog;
import server.domain.Group;
import server.domain.Message;
import server.domain.User;
import server.exceptions.group.GroupNotExistException;
import server.exceptions.group.UserDoesNotBelongToGroupException;

/**
 * Handles sending a new text message to the group
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class MessageGroupHandler {

	private Message message;

	private User sender;
	
	private Group grupo;

	/**
	 * Constructor
	 * @param groupID
	 * @param content
	 * @param sender
	 * @throws GroupNotExistException
	 * @throws CertificateException 
	 * @throws ClassNotFoundException 
	 */
	public MessageGroupHandler(String groupID, byte[] content, User sender) throws GroupNotExistException, ClassNotFoundException, CertificateException {
		this.grupo = GroupCatalog.getInstance().getGroup(groupID);
		if(grupo == null)
			throw new GroupNotExistException();
		message = new Message(sender.getUsername(),content,grupo); 
		this.sender=sender;
	}

	/**
	 * @return
	 * @throws UserDoesNotBelongToGroupException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public String sendmsg() throws UserDoesNotBelongToGroupException, IOException, ClassNotFoundException{
		if(!message.getGroup().hasMember(sender))
			throw new UserDoesNotBelongToGroupException();
		//Tem que ser uma array de Objects para poder ser utilizado o writeObjects() porque nao modifica os bytes
		//Por isso não se pode escrever o User tem que ser a string do user
		grupo.sendMsgCollect(message,sender);
		grupo.sendMsgHistoryUser(message.getSender(),message.getContent(),this.grupo.getUserKey(sender),sender);

		return "Message Sent";	
	}
}