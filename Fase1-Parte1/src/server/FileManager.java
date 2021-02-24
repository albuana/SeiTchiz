package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

	public void removeViewer(String username) {
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
	          String[] split=line.split(":");
	          for(int i=2;i<split.length;i++) {
	        	  if(split[i]!=username) {
	        		  if(i==split.length) {
		        		  pw.print(split[i]);	        			  
	        		  }
	        		  else {
		        		  pw.print(split[i]+":");	        			  
	        		  }
	        	  }
	            pw.println("");
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

}
