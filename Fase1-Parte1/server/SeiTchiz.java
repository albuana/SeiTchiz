package server;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * 
 * Class that initiates the server from the parameters it receives 
 */
public class SeiTchiz {
	
	public static void main(String[] args) throws NumberFormatException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException{
		System.out.println("servidor: main");
		Server server = Server.create(Integer.parseInt(args[0]));
//		server.startServer();
	}

}
