package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import cipher.CipherHandler;
import server.catalog.UserCatalog;
import server.domain.User;
import server.exceptions.NotWellFormedException;
import server.exceptions.UserCouldNotLoginException;
import server.handler.LoginUserHandler;
import server.handler.RequestHandler;

/**
 * Creates new thread for every client and executes functions and sends of objects from server
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class ServerThread extends Thread {
	private Socket socket = null;
	private ObjectOutputStream outStream;  
	private ObjectInputStream inStream; 
	private User currentUser;
	private Server server; //dummy

	/**
	 * @param inSocket the client's socket
	 * @throws IOException
	 */
	public ServerThread(Socket inSocket) throws IOException {
		this.socket = inSocket;
		this.outStream = new ObjectOutputStream(socket.getOutputStream());
		this.inStream = new ObjectInputStream(socket.getInputStream());
		server = Server.getInstance();
		System.out.println("\n----------------------------------------\n");
		System.out.println("thread do server para cada cliente\n");
	}

	/**
	 * @param object the object to send
	 * @return true if object was successfully sent
	 */
	public boolean send(Object object) {
		try {
			outStream.writeObject(object);
			outStream.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Runs the server thread responsible for receiving and dealing with a client's requests
	 */
	@SuppressWarnings("unchecked")
	public void run(){

		try {
			List<Object> list = (ArrayList<Object>) inStream.readObject();
			if(((String)list.get(0)).equals("login")) {
				String userID = (String) list.get(1);

				LoginUserHandler loginUserHandler = new LoginUserHandler(userID); 

				byte[] originalNonce = loginUserHandler.loginGetNonce();

				//flag that tell us if the user is new or not
				Boolean flagNewUser = loginUserHandler.loginGetFlag();
				send(flagNewUser);

				try {
					
					System.out.println(server.getKeystore() + "; " + server.getKeystorePassword() + "; " + Server.KEY_STORE_ALIAS);
					
					//the nounce noticed
					send(CipherHandler.sign(originalNonce, CipherHandler.getPrivateKeyFromKeystore
							(server.getKeystore(), server.getKeystorePassword(), Server.KEY_STORE_ALIAS))); 
				} catch (UnrecoverableKeyException | KeyStoreException e) {
					e.printStackTrace();
				}

				//what client sends, the server will recieve:
				boolean success = false;
				//if the user is new
				if(flagNewUser.booleanValue()) {

					//if flag is true, that means tha user dont exists in system
					Certificate userCert = (Certificate) list.get(0);
					List<Object> lista = (ArrayList<Object>) inStream.readObject();
					byte []  nounceClient = (byte[]) lista.get(0);
					byte [] signedNounce = (byte[]) lista.get(1);

					System.out.println("The client is new in sistem.\n");

					try {
						//let's try to regist
						success = loginUserHandler.register(userCert, nounceClient, signedNounce);
					} catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
						e.printStackTrace();
					}

				}else { //if user already exists in system, i mean the flag is false

					//means tha user already exists in system
					byte []  nounce = (byte[]) list.get(0);
					byte [] signedNounce = (byte[]) list.get(1);
					success = loginUserHandler.login(nounce,signedNounce);
					System.out.println("The client already belongs to the sistem.\n");
				}

				send(success);
				//if login was made with success
				if(success) {
					currentUser = UserCatalog.getInstance().getUser(userID);
				}
				else {
					send((new UserCouldNotLoginException()).getMessage());
				}

			} //login close

			while(true) {
				//clients function
				String function = null;
				List<Object> params = null;

				try {
					//call function
					List<Object> objs = (ArrayList<Object>) inStream.readObject();
					function = (String) objs.get(0);
					params = objs.subList(1, objs.size());

					params.add(currentUser);

					Class<?>[] c = new Class[params.size()];

					for (int i = 0; i < c.length; i++) {
						c[i] =  params.get(i).getClass();
					}

					System.out.println("Method: " + function);
					Method m = RequestHandler.class.getMethod(function, c);
					Object result = m.invoke(null , params.toArray());					
					System.out.println(result);
					send(result);

				}catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException e) {
					e.printStackTrace();
					break;
				}catch(NoSuchMethodException e) {
					send((new NotWellFormedException()).getMessage()); //send error to the user
				}catch(InvocationTargetException e) {
					send(e.getCause().getMessage()); //send error to the user
				}catch(SocketException | EOFException e) {
					System.out.println("----------------------------------------\n");
					System.out.println("The client disconnected from server.\n");
					System.out.println("----------------------------------------\n");
					break;
				} 
			}
			outStream.close();
			inStream.close();
			socket.close();

		} //try close
		catch (IOException | UserCouldNotLoginException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}