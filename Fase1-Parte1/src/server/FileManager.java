package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class FileManager {
	
	private String fileName;
	private String filePath;
	
	
	public FileManager(String filePath, String fileName) {
		this.filePath = filePath;
		this.fileName = filePath + "/" + fileName;
		createDirectory();
		createFile();
		
	}
	
	private void createDirectory() {
		try {
			Path path = Paths.get(filePath);

			Files.createDirectories(path);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

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
	public void removeFromFile(String str) throws IOException {
		File newFile=new File("aux.txt");
		File currentFile = new File(fileName);
		BufferedReader read = new BufferedReader( new FileReader (fileName));
		BufferedWriter writer = new BufferedWriter( new FileWriter (newFile.getPath()));
		String line;
		while((line=read.readLine())!=null) {
			String trimmedLine = line.trim();
			if(trimmedLine.equals(line)) {
	            writer.write(line + System.getProperty("line.separator"));
			}
		}
        writer.close(); 
        read.close(); 
        newFile.renameTo(currentFile);			
	}
	

}
