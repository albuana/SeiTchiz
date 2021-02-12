package client;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import server.RequestHandler;
import client.exceptions.CommandNotExistException;
import client.exceptions.UserCouldNotSendException;
import client.handler.LoginUserHandler;

/**
 *Client main , receives command line parameters and starts the client as well as handling the function parameter distinction
 *@since 1.0 
 */
public class ClientMain {

	private static final List<String> AVAILABLE_METHODS 
	= Arrays.asList("follow","unfollow","viewfollowers","post",
			"wall","like","newgroup","addu","removeu","ginfo",
			"msg","collect","history","menu","quit"); 
	
	/**
	 * 
	 * @param args -command line parameters
	 * @throws UserCouldNotException
	 */
	public static void main(String[] args) throws UserCouldNotSendException {

		Scanner sc = new Scanner(System.in);
		if(args.length == 0 || args.length < 5) {
			System.out.println("Exemplo de utlizacao : SeiTchiz <serverAddress> <clientID>"); 
			sc.close();
			return;
		}

		String serverAddress = args[0];
		String clientID = args[1];

		System.out.println("**********  Bem vindos ao SeiTchiz  **********");

		//CONEXAHO
		try {
			Client.connect(serverAddress,clientID);
		} catch (IOException e) {
			e.printStackTrace();
		} 

		LoginUserHandler login = new LoginUserHandler(serverAddress, clientID);

		//LOGIN
		Object r;
		try {
			r = login.login();
			if(r instanceof String) {
				System.out.println((String)r);
				sc.close();
				Client.getInstance().close(); //implementar close
				return;
			}


		} catch (IOException e1) {
			e1.printStackTrace();
		}

		//MOSTRAR FUNCIONALIDADES
		showMenu();


		while (true) {
			//LER FUNCIONALIDADE ESCOLHIDA
			String[] parameters = sc.nextLine().split(" ");
			String function = parameters[0];
			try{
				//CASO A FUNCIONALIDADE ESCRITA MAL
				if(!AVAILABLE_METHODS.contains(parameters[0]))
					throw new CommandNotExistException();
				if (parameters[0].equals("quit"))
					break;
				else if(parameters[0].equals("menu")) {
					showMenu();
					continue;
				}

				if(parameters[0].equals("msg")) {
					String[] aux = new String[3];
					aux[0] = parameters[0];
					aux[1] = parameters[1];
					aux[2] = String.join(" ",Arrays.copyOfRange(parameters, 2, parameters.length));
					parameters = aux;
				}

				String[] params = new String[parameters.length - 1];		
				Class<?>[] c = new Class[parameters.length - 1];

				System.out.println(function);
				for (int i = 0; i < c.length; i++) {
					c[i] =  parameters[i + 1].getClass();
					params[i] = parameters[i + 1];
				}

				System.out.println("Method: " + function);
				Method m = RequestHandler.class.getMethod(function, c);

				Object result = m.invoke(null , params);
				System.out.println("\t ****** RESPOSTA ******");
				System.out.println(result);
			}catch(Exception e) {
				e.printStackTrace();
			}

		}


		System.out.print("Volte Sempre!");
		sc.close();
		Client.getInstance().close();

	}

	/**
	 * Prints a menu related to the handling of the program
	 */
	private static void showMenu() {
		System.out.print("\nMenu de Aplicacao :\n"+
				"------------------> follow <userID>\n" + 
				"------------------> unfollow <userID>\n" +
				"------------------> viewfollowers\n" +
				"------------------> post <photo>\n" +
				"------------------> wall <nPhotos>\n" +
				"------------------> like <photoID>\n" +
				"------------------> newgroup <groupID>\n" +
				"------------------> addu <userID> <groupID>\n" +
				"------------------> removeu <userID><groupID>\n" +
				"------------------> ginfo <groupID>\n" +
				"------------------> msg <groupID> <msg>\n" +
				"------------------> collect <groupID>\n" +
				"------------------> history <groupID>\n"+
				"------------------> menu\n" +
				"------------------> quit\n");
	}
}
