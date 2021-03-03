package client;


import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import client.exceptions.CommandNotExistException;
import client.exceptions.UserCouldNotSendException;
import client.handler.LoginUserHandler;
import client.handler.RequestHandler;

/**
 *Client main , receives command line parameters and starts the client as well as handling the function parameter distinction
 */
public class SeiTchiz {

	private static final List<String> AVAILABLE_METHODS 
	= Arrays.asList("follow","unfollow","viewfollowers","post",
			"wall","like","newgroup","addu","removeu","ginfo",
			"msg","collect","history","menu","quit", "f", "u", "v",
			"p", "w", "l", "n", "a", "r", "g", "m", "c", "h", "m", "q");


	/**
	 * 
	 * @param args -command line parameters
	 * @throws UserCouldNotSendException 
	 * @throws IOException 
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) throws UserCouldNotSendException, IOException  {

		Scanner sc = new Scanner(System.in);

		if(args.length==0) {
			System.out.print("\nExample: SeiTchiz <IP>[:porto] <clientID> [password]"); 
			sc.close();
			System.out.println("\n");
			return;
		}
		
		String serverAddress = args[0];
		String clientID = args[1];
		String password;
		
		System.out.println("\n**********  Server connection  **********\n");
		
		//CONEXAHO
		try {
            Client.connect(serverAddress,clientID);
            System.out.println("Sucessfully connection with server.");
        } catch(java.net.ConnectException e) {

            try {
                throw new UserCouldNotSendException("");
            } catch(UserCouldNotSendException e1){
                System.out.println(e1.getMessage());
                sc.close();
                return;
            }
        }
		
		System.out.println("\n**********  Welcome to SeiTchiz  **********\n");
		
		if(args.length==2) {
			System.out.print("Password's missing.\nPlease type here: ");
			password = sc.nextLine();
			System.out.println("\n");
		}
		else {
			password = args[2];
		}
		
		LoginUserHandler login = new LoginUserHandler(clientID, password);
		
		//LOGIN
		Object r = login.login();
		if(r instanceof String) {
			System.out.println((String)r);
			sc.close();
			Client.getInstance().close();
			return;
		}

		//Se nao se conseguir fazer login com sucesso.
        if(!r.getClass().isInstance(true)) {
            System.out.println("\n\t ** RESPONSE **");
            System.out.println(r + "\n");
            sc.close();
            Client.getInstance().close();
            return;
        }


		//MOSTRAR FUNCIONALIDADES
		showMenu();

		while (true) {
			//LER FUNCIONALIDADE ESCOLHIDA
			String[] parameters = sc.nextLine().split(" ");
			String function = parameters[0];
			try{
				//CASO A FUNCIONALIDADE ESCRITA MAL
				
				if (parameters[0].equals("quit") || parameters[0].equals("q"))
					break;
				else if(parameters[0].equals("menu")) {
					showMenu();
					continue;
				}

				if(!AVAILABLE_METHODS.contains(parameters[0]))
					System.out.println(new CommandNotExistException().getMessage());
				else if((parameters[0].equals("msg")) || (parameters[0].equals("m"))) {
                    String[] aux = new String[3];
                    aux[0] = parameters[0];
                    aux[1] = parameters[1];
                    aux[2] = String.join(" ",Arrays.copyOfRange(parameters, 2, parameters.length));
                    parameters = aux;
                    System.out.println("\t ** RESPONSE **");
                    System.out.println(RequestHandler.msg(parameters[1],parameters[2]));
                }else if(parameters[0].equals("f")) {
					System.out.println("\t ****** RESPONSE ******");
					System.out.println(RequestHandler.follow(parameters[1]));
				}else if(parameters[0].equals("u")) {
					System.out.println("\t ****** RESPONSE ******");
					System.out.println(RequestHandler.unfollow(parameters[1]));
				}else if(parameters[0].equals("v")) {
					System.out.println("\t ****** RESPONSE ******");
					System.out.println(RequestHandler.viewfollowers());
				}else if(parameters[0].equals("p")) {
					System.out.println("\t ****** RESPONSE ******");
					System.out.println(RequestHandler.post(parameters[1]));
				}else if(parameters[0].equals("l")) {
					System.out.println("\t ****** RESPONSE ******");
					System.out.println(RequestHandler.like(parameters[1]));
				}else if(parameters[0].equals("n")) {
					System.out.println("\t ****** RESPONSE ******");
					System.out.println(RequestHandler.newgroup(parameters[1]));
				}else if(parameters[0].equals("a")) {
					System.out.println("\t ****** RESPONSE ******");
					System.out.println(RequestHandler.addu(parameters[1],parameters[2]));
				}else if(parameters[0].equals("r")) {
					System.out.println("\t ****** RESPONSE ******");
					System.out.println(RequestHandler.removeu(parameters[1],parameters[2]));
				}else if(parameters[0].equals("g")) {
					System.out.println("\t ****** RESPONSE ******");
					if(parameters.length == 2) System.out.println(RequestHandler.ginfo(parameters[1]));
					else System.out.println(RequestHandler.ginfo());
				}else if(parameters[0].equals("c")) {
					System.out.println("\t ****** RESPONSE ******");
					System.out.println(RequestHandler.collect(parameters[1]));
				}else if(parameters[0].equals("h")) {
					System.out.println("\t ****** RESPONSE ******");
					System.out.println(RequestHandler.history(parameters[1]));
				}else if(parameters[0].equals("w")) {
					System.out.println("\t ****** RESPONSE ******");
					System.out.println(RequestHandler.wall(parameters[1]));
				}else {

					String[] params = new String[parameters.length - 1];		
					Class<?>[] c = new Class[parameters.length - 1];

//					System.out.println(function);
					for (int i = 0; i < c.length; i++) {
						c[i] =  parameters[i + 1].getClass();
						params[i] = parameters[i + 1];
					}

//					System.out.println("Method: " + function);
					Method m = RequestHandler.class.getMethod(function, c);
					Object result = m.invoke(null , params);
					System.out.println("\t ****** RESPONSE ******");
					System.out.println(result + "\n");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}


		}

		System.out.print("Bye, see you soon!");
		sc.close();
		Client.getInstance().close();


	}

	/**
	 * Prints a menu related to the handling of the program
	 */
	private static void showMenu() {
		System.out.print("Application Menu :\n"+
				"------------------> follow <userID>\n" + 
				"------------------> unfollow <userID>\n" +
				"------------------> viewfollowers\n" +
				"------------------> post <photo>\n" +
				"------------------> wall <nPhotos>\n" +
				"------------------> like <photoID>\n" +
				"------------------> newgroup <groupID>\n" +
				"------------------> addu <userID> <groupID>\n" +
				"------------------> removeu <userID> <groupID>\n" +
				"------------------> ginfo <groupID>\n" +
				"------------------> msg <groupID> <msg>\n" +
				"------------------> collect <groupID>\n" +
				"------------------> history <groupID>\n"+
				"------------------> menu\n" +
				"------------------> quit\n");
	}
}
