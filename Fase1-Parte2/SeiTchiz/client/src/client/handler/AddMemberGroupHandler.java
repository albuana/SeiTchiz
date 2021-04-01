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
 * Handles add user operations
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class AddMemberGroupHandler {
	private String groupID;
	private String newUser;

	/**
	 * Add Member Handler Constructor
	 * @param groupId
	 * @param newUser
	 */
	public AddMemberGroupHandler(String groupID, String newUser) {
		this.groupID = groupID;
		this.newUser=newUser;
	}

	/**
	 * Executes the client side operation of the create new Group operation
	 * @return the result of the operation
	 * @throws UserCouldNotSendException
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 */
	public Object addMember() throws UserCouldNotSendException, InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException {

		Client.getInstance().send("getGroupPublicKey",groupID);
		
		Object response = Client.getInstance().receive();

		response = Client.getInstance().receive();
		if(response instanceof String)
			return response;
		
		//o dono do grupo cria uma nova chave de grupo secreta
		SecretKey sk = CipherHandler.generateKey("AES");					

		//
		Object[] objs = (Object[]) response;
		
		//lista com as public keys de todos os users
		List<PublicKey> publicKeys = (List<PublicKey>) objs[0];
		
		//lista de users
		List<String> users = (List<String>) objs[1];
		
		//lista com as chaves encriptadas com as public keys dos utilizadores
		List<Object> list = new ArrayList<>();

		// cifrar a chave de grupo com a chave de cada um dos membros desse grupo
		for(int i =0; i<publicKeys.size(); i++) {
			byte[] encrypetedKye = CipherHandler.encryptKey(sk, publicKeys.get(i));
			//lista para enviar para o servidor
			list.add(new Object[] {users.get(i), encrypetedKye});
		}
		
		Client.getInstance().send("getUserPublicKey",newUser);
		
		PublicKey pk = (PublicKey)response;
		
		//incluindo o novo membro (!)
		list.add(new Object[] {newUser, CipherHandler.encryptKey(sk, pk)});

		//envia para o servidor
		Client.getInstance().send("addu",newUser,groupID,list);

		Object result = Client.getInstance().receive();
		return result;
	}
}
