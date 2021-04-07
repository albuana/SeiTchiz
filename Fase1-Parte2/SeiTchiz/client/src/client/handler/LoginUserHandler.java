package client.handler;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

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
	
	public Object login() throws UserCouldNotSendException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnrecoverableKeyException, SignatureException, NoSuchPaddingException {
        Client client = Client.getInstance();
        client.send("login",userID);
        byte [] nonce = (byte[]) client.receive(); 
        boolean flagNewUser = (boolean) client.receive();
        
        //TODO tem de se verificar a assinatura no client???
    	
        PrivateKey pk=(PrivateKey) Client.getInstance().getPrivateKey();

        byte[] clientSignedNounce=CipherHandler.sign(nonce,pk);
        
        if(flagNewUser) {
            Certificate cer=CipherHandler.getCertificate(pathToKeystore, password, alias, keystoreType);
            client.send(nonce,clientSignedNounce,cer);
        }
        else {
            client.send(nonce,clientSignedNounce);
        }

        return Client.getInstance().receive();
    }



}
