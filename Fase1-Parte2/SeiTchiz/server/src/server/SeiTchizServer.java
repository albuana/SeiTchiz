package server;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * Class that initiates the server from the parameters it receives 
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 *
 */
public class SeiTchizServer {

	private static int port;

	public static void main(String[] args) throws NumberFormatException, IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		System.out.println("\nserver: main");

		//		if(args.length == 0){
		//			port = 45678;
		//			Server server = Server.create(port, args[1],args[2]);
		//			server.startServer();
		//
		//		}else{
		//			System.out.println("porto " +  Integer.parseInt(args[0]));
		//			System.out.println("keystore " +  args[1]);
		//			System.out.println("keystore password " +  args[2]);
		//			System.out.println("aaa " +Server.create(Integer.parseInt(args[0]), args[1],args[2]));

//		int p = Integer.parseInt(args[0]);
//		String kspath = args[1]; 
//		String kspass = args[2];

		System.out.println("\nVamos saber os args colocados (soh para teste)");
		
		System.out.println(Integer.parseInt(args[0]) +"; " + args[1] + "; " +  args[2]);

		Server server = Server.create(Integer.parseInt(args[0]) , args[1], args[2]);
		
		System.out.println("\nSe este print aparecer significa que o ficheiro keystore.server foi lido!");
		
//		System.out.println(p + kspath + kspass);
		
		server.startServer();
		//		}
	}
}
