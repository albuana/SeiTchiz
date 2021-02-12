package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import client.exceptions.UserCouldNotSendException;

public final class Client {

	private Socket socket;
	private static Client INSTANCE;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String user;

	/**
	 * Returns the Client singleton instance
	 * @return Client singleton
	 */
	public static Client getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Client constructor
	 * @param server address serveraddress
	 * @param user
	 */
	private Client(String serveraddress, String user) {
		//TODO
	}

	/**
	 * Returns the Client singleton instance after creating the Client
	 * @param userID 
	 * @param server address serveraddress
	 * @return Client singleton
	 */
	public static Client connect(String serveraddress, String userID) {
		INSTANCE = new Client(serveraddress, userID);
		return INSTANCE;
	}

	/**
	 * Sends data
	 * @param objs-data to be sent, such as function name, parameters of the function as well as data relevant to such function
	 * @throws UserCouldNotSendException
	 */
	public void send(Object... objs) throws UserCouldNotSendException{
		try {
			List<Object> list = new ArrayList<>();
			for(int i = 0; i < objs.length; i++)
				list.add(objs[i]);

			out.writeObject(list);
			out.flush();
		} catch (IOException e) {
			throw new UserCouldNotSendException(e.getMessage());
		}
	}

	/**
	 * Receives objects as a response from the server
	 * @return response from server
	 */
	public Object receive() {
		try {
			return in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} 
		return null;
	}

	/**
	 * closes socket connection and open streams
	 */
	public void close(){
		try {
			this.out.close();
			this.in.close();
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return UserID
	 */
	public String getUserID() {
		return user;
	}


}
