package server;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

import cipher.CipherHandler;

/**
 * 
 * Initiates the server socket to be used as well as waiting for need to create new server threads that will wait
 * to the requests done by the corresponding client 
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class Server {

	private static Server INSTANCE = null; 
	public static final String DATA_PATH = "./Fase1-Parte2/SeiTchiz/server/Data/";

	public static final String KEY_STORE_ALIAS = "ourserverpass";
	public static final String KEY_STORE_TYPE = "JCEKS"; //para armazenar chaves simétricas
	public static final String POST_PATH = "./Fase1-Parte2/SeiTchiz/server/Data/posts";

	private SSLServerSocket serverSocket;

	private int port;
	private final String keystorePassword;
	private final KeyStore keystore;

	public static Server getInstance() {
		return INSTANCE;
	}

	/**
	 * 
	 * @param port Port of the server
	 * @param keystorePath Path where the keystore.server is stored
	 * @param keystorePassword Password of the keystore
	 * @return a server instance
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static Server create(int port, String keystorePath, String keystorePassword) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		//		System.out.println("bbbbb    "+new Server(port,keyStorePath,keyStorePassword));
		INSTANCE = new Server(port,keystorePath,keystorePassword);
		return INSTANCE;

	}

	/**
	 * 
	 * @param port Port of the server
	 * @param keystorePath Path where the keystore.server is stored
	 * @param keystorePassword Password of the keystore
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	private Server(int port, String keystorePath, String keystorePassword) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		//		System.out.println(keystorePath);
		//lets set the properties
		System.setProperty("javax.net.ssl.keyStore", keystorePath);
		System.setProperty("javax.net.ssl.keyStorePassword", keystorePassword);
		System.setProperty("javax.net.ssl.keyStoreType", "jceks");
		this.keystorePassword = keystorePassword;
		System.out.println("keystore server: " + keystorePath + "; " + keystorePassword + "; " + KEY_STORE_TYPE);
		keystore = CipherHandler.getKeystore(keystorePath, keystorePassword, KEY_STORE_TYPE);
		this.port = port;   
	}

	/**
	 * Starts the server socket and the loop for new server threads.
	 */
	public void startServer () throws IOException{

		try {
			//substituir a ligacao TCP por uma ligacao TLS/SSL ----> vai garantir a itegridade e confidencialidade de toda a comunicacao
			ServerSocketFactory serversocketfactory = SSLServerSocketFactory.getDefault();
			serverSocket =  (SSLServerSocket) serversocketfactory.createServerSocket(this.port);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while(true) {
			Socket clientSoket = serverSocket.accept();
			ServerThread newThread = new ServerThread(clientSoket);
			newThread.start();
		}
	}

	/**
	 * @return keystore
	 */
	public KeyStore getKeystore() { 
		return keystore;
	}

	/**
	 * 
	 * @return keystorePassword
	 */
	public String getKeystorePassword() { 
		return keystorePassword;
	}

}
