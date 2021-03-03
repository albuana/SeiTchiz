package server;

import java.io.IOException;

/**
 * 
 * Class that initiates the server from the parameters it receives 
 */
public class SeiTchizServer {
	
	private static int port;

	public static void main(String[] args) throws NumberFormatException, IOException{
		System.out.println("\nserver: main\n");

		if(args.length == 0){
			port = 45678;
			Server server = Server.create(port);
			server.startServer();

		}else{
			Server server = Server.create(Integer.parseInt(args[0]));
			server.startServer();
		}
	}
}
