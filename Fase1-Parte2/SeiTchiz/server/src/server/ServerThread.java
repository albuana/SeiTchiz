package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import server.handler.LoginUserHandler;
import server.handler.RequestHandler;
import server.catalog.UserCatalog;
import server.domain.User;
import server.exceptions.NotWellFormedException;
import server.exceptions.UserCouldNotLoginException;

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
				String password = (String) list.get(2);

				LoginUserHandler loginUserHandler = new LoginUserHandler(userID, password);

				//flag that tell us if the user is new or not
				Boolean flagNewUser = loginUserHandler.loginGetFlag();
				send(flagNewUser);

				//what client sends, the server will recieve:
				boolean success = false;
				if(flagNewUser.booleanValue()) {
					
					//if flag is true, that means tha user dont exists in system
                    success = loginUserHandler.register(userID,password);
                    send(true);
                    List<Object> lista = (ArrayList<Object>) inStream.readObject();
                    String name=(String)lista.get(0);
                    loginUserHandler.registerToFile(name);
                    System.out.println("The client is new in sistem.\n");
                    
                }else {
                	
                	//if flag is false, that means tha user already exists in system
                    success = loginUserHandler.login(userID,password);
                    //send(false);
                    System.out.println("The client already belongs to the sistem.\n");
                }

				//if login was made with success
				if(success) {
                    send(success);
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
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
}