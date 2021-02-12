package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

//Alterar pacote
public class Client {

	private static String ipServer;
	private static String password;
	private static String idClient;
	private static int portServer;


	public static void main(String[] args){

		/////////  Tratamento do Input  ////////////////////////////////////////////////////

		//String serverAdress = "127.0.0.1";
		//String[] infoList = serverAdress.split(":", 2);

		//ipServer = infoList[0];
		ipServer = "127.0.0.1";
		idClient = "123456";
		password = "pass";

		/*if(infoList.length == 2)
			portServer = Integer.parseInt(infoList[1]);
		else
			*/portServer = 45678;
		////////////////////////////////////////////////////////////////////////////////////

		///////////  Criar Socket e Streams, Enviar info para o Servidor //////////////////
		
		Socket clientSocket;
		ObjectInputStream in;
		ObjectOutputStream out;

		try {
			clientSocket = new Socket(ipServer, portServer);
			//in = new ObjectInputStream(clientSocket.getInputStream());
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			
			out.writeObject(ipServer);
			out.writeObject(portServer);
			out.writeObject(idClient);
			out.writeObject(password);
			
		} catch (UnknownHostException e) {
			System.out.println("Ocorreu um problema na ligaho");
		} catch (IOException e) {
			System.out.println("Ocorreu um problema na ligaho");
		}

		/////////////////////////////////////////////////////////////////////////////////////


		//Nao esquecer de fechar o socket e as streams
	}

}
