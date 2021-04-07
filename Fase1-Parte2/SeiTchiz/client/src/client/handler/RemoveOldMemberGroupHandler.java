package client.handler;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import cipher.CipherHandler;
import client.Client;
import client.exceptions.UserCouldNotSendException;

/**
 * Handles remove user operations
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class RemoveOldMemberGroupHandler {
	
	private String groupID;
	private String oldUser;
	
	public RemoveOldMemberGroupHandler(String groupID, String oldUser) {
		this.groupID = groupID;
		this.oldUser=oldUser;
	}

	public Object removeMember() throws UserCouldNotSendException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException {
		Client.getInstance().send("getGroupPublicKey",groupID);
		
		Object response = Client.getInstance().receive();

		if(response instanceof String)
			return response;
		//o dono do grupo cria uma nova chave de grupo secreta
		SecretKey sk = CipherHandler.generateKey("AES");					

		Object[] objs = (Object[]) response;
		//lista com as public keys de todos os users
		List<PublicKey> publicKeys = (List<PublicKey>) objs[0];
		
		//lista de users
		List<String> users = (List<String>) objs[1];
		
		List<Object[]> list = new ArrayList<>();
		// cifrar a chave de grupo com a chave de cada um dos membros desse grupo
		for(int i =0; i<publicKeys.size(); i++) {
			if(!users.get(i).equals(oldUser)) {
				byte[] encrypetedKye = CipherHandler.encryptKey(sk, publicKeys.get(i));
				//lista para enviar para o servidor
				list.add(new Object[] {users.get(i), encrypetedKye});
			}

		}
		Client.getInstance().send("removeu",groupID,oldUser,list);

		Object result = Client.getInstance().receive();
		return result;
	}
}