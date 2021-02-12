package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import client.exceptions.UserCouldNotSendException;

public final class Client {

	private static Socket socket;
	private static Client INSTANCE = null;
	private static ObjectOutputStream out;
	private static ObjectInputStream in;
	private static String userID;
	private static String password;
	private static String serverAddress;


	/**
	 * Returns the Client singleton instance
	 * @return Client singleton
	 */
	public static Client getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Client constructor
	 * @param server address serverAddress
	 * @param user
	 */
	private Client(String serverAddress, String userID) {
		String[] infoList = serverAddress.split(":", 2);

		String ipServer = infoList[0];
		int portServer;
		
		if(infoList.length == 2)
			portServer = Integer.parseInt(infoList[1]);
		else
			portServer = 45678;
		
		try {
			Client.socket = new Socket(ipServer, portServer);
			Client.in = new ObjectInputStream(Client.socket.getInputStream());
			Client.out = new ObjectOutputStream(Client.socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}
	
	/**
	 * Returns the Client singleton instance after creating the Client and connecting to the server
	 * @param userID 
	 * @param server address serverAddress
	 * @return Client singleton
	 */
	public static Client connect(String serverAddress, String userID) {
		INSTANCE = new Client(serverAddress, userID);
		return INSTANCE;
	}
	

	/**
	 * Sends data
	 * @param objs-data to be sent, such as function name, parameters of the function as well as data relevant to such function
	 * @throws UserCouldNotSendException
	 */
	public void send(Object[] objs){
		try {
			ArrayList<Object> list = new ArrayList<Object>();
			for(int i = 0; i < objs.length; i++)
				list.add(objs[i]);

			out.writeObject(list);
			out.flush();
		} catch (IOException e) {
				e.printStackTrace();
			
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
			Client.out.close();
			Client.in.close();
			Client.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return UserID
	 */
	public String getUserID() {
		return Client.userID;
	}
	
	public String getServerAddress() {
		return Client.serverAddress;
	}
	
	public boolean hasPassword(String password) {
		return Client.password.equals(password);
	}

}
