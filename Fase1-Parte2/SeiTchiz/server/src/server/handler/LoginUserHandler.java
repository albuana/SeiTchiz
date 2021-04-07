package server.handler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Random;

import cipher.CipherHandler;
import server.catalog.UserCatalog;
import server.domain.User;
import server.exceptions.UserCouldNotLoginException;


/**
 * Handles login or registration of user
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class LoginUserHandler {

	private String userID;

	//private String passwordUser;

	private byte[] originalNonce;


	public LoginUserHandler(String userID) {
		this.userID = userID;

	}

	/**
	 * @return flag for if the is already registered
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws CertificateException 
	 */
	public boolean loginGetFlag() throws IOException, ClassNotFoundException, CertificateException {
		Boolean flag = false;

		UserCatalog users = UserCatalog.getInstance();
		if(users.getUser(userID) == null) {
			flag = true;
		}
		return flag;
	}

	/**
	 * @param userID
	 * @param password
	 * @return
	 * @throws IOException
	 */
//	public boolean register(String userID, String password) throws IOException {
//		UserCatalog users = UserCatalog.getInstance();
//		User newUser=new User(userID, password);
//		if(users.getUser(userID)==null) {
//			return true;
//		}
//		return false;
//	}

	/**
	 * 
	 * @param nounce
	 * @param signedNounce
	 * @return
	 * @throws IOException
	 * @throws UserCouldNotLoginException
	 * @throws SignatureException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws ClassNotFoundException 
	 * @throws CertificateException 
	 */
	public boolean login(byte[] nonceClient, byte[] signedNonce) throws IOException, UserCouldNotLoginException, InvalidKeyException, NoSuchAlgorithmException, SignatureException, ClassNotFoundException, CertificateException {
		//provar que o cliente tem acesso à chave privada daquele utilizador, i.e., que é quem diz ser
		//nonce enviado igual ao recebido
		if(!Arrays.equals(nonceClient,originalNonce))
			return false;
		//procurar certificado do cliente
		User user = UserCatalog.getInstance().getUser(userID);
		//verificao da assinatura
		if(!CipherHandler.verifySignature(user.getPublicKey(), originalNonce, signedNonce))
			return false;
		return true;
	}

	/**
	 * 
	 * @param name
	 * @throws IOException
	 */
	//	public void registerToFile(String name) throws IOException {
	//		UserCatalog users = UserCatalog.getInstance();
	//		User newUser=new User(userID, passwordUser);
	//		users.addUser(newUser, name);
	//	}

	/**
	 * 
	 * @return login nonce
	 */
	public byte[] loginGetNonce() {
		long generatedLong = new Random().nextLong();
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(generatedLong);
		this.originalNonce = buffer.array();
		return this.originalNonce;
	}

	public boolean register(Certificate userCert, byte[] nounceClient, byte[] signedNounce) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException, ClassNotFoundException, IOException, CertificateException {
		//provar que o cliente tem acesso ah chave privada daquele utilizador, i.e., que eh quem diz ser
		//nounce enviado igual ao recebido
		if(!Arrays.equals(nounceClient,originalNonce))
			return false;
		PublicKey pubkey = userCert.getPublicKey();
		//verificaho da assinatura
		if(!CipherHandler.verifySignature(pubkey, originalNonce, signedNounce))
			return false;
		// userID:ficheiroCertificado no users.txt
		User user = new User(userID, pubkey);
		UserCatalog.getInstance().addUser(user,userCert);

		//cat

		return true;
	}

}