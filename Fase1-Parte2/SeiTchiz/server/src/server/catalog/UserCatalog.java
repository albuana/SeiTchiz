package server.catalog;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import server.Server;
import server.domain.User;
import server.FileManager;

/**
 * Catalog knows all the information related to the users
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 *
 */
public class UserCatalog {
	public static final String USERS_FILE_PATH = Server.DATA_PATH + "users/";
	public static final String CERTIFICATE_FILE_PATH = Server.DATA_PATH + "publicKeys/";
	private static UserCatalog INSTANCE;
	private static Map<String,User> userList;
	private static FileManager file;

	/**
	 * @return the user catalog singleton
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws CertificateException 
	 */
	public static UserCatalog getInstance() throws IOException, ClassNotFoundException, CertificateException {
		if(INSTANCE == null)
			INSTANCE = new UserCatalog();
		return INSTANCE;
	}

	/**
	 * Creates the user's catalog instance
	 * Inicializa the user.txt file
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws CertificateException 
	 */
	private UserCatalog () throws IOException, ClassNotFoundException, CertificateException {
		userList = new HashMap<>();
		file = new FileManager(USERS_FILE_PATH,"users.txt");
		initiateUserList();
	}

	/**
	 * Initialize the users list
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
//	private void initiateUserList() throws IOException, ClassNotFoundException {
//		ArrayList<String> list=file.fileToList();
//		for(int i=0;i<list.size();i++) {
//			String[] userAndPass=list.get(i).split(",");
//			String user = userAndPass[0];
//			String certPath = userAndPass[1];
//			Certificate certClient = (Certificate) new FileManager(CERTIFICATE_FILE_PATH + certPath).loadObjects();
//			System.out.println("certClient: " + certClient);
//			PublicKey pubKey = certClient.getPublicKey();
//			userList.put(user,new User(user,pubKey));
//		}
//		//TODO
//	}
	
	
	
	private void initiateUserList() throws IOException, ClassNotFoundException, CertificateException {
        ArrayList<String> list=file.fileToList();
        for(int i=0;i<list.size();i++) {
            String[] userAndPass=list.get(i).split(",");
            String user = userAndPass[0];
            String certPath = userAndPass[1];
            FileInputStream fis = new FileInputStream(CERTIFICATE_FILE_PATH+user+".cer");
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            Certificate cert = cf.generateCertificate(fis);
            PublicKey pubKey = cert.getPublicKey();
            userList.put(user,new User(user,pubKey));
        }
        //TODO
    }

	/**
	 * @param username the user's username to search
	 * @return the user that has an username equal to the given username. If there is any it returns null
	 */
	public User getUser(String username) {
		return userList.get(username);
	}

	/**
	 * Add the name; username and the password of the user to the file and to the list
	 * @param user the user's username
	 * @param userCert the user's name
	 * @throws ClassNotFoundException 
	 * @throws IOException if an error occurs whilst writing in the user's database
	 */
	public void addUser(User user, Certificate userCert) throws IOException, CertificateEncodingException {
        String str = user.getUsername() + "," + user.getUsername() + ".cert" + "\n";
        file.writeFile(str);
        userList.put(user.getUsername(), user);
        Path path = Paths.get(CERTIFICATE_FILE_PATH);
        Files.createDirectories(path);
        File file=new File(CERTIFICATE_FILE_PATH+user.getUsername()+".cer");
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buf = userCert.getEncoded();
        fos.write(buf);
        fos.close();
 
    }
	
	
}
