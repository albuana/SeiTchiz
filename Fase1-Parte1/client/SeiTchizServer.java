package server;

import java.io.IOException;

/**
 * 
 * Class that initiates the server from the parameters it receives 
 */
public class SeiTchizServer {
	
	public static void main(String[] args) throws NumberFormatException, IOException{
		System.out.println("servidor: main");
		Server server = Server.create(45678);
		server.startServer();
	}

}
