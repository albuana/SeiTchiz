package server;

import java.io.IOException;

/**
 * 
 * Class that initiates the server from the parameters it receives 
 */
public class SeiTchizServer {
	
	public static void main(String[] args){
//		Scanner sc=new Scanner(System.in);
//		System.out.println("servidor: main");
//		System.out.print("Porto: ");
//		int porto=Integer.parseInt(sc.nextLine());
//		Server server = Server.create(porto);
//		server.startServer();
		
		
		System.out.println("servidor: main");
		Server server;
		try {
			server = Server.create(Integer.parseInt(args[0]));
		} catch (NumberFormatException e) {
			System.out.println("Porto invalido");
			return;
		} catch (IOException e) {
			System.out.println("Porto invalido");
			return;
		}
		try {
			server.startServer();
		} catch (IOException e) {
			System.out.println("Erro a Iniciar o server");
		}
		
		try {
			server.destroy();
		} catch (IOException e) {
			System.out.println("Erro a Desligar o server");
		}
	}

}
