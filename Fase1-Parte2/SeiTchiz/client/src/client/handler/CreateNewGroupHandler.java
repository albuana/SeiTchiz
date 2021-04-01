package client.handler;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import cipher.CipherHandler;
import client.Client;
import client.exceptions.UserCouldNotSendException;

/**
 * Handles the Create New Group operation
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class CreateNewGroupHandler {

	private String groupID;

	/**
	 * Create New Group Handler Constructor
	 * @param groupId
	 */
	public CreateNewGroupHandler(String groupID) {
		this.groupID = groupID;
	}

	/**
	 * Executes the client side operation of the create new Group operation
	 * @return the result of the operation
	 * @throws UserCouldNotSendException
	 * @throws NoSuchAlgorithmException 
	 * @throws NoSuchPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 */
	public Object newgroup() throws UserCouldNotSendException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException {
		
		//criar uma chave de grupo simetrica
		SecretKey secKey = CipherHandler.generateKey("AES");
		
		//chave publica do dono
		PublicKey publicKey = Client.getInstance().getPublicKey();
		
		//cifrar a chave simetrica do grupo com a chave publica do cliente 
		byte [] encriptedKey = CipherHandler.encrypt(secKey, publicKey);
		
		//enviar ao cliente
		Client.getInstance().send("newgroup",groupID, encriptedKey);
		
		return Client.getInstance().receive();
	}

}

