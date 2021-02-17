package server.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager {
	public String filename;
	public FileManager(String name) {
		this.filename=name;
		createFile();
	}
	
	private void createFile(){
		File file = new File(filename);

		if(!file.exists())
			try {
				file.createNewFile();

			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void writeFileUser(String userID2, String password) throws IOException {
	    String str = "\n"+userID2+ " " + password;
	    FileOutputStream outputStream = new FileOutputStream(filename);
	    byte[] strToBytes = str.getBytes();
	    outputStream.write(strToBytes);
	    outputStream.close();
	}
}