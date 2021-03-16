package client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import client.exceptions.UserCouldNotSendException;

/**
 * Handles Client-Server connectivity as well as transmitting and receiving of information
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public final class Client {

	private Socket socket = null;
	private static Client INSTANCE;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String userID;
	private String password;
	private String serverAddress;

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
	 * @param userID
	 * @throws IOException 
	 */
	private Client(String serverAddress, String userID) throws IOException {
		String[] infoList = serverAddress.split(":", 2);

		String ipServer = infoList[0];
		int portServer;

		if(infoList.length == 2)
			portServer = Integer.parseInt(infoList[1]);
		else
			portServer = 45678;
		
		socket = new Socket(ipServer, portServer);
//		System.out.println("\nThe client are connected with the server.\n"); 
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		this.userID =userID;
	}

	/**
	 * Returns the Client singleton instance after creating the Client and connecting to the server
	 * @param userID 
	 * @param server address serverAddress
	 * @return Client singleton
	 * @throws IOException 
	 */
	public static Client connect(String serverAddress, String userID) throws IOException {
		INSTANCE = new Client(serverAddress, userID);
		return INSTANCE;
	}


	/**
	 * Sends data
	 * @param objs-data to be sent, such as function name, parameters of the function as well as data relevant to such function
	 * @throws UserCouldNotSendException
	 */
	public void send(Object... objs) throws UserCouldNotSendException{
		try {
			ArrayList<Object> list = new ArrayList<Object>();
			for(int i = 0; i < objs.length; i++)
				list.add(objs[i]);

			out.writeObject(list);
			out.flush();
		} catch (IOException e) {
			throw new UserCouldNotSendException(e.getMessage());

		}
	}
	
	public void sendImage(File file) throws UserCouldNotSendException{
        try {

            String extensao = file.getName().substring(file.getName().lastIndexOf('.') + 1);
            BufferedImage bImage = ImageIO.read(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, extensao, bos );
            byte [] data = bos.toByteArray();

            send("post", data, file.getName());

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
		return this.userID;
	}

	public String getServerAddress() {
		return this.serverAddress;
	}

	public boolean hasPassword(String password) {
		return this.password.equals(password);
	}
}