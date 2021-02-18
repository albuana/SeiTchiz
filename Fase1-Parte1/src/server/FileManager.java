package server;

import java.io.File;

public class FileManager {
	
	private String fileName;
	private File file;
	
	public static void createDirectory(String name) {
		new File(name).mkdir();
	}
	
	public FileManager(String file) {
		this.fileName = file;
		this.file = new File(fileName);
	}

}
