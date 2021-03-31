package server;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;

public class FileManager {

	private String fileName; 
	private String filePath; 

	private volatile File file;
	private static Cipher encryptCipher;
	private static Cipher decryptCipher;
	private static Key key;
	
	

	public FileManager(String file) {
		this.fileName = file;
		this.file = new File(fileName);
	}

	/**
	 * 
	 * @param filePath
	 * @param fileName
	 */
	public FileManager(String filePath, String fileName) {
		this.filePath = filePath;
		this.fileName = filePath + "/" + fileName;
		createDirectory();
		createFile();

	}
	/**
	 * 
	 */
	private void createDirectory() {
		try {
			Path path = Paths.get(filePath);

			Files.createDirectories(path);
 
		} catch (IOException e) {
			e.printStackTrace();
		}

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

	/**
	 * 
	 * @param str
	 * @throws IOException
	 */
	public void writeFile(String str) throws IOException {
		Files.write(Paths.get(fileName), str.getBytes(), StandardOpenOption.APPEND);
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public ArrayList<String> fileToList() throws IOException {
		BufferedReader read = new BufferedReader( new FileReader (fileName));
		ArrayList<String> ret=new ArrayList<String>();
		String line;
		while((line=read.readLine())!=null) {
			ret.add(line);
		}
		read.close();
		return ret;
	}
	/**
	 * 
	 * @param lineToRemove
	 */
	public void removeFromFile(String lineToRemove) {

		try {

			File inFile = new File(fileName);

			if (!inFile.isFile()) {
				System.out.println("Parameter is not an existing file");
				return;
			}

			//Construct the new file that will later be renamed to the original filename.
			File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

			BufferedReader br = new BufferedReader(new FileReader(fileName));
			PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

			String line = null;

			//Read from the original file and write to the new
			//unless content matches data to be removed.
			while ((line = br.readLine()) != null) {

				if (!line.trim().equals(lineToRemove)) {

					pw.println(line);
					pw.flush();
				}
			}
			pw.close();
			br.close();

			//Delete the original file
			if (!inFile.delete()) {
				System.out.println("Could not delete file");
				return;
			}

			//Rename the new file to the filename the original file had.
			if (!tempFile.renameTo(inFile))
				System.out.println("Could not rename file");

		}
		catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 
	 * @param line
	 * @param toRemove
	 */
	public void collectRemoveViewer(String line, String toRemove) {
		try {

			File inFile = new File(fileName);

			if (!inFile.isFile()) {
				System.out.println("Parameter is not an existing file");
				return;
			}

			//Construct the new file that will later be renamed to the original filename.
			File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

			BufferedReader br = new BufferedReader(new FileReader(fileName));
			PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

			String currentLine = null;

			//Read from the original file and write to the new
			//unless content matches data to be removed.

			while ((currentLine = br.readLine()) != null) {

				if (!currentLine.trim().equals(line)) {
					pw.println(currentLine);
					pw.flush();
				}
				else {
					//Cria uma linha que come�a com sender:msg
					String[] split=line.split(":");
					StringBuilder newLine=new StringBuilder();
					newLine.append(split[0]+":"+split[1]);
					for(int i=2;i<split.length;i++) {
						//se o membro dos viewers � igual ao toRemove ele nao faz nada
						//se for diferente ele escreve na nova linha esse viewer
						if(!split[i].equals(toRemove)) {
							newLine.append(":"+split[i]);
						}
					}
					pw.println(newLine);
					pw.flush();
				}
			}
			pw.close();
			br.close();

			//Delete the original file
			if (!inFile.delete()) {
				System.out.println("Could not delete file");
				return;
			}

			//Rename the new file to the filename the original file had.
			if (!tempFile.renameTo(inFile))
				System.out.println("Could not rename file");

		}
		catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public String getFilename() {
		return fileName;
	}

	/**
	 * 
	 * @param line
	 * @param newViewer
	 */
	public void historyAddViewer(String line, String newViewer) {
		try {

			File inFile = new File(fileName);

			if (!inFile.isFile()) {
				System.out.println("Parameter is not an existing file");
				return;
			}

			//Construct the new file that will later be renamed to the original filename.
			File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

			BufferedReader br = new BufferedReader(new FileReader(fileName));
			PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

			String currentLine = null;

			//Read from the original file and write to the new
			//unless content matches data to be removed.
			while ((currentLine = br.readLine()) != null) {
				//Compara o sender:msg de cada currentLine com o line
				String[] split=currentLine.split(":");
				String compareCurrent=split[0]+":"+split[1];
				split=line.split(":");
				String compareLine=split[0]+":"+split[1];
				//Se forem diferentes ele escreve a currentLine
				if (!compareCurrent.equals(compareLine)) {
					pw.println(currentLine);
					pw.flush();
				}
				else {
					//se forem iguais ele escreve uma nova linha ao qual adiciona ao line o newViewer
					StringBuilder newLine=new StringBuilder();
					newLine.append(line+":"+newViewer);
					pw.println(newLine);
					pw.flush();
				}
			}
			pw.close();
			br.close();


			//Delete the original file
			if (!inFile.delete()) {
				System.out.println("Could not delete file");
				return;
			}

			//Rename the new file to the filename the original file had.
			if (!tempFile.renameTo(inFile))
				System.out.println("Could not rename file");

		}
		catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}

	}


	public void deleteFile() {
		File inFile = new File(fileName);
		if (!inFile.delete()) {
			System.out.println("Could not delete file");
			return;
		}
	}

	public List<String> loadContent () throws ClassNotFoundException, IOException {
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