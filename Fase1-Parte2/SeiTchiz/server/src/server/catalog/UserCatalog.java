package server.catalog;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cipher.CipherHandler;
import server.domain.User;
import server.FileManager;

/**
 * Catalog knows all the information related to the users
 * @author Ana Albuquerque 53512, GonÃ§alo Antunes 52831, Tiago Cabrita 52741
 *
 */
public class UserCatalog {
	public static final String USERS_FILE_PATH = "Fase1-Parte2/SeiTchiz/server/Data/users/";
	public static final String CERTIFICATE_FILE_PATH = "Fase1-Parte2/SeiTchiz/server/Data/publicKeys/";
	private static UserCatalog INSTANCE;
	private static Map<String,User> userList;
	private static List<String> file; //dummy

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
        //Cria Path para publicKeys
		Path path = Paths.get(CERTIFICATE_FILE_PATH);
		Files.createDirectories(path);
		path = Paths.get(USERS_FILE_PATH);
		Files.createDirectories(path);
		file = new FileManager(USERS_FILE_PATH+"users.txt").loadContent();
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
        FileManager userTxt = new FileManager(USERS_FILE_PATH+"users.txt");
		List<String> list=userTxt.loadContent();
        for(int i=0;i<list.size();i++) {
            String[] userAndPass=list.get(i).split(",");
            String user = userAndPass[0];
            String certFileName = userAndPass[1];
            PublicKey pubKey = CipherHandler.getCertificateFromPath(CERTIFICATE_FILE_PATH+certFileName).getPublicKey();
            userList.put(user,new User(user,pubKey));
        }
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
	public void addUser(User user, Certificate userCert) throws IOException, CertificateEncodingException, ClassNotFoundException {
        String str = user.getUsername() + "," + user.getUsername() + ".cer";
        FileManager userTxt = new FileManager(USERS_FILE_PATH+"users.txt");
        userTxt.writeContent(str);
        userList.put(user.getUsername(), user);
        File file=new File(CERTIFICATE_FILE_PATH+user.getUsername()+".cer");
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buf = userCert.getEncoded();
        fos.write(buf);
        fos.close();
 
    }

	
}
