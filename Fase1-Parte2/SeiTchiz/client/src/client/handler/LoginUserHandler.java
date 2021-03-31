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
	private static final String CLIENT_ALIAS = "ourclientpass";

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
//	public Object login() throws UserCouldNotSendException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnrecoverableKeyException, SignatureException, NoSuchPaddingException {
//		
//		
//		
////		Client client = Client.getInstance();
////		
////		//Cliente envia clientID ao servidor pedindo para se autenticar
////        client.send("login",userID);
////
////        //O servidor responde com um nonce aleatório de 8 bytes
////        byte [] signedNonce = (byte[]) client.receive(); //E(privateKeyServidor, nonce)
////        
////        byte [] nonce = (byte[]) client.receive(); //D(pubServidor,E(privateKeyServidor, nonce)) = nonce
////        
////        boolean flagNewUser = (boolean) client.receive();
////
////        //Se clientD ainda não tiver sido registado
////        if(flagNewUser) {
////        	//o cliente efetua o registo do utilizador enviando ao servidor o nonce recebido,
////            client.send(signedNonce);
////            
////            //O registo eh bem-sucedido se e somente se o servidor verificar 
////            //a assinatura do nonce 
////    		PublicKey pubKey = CipherHandler.getPublicKeyFromPathToKeystore(pathToTruststore, PASS_TRUSTSTORE, alias, keystoreType);
////    		if(!CipherHandler.verifySignature(pubKey, nonce, signedNonce)) {
////    			//TODO
////    			//exception a assinatura esta errada
////    			System.out.println("Assinatura não eh verificada");
////    		}
////            
////            //a sua assinatura deste gerada com a chave privada do utilizador
////            PrivateKey pk = CipherHandler.getPrivateKeyFromKeystorePath(pathToKeystore, password, CLIENT_ALIAS, keystoreType);
////            byte[] clientSignedNounce = CipherHandler.sign(signedNonce, pk);
////            
////            //, e o certificado com a chave pública correspondente
////            Certificate cert = CipherHandler.getCertificate(pathToKeystore, password, CLIENT_ALIAS, keystoreType);
////            client.send(clientSignedNounce);
////            client.send(cert);
////        }
//		
//		
//		Client client = Client.getInstance();
//		client.send("login",userID);
//		//if flag == true then this is the first login of this user
//		boolean flagNewUser = (boolean) client.receive();
//		byte [] signedNonce = (byte[]) client.receive(); //E(privateKeyServidor, nonce)
//		byte [] nonce = (byte[]) client.receive(); //D(pubServidor,E(privateKeyServidor, nonce)) = nonce
////
////
//		System.out.println("pubkey: " + pathToTruststore + ", " + PASS_TRUSTSTORE + ", " + alias + ", " + keystoreType + "\n");
////		
//		
////		O login é bem-sucedido se e somente se o servidor verificar a
//		//assinatura do nonce (que deve ser o mesmo gerado e enviado no
//		//passo 1) usando a chave pública associada ao clientID. 
//		//Caso não se verifique a assinatura, é enviada uma mensagem de
//		//erro ao cliente informando que a autenticação não foi bem-sucedida.
//		
//		// PASS_TRUSTSTORE - ourserverpass
//		
//		PublicKey pubKey = CipherHandler.getPublicKeyFromPathToKeystore(pathToTruststore, PASS_TRUSTSTORE, alias, keystoreType);
//		if(!CipherHandler.verifySignature(pubKey, nonce, signedNonce)) {
//			//exception a assinatura esta errada
//			System.out.println("a");
//		}
//		
//		//Se clientID estiver registado no servidor,
//		//o cliente assina o nonce recebido utilizando 
//		//a chave privada do utilizador
//		//
//		System.out.println("pk: " + pathToTruststore + ", " + password + ", " + CLIENT_ALIAS + ", " + keystoreType + "\n");
//
//		PrivateKey pk = CipherHandler.getPrivateKeyFromKeystorePath(pathToKeystore, password, CLIENT_ALIAS, keystoreType);
//		byte[] clientSignedNounce = CipherHandler.sign(nonce, pk);
////
//		if(flagNewUser) {
//			//falta a parte do "Type your name:"
//			Certificate cert = CipherHandler.getCertificate(pathToKeystore, password, CLIENT_ALIAS, keystoreType);		
//			client.send(cert);
//		}
//		
//		//e envia esta assinatura ao servidor
//		client.send(nonce, clientSignedNounce);
//
//		return Client.getInstance().receive();
//	}
//
//	
//	
	
	public Object login() throws UserCouldNotSendException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnrecoverableKeyException, SignatureException, NoSuchPaddingException {
        Client client = Client.getInstance();
        client.send("login",userID);
        //if flag == true then this is the first login of this user
//		byte [] signedNonce = (byte[]) client.receive(); //E(privateKeyServidor, nonce)
        byte [] nonce = (byte[]) client.receive(); //D(pubServidor,E(privateKeyServidor, nonce)) = nonce
        boolean flagNewUser = (boolean) client.receive();
//        System.out.println(nonce);
//        System.out.println(flagNewUser);
        
//		PublicKey pubKey = CipherHandler.getPublicKeyFromPathToKeystore(pathToTruststore, PASS_TRUSTSTORE, alias, keystoreType);
//		if(!CipherHandler.verifySignature(pubKey, nonce, signedNonce)) {
//			//exception a assinatura esta errada
//			System.out.println("a");
//		}
        
        //TODO tem de se verificar a assinatura no client???
        
        
    	System.out.println("alias : " + alias);
    	System.out.println("password : " + password + " " + CLIENT_ALIAS);

    	
        PrivateKey pk=CipherHandler.getPrivateKeyFromKeystorePath(pathToKeystore, password, CLIENT_ALIAS, keystoreType);
//		System.out.println(pk);

        byte[] clientSignedNounce=CipherHandler.sign(nonce,pk);
        
        if(flagNewUser) {
        	
        	System.out.println("alias: " + alias);
            Certificate cer=CipherHandler.getCertificate(pathToKeystore, password, CLIENT_ALIAS, keystoreType);
            client.send(nonce,clientSignedNounce,cer);
        }
        else {
            client.send(nonce,clientSignedNounce);
        }

        return Client.getInstance().receive();
    }



}
