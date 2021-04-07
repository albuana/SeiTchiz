package server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;

/**
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class FileManager {

	private String fileName; 
	private String filePath; 

	private volatile File file;
	private static Cipher encryptCipher;
	private static Cipher decryptCipher;
	private static Key key;
	
	static {
        try {
        	KeyStore keystore = Server.getInstance().getKeystore();
        	String keystorepass = Server.getInstance().getKeystorePassword();
        	key =  keystore.getKey("secKey", keystorepass.toCharArray());

            encryptCipher = Cipher.getInstance(key.getAlgorithm());
            decryptCipher = Cipher.getInstance(key.getAlgorithm());

            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | KeyStoreException | UnrecoverableKeyException e) {
            e.printStackTrace();
        }
    }

	public FileManager(String file) throws IOException {
		this.fileName = file;
		filePath=file;
		this.file = new File(fileName);
	}

	/**
	 * 
	 */
	public void createFile(){
		File file = new File(fileName);

		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public String getFilename() {
		return fileName;
	}

	

	public void deleteFile() {
		File inFile = new File(fileName);
		if (!inFile.delete()) {
			System.out.println("Could not delete file");
			return;
		}
	}

	public List<String> loadContent () throws ClassNotFoundException, IOException {
		if(!file.exists()) {
			List<String> everything=new ArrayList<String>();
			return everything;
		}
		synchronized(file) {
			List<String> everything = loadObjects();

			return everything;
		}
	}

	public <E> List<E> loadObjects() throws IOException, ClassNotFoundException{
		byte[] bytes = loadBytes();

		if(bytes.length == 0)
			return new ArrayList<E>();
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bis);
		List<E> result = (List<E>)ois.readObject();

		return result;
	}

	public byte[] loadBytes() throws IOException {
		FileInputStream fis = new FileInputStream(file);
		CipherInputStream cis = new CipherInputStream(fis,decryptCipher); 

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int b;
		byte[] d = new byte[16];
		while((b = cis.read(d)) != -1) {
			bos.write(d, 0, b);
		}

		bos.flush();
		byte[] res = bos.toByteArray();
		bos.close();
		cis.close();
		fis.close();
		return res;
	}
	
	public void writeContent (String content) throws IOException, ClassNotFoundException{
		synchronized(file) {
			String text = content;
			this.writeObjects(text);
		}
	}
	
	public <E> void writeObjects(E... objs) throws IOException, ClassNotFoundException{
		synchronized(file) {
			
			List<E> newList = new ArrayList<>();
			if(file.exists()) {
				newList = loadObjects();
				removeAll();				
			}
			
			for(E e: objs)
				newList.add(e);
			
			//get bytes
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			
			oos.writeObject(newList);
			oos.flush();
			oos.close();
			byte[] bytes = bos.toByteArray();
			bos.close();

			//encrypt the bytes and write them
			writeBytes(bytes);
		}
	}
	
	public void removeAll() throws IOException {
		synchronized(file) {
			PrintWriter writer = new PrintWriter(file);	
			writer.print("");
			writer.close();
		}
	}
	
	public void writeBytes(byte[] bytes) throws FileNotFoundException, IOException {
		synchronized(file) {
			FileOutputStream fos = new FileOutputStream(file,true);
			CipherOutputStream cos = new CipherOutputStream(fos,encryptCipher);

			cos.write(bytes);
			cos.flush();
			cos.close();
			fos.close();
		}
	}


}