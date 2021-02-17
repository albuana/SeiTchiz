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
				}else {
					//se a flag for false ou seja se o utilizador jah existe no sistema
					success = loginUserHandler.login(userID,password);
				}

				send(success);
				if(success) 
					currentUser = UserCatalog.getInstance().getUser(userID);
				
					
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

					//if(currentUser != null)
					params.add(currentUser);


					Class<?>[] c = new Class[params.size()];

					System.out.println(function);
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
					send((new NotWellFormedException()).getMessage()); //mandar erro ao utilizador
				}catch(InvocationTargetException e) {
					send(e.getCause().getMessage()); //mandar erro ao utilizador
				}catch(SocketException | EOFException e) {
					System.out.println("The client disconnected from server");
					break;
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
		}

	}
}






