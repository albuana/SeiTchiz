package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * 
 * Initiates the server socket to be used as well as waiting for need to create new server threads that will  to the requests done by the corresponding client 
 */
public class Server {
	
	private static Server INSTANCE = null;
	public static final String DATA_PATH = "./Data/";
	
	private int port;
	
	public static Server getInstance() {
		return INSTANCE;
	}
	
	/**
	 * 
	 * @param port the server's port 
	 * @throws IOException 
	 */
	public static Server create(int port) throws IOException {
		INSTANCE = new Server(port);
		return INSTANCE;

	}
	
	/**
	 * 
	 * @param port the server's port 
	 * @throws IOException 
	 */
	private Server(int port) throws IOException {
		this.port = port;
	}
	
	/**
	 * Starts the server socket and the loop for new server threads
	 */
	public void startServer (){
		
		//TODO
		
	}
	
}
