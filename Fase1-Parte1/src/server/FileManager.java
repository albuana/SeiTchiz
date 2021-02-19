package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class FileManager {
	
	private String fileName;
	
	
	public FileManager(String file) {
		this.fileName = file;
		createFile();
	}
	
	public void createFile(){
		File file = new File(fileName);

		if(!file.exists())
			try {
				file.createNewFile();

			} catch (IOException e) {
				e.printStackTrace();
			}
	}


	public void writeFile(String str) throws IOException {
	    Files.write(Paths.get(fileName), str.getBytes(), StandardOpenOption.APPEND);
	}
	
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
	public void removeFromFile(String str) {
		
	}
	

}
