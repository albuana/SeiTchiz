package server;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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
		
		int port;
        String keyStore;
        String password;

        try {
            port = Integer.parseInt(args[0]);
            keyStore = args[1];
            password = args[2];

        }catch(NumberFormatException e) {

            port = 45678;
            keyStore = args[0];
            password = args[1];
        }

        Server server = Server.create(port, keyStore, password);
		
		server.startServer();
	}
}
