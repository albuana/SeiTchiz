package server;

import java.io.IOException;

/**
 * 
 * Class that initiates the server from the parameters it receives 
 */
public class SeiTchiz {
	
	public static void main(String[] args) throws NumberFormatException, IOException{
		System.out.println("servidor: main");
		Server server = Server.create(1000);
		server.startServer();
	}

}