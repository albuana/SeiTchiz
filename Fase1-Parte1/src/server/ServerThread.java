package server;

import java.io.EOFException;
import java.io.FileOutputStream;
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
import server.exceptions.UserNotExistException;
import server.exceptions.group.UserCouldNotCreateGroupException;

/**
 * Creates new thread for every client and executes functions and sends of objects from server
 * @since 1.0
 */
public class ServerThread extends Thread{
	private Socket socket = null;
	private ObjectOutputStream outStream; 
	private ObjectInputStream inStream; 
	private User currentUser;
	private Server server;

	/**
	 * 
	 * @param inSoc the client's socket
	 * @throws IOException
	 * @since 1.0
	 */
	public ServerThread(Socket inSoc) throws IOException {
		this.socket = inSoc;
		this.outStream = new ObjectOutputStream(socket.getOutputStream());
		this.inStream = new ObjectInputStream(socket.getInputStream());
		server = Server.getInstance();
		System.out.println("thread do server para cada cliente");
	}

	/**
	 * 
	 * @param obj the object to send
	 * @return true if obj was successfully sent
	 * @since 1.0
	 */
	public boolean send(Object obj) {
		try {
			outStream.writeObject(obj);
			outStream.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Runs the server thread responsible for receiving and dealing with a client's requests
	 * @since 1.0
	 */



	@SuppressWarnings("unchecked")
	public void run(){

		try {

			List<Object> list = (ArrayList<Object>) inStream.readObject();
			if(((String)list.get(0)).equals("login")) {
				String userID = (String) list.get(1);
				String password = (String) list.get(2);

				LoginUserHandler loginUserHandler = new LoginUserHandler(userID, password);

				//a flag que diz se o utilizador eh novo ou nao
				Boolean flagNewUser = loginUserHandler.loginGetFlag();
				send(flagNewUser);

				//o que o cliente envia que o servidor vai receber:
				boolean success = false;
				if(flagNewUser.booleanValue()) {
					//se a flag for true, ou seja se o utilizador ainda naho existe no sistema
					success = loginUserHandler.register(userID,password);
					System.out.println("Sou um utilizador novo");
				}else {
					//se a flag for false ou seja se o utilizador jah existe no sistema
					success = loginUserHandler.login(userID,password);

					System.out.println("Sou um utilizador que já existe");
				}

				send(success);
				if(success) 
					currentUser = UserCatalog.getInstance().getUser(userID);
			} //login close

			while(true) {

				try {
					String function = null;
					List<Object> params = null;


					//call function
					List<Object> objs = (ArrayList<Object>) inStream.readObject();
					function = ((String) objs.get(0)).toLowerCase();
					params = objs.subList(1, objs.size());

					if(currentUser != null)
						params.add(currentUser);



					if(function.equals("follow")) {
						send(RequestHandler.follow((String) params.get(0), currentUser.getUsername()));
					}
					
					if(function.equals("unfollow")) {
						send(RequestHandler.unfollow((String) params.get(0), currentUser.getUsername()));
					}
					
					if(function.equals("viewfollowers")) {
						send(RequestHandler.viewFollowers(currentUser.getUsername()));
					}

					if(function.equals("newgroup")) {
						send(RequestHandler.create((String) params.get(0), currentUser));
					}
					

				}catch (ClassNotFoundException | IllegalArgumentException e) {
					e.printStackTrace();
					break;
				}catch(SocketException | EOFException e) {
					System.out.println("The client disconnected from server");
					break;
				} catch (UserCouldNotCreateGroupException e) {
					e.printStackTrace();
				}
			}
			outStream.close();
			inStream.close();
			socket.close();
		} //try close
		catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (UserCouldNotLoginException e) {
			e.printStackTrace();
		}

	}
}






