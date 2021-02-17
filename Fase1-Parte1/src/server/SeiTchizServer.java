package server;

import java.io.IOException;
import java.util.Scanner;

/**
 * 
 * Class that initiates the server from the parameters it receives 
 */
public class SeiTchizServer {
	
	public static void main(String[] args) throws NumberFormatException, IOException{
//		Scanner sc=new Scanner(System.in);
//		System.out.println("servidor: main");
//		System.out.print("Porto: ");
//		int porto=Integer.parseInt(sc.nextLine());
//		Server server = Server.create(porto);
//		server.startServer();
		
		
		System.out.println("servidor: main");
		Server server = Server.create(Integer.parseInt(args[0]));
		server.startServer();
		server.destroy();
	}

}
