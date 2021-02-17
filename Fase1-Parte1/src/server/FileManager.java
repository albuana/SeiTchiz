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
import java.util.ArrayList;
import java.util.List;

public class FileManager {
	private String fileName;
	private File file; 

	/**
	 * Creates a directory based on name given
	 * @param name pathname to give directory
	 */
	public static void createDirectory(String name) {
		new File(name).mkdir();
	}

	/**
	 * FileManager constructor
	 * @param file file to start managing
	 */
	public FileManager(String file) {
		this.fileName = file;
		this.file = new File(fileName);
	}

	/**
	 * Load content from a file
	 * @return ArrayList of file content
	 */
	public List<String> loadContent () throws ClassNotFoundException, IOException {
		synchronized(file) {
			List<String> everything = loadObjects();

			return everything;
		}
	}


	/**
	 * Writes content to file
	 * @param content data to be written
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public void writeContent (String content) throws IOException, ClassNotFoundException{
		synchronized(file) {
			String text = content;
			this.writeObjects(text);
		}
	}

	/**
	 * write objects to file
	 * @param <E> type of objects
	 * @param objs objects
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
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

			writeBytes(bytes);
		}
	}

	@SuppressWarnings("unchecked")
	/**
	 * loads objects from file
	 * @return list of objects
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public <E> List<E> loadObjects() throws IOException, ClassNotFoundException{
		synchronized(file) {
			byte[] bytes = loadBytes();

			if(bytes.length == 0)
				return new ArrayList<E>();
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			List<E> result = (List<E>)ois.readObject();

			return result;
		}
	}

	/**
	 * Calls write content method to write a list of data
	 * @param contentUser Array of data to be written 
	 * @throws ClassNotFoundException 
	 * @throws IOException
	 */
	public void writeList (List<String> contentUser) throws ClassNotFoundException, IOException{
		for(String s: contentUser)
			writeContent(s);
	}

	/**
	 * Removes all data from file
	 * @throws IOException
	 */
	public void removeAll() throws IOException {
		synchronized(file) {
			PrintWriter writer = new PrintWriter(file);	
			writer.print("");
			writer.close();
		}
	}

	/**
	 *
	 * @param bytes the data to be written 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void writeBytes(byte[] bytes) throws FileNotFoundException, IOException {
		synchronized(file) {
			FileOutputStream fos = new FileOutputStream(file,true);

			fos.write(bytes);
			fos.flush();
			fos.close();
		}
	}

	/**
	 * Loads into an array of bytes the data in a file
	 * @return array of bytes
	 * @throws IOException
	 */
	public byte[] loadBytes() throws IOException {
		synchronized(file) {
			FileInputStream fis = new FileInputStream(file);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int b;
			byte[] d = new byte[16];
			while((b = fis.read(d)) != -1) {
				bos.write(d, 0, b);
			}
			
			bos.flush();
			byte[] res = bos.toByteArray();
			bos.close();
			fis.close();
			return res;
		}
	}

	/**
	 * Removes a file
	 */
	public void removeFile() {
		file.delete();
	}


}
