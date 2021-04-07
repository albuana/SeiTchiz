package client.handler;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import cipher.CipherHandler;
import client.Client;
import client.exceptions.UserCouldNotSendException;

/**
 * Handles the collect messages operations
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class CollectMessageHandler {
	
	private String groupID;

	public CollectMessageHandler(String groupID) {
		this.groupID = groupID;
	}

	public Object collect() throws UserCouldNotSendException, InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException {
		Client.getInstance().send("collect",groupID);
		Object response = Client.getInstance().receive();

		if(response instanceof String)
			return response;
		
		List<Object[]> result = (List<Object[]>) response;
		String sender;
		byte[] content;
		byte[] Encryptedkey;
		PrivateKey pk=(PrivateKey) Client.getInstance().getPrivateKey();
		
		StringBuilder sb=new StringBuilder("");
		for(Object[] o:result) {
			sender=(String) o[0];
			content=(byte[]) o[1];
			Encryptedkey=(byte[]) o[2];
			Key key=CipherHandler.decryptKey(Encryptedkey, Cipher.SECRET_KEY,pk,"AES");
			byte[] decryptedMessage=CipherHandler.decryptMessage(key,content);
			sb.append("Sender: "+sender+" Msg: "+new String(decryptedMessage)+"\n");
		}
		return sb.toString();
	}
}
