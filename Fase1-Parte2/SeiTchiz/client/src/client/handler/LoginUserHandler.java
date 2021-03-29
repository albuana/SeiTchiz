package client.handler;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import cipher.CipherHandler;
import client.Client;
import client.exceptions.UserCouldNotSendException;

/**
 * Handles user login with server
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class LoginUserHandler {

	private static final String PASS_TRUSTSTORE = "ourserverpass"; //
	private static final String CLIENT_ALIAS = "keyRSA";

	private String userID;
	private String pass;
	private String pathToKeystore;
	private String pathToTruststore;
	private String password;
	private String alias;
	private String keystoreType;

	/**
	 * 
	 * @param userID the user's username
	 * @param pass the user's password
	 * @param string2 
	 * @param string 
	 * @param keystorePassword 
	 * @param truststore 
	 */
	public LoginUserHandler(String userID,String pathToKeystore,String pathToTruststore, String password,String alias, String keystoreType) {
		this.userID = userID;
		this.pathToKeystore = pathToKeystore;
		this.pathToTruststore = pathToTruststore;
		this.password = password;
		this.alias = alias;
		this.keystoreType = keystoreType;
	}

	/**
	 * Sends a call for the login function in the server and waits for the response
	 * @return true if the user was able to login and a string if an error occurred if anything failed
	 * @throws UserCouldNotSendException
	 */
	public Object login() throws UserCouldNotSendException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnrecoverableKeyException, SignatureException, NoSuchPaddingException {
		Client client = Client.getInstance();
		client.send("login",userID);
		//if flag == true then this is the first login of this user
		boolean flagNewUser = (boolean) client.receive();
		byte [] signedNonce = (byte[]) client.receive(); //E(privateKeyServidor, nonce)
		byte [] nonce = (byte[]) client.receive(); //D(pubServidor,E(privateKeyServidor, nonce)) = nonce


		PublicKey pubkey = CipherHandler.getPublicKeyFromPathToKeystore(pathToTruststore, PASS_TRUSTSTORE, alias, keystoreType);
		if(!CipherHandler.verifySignature(pubkey, nonce, signedNonce)) {
			//TODO
			//exception a assinatura esta errada
			System.out.println("a");
		}
		PrivateKey pk = CipherHandler.getPrivateKeyFromKeystorePath(pathToKeystore, password, CLIENT_ALIAS, keystoreType);
		byte[] clientSignedNounce = CipherHandler.sign(nonce, pk);

		if(flagNewUser) {
			//TODO
			//falta a parte do "Type your name:"
			Certificate cert = CipherHandler.getCertificate(pathToKeystore, password, CLIENT_ALIAS, keystoreType);		
			client.send(cert);
		}
		client.send(nonce, clientSignedNounce);

		return Client.getInstance().receive();
	}




}
