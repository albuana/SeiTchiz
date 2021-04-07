package client.handler;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import cipher.CipherHandler;
import client.Client;
import client.exceptions.UserCouldNotSendException;

/**
 * Handles the Send message to a group operation
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class GroupMessageHandler {
	
	private String content;
	private String groupId;

	public GroupMessageHandler(String groupId,String content) {
		this.content= content;
		this.groupId = groupId;
	}
	
	public Object sendmsg() throws UserCouldNotSendException, InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException {
		//Pede groupKey de user		
		Client.getInstance().send("getEncryptedGroupKeyUser",groupId);
		Object response = Client.getInstance().receive();

		byte[] enc=(byte[]) response;
		
		//Dono faz unwrap da key com a privateKey do cliente
		Key key=CipherHandler.decryptKey(enc, Cipher.SECRET_KEY,Client.getInstance().getPrivateKey(),"AES");
		//Encrypta mensagem com a unwrapped key e envia;	
		byte[] encryptedMessage=CipherHandler.encryptMessage(key,content.getBytes());
		Client.getInstance().send("msg",groupId,encryptedMessage);
		String res=(String) Client.getInstance().receive();
		return res;
	}

}
