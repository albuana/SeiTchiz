package server.domain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

import server.Server;

public class Post {
	
	//Post ID counter
	private static Post INSTANCE = null;
	private static final String POST_DIRECTORY = Server.DATA_PATH+"posts/";
	
	/**
	 * Returns instance
	 * @return
	 */
	public static Post getInstance() {
		if(INSTANCE == null)
			return new Post();
		return INSTANCE;
	}
	
	/**
	 * Constructor
	 */
	private Post() {

	}
	
	/**
	 * Create a Post and keep it into the folder of the user, if the folser does not exist this method will create it
	 * @param file
	 * @param userID
	 * @return
	 * @throws IOException
	 */
	public String createPost(File file, String userID) throws IOException {
		File dir = new File(POST_DIRECTORY);
		File[] directoryListing = dir.listFiles();


		for(File userFile: directoryListing) {
			//Cheks if the user has any post
			if(userFile.getName().equals(userID)) {
				
				//Creates .txt file with info about the post
				File postFile = createFile(file, userID);
				if(postFile.exists())
					return "O Post já existe";
				
				postFile.createNewFile();
				
				//Creates Post
				createPostFile(file, userID);
				
				//Write on file the id and likes
				writeFile(postFile);

				return "Post criado";
			}
		}

		//Creates directories
		//Creates post
		createDirectories(userID);
		
		//Creates .txt file with info about the post
		File postFile = createFile(file, userID);
		if(postFile.exists())
			return "O Post já existe";
		
		postFile.createNewFile();
		
		//Creates Post
		createPostFile(file, userID);
		
		//Write on file the id and likes
		writeFile(postFile);

		return "Post criado";

	}


	
	
	
	/**
	 * Create Post File
	 * @param file
	 * @param userID
	 * @throws IOException
	 */
	private void createPostFile(File file, String userID) throws IOException {
		File newPost = new File(POST_DIRECTORY+userID+"/"+file.getName());
		newPost.createNewFile();
	}

	/**
	 * Create File
	 * @param file
	 * @param userID
	 * @return
	 * @throws IOException
	 */
	private File createFile(File file, String userID) throws IOException {
		String fileNameWithOutExt = file.getName().replaceFirst("[.][^.]+$", "");
		File newPostFileTXT = new File(POST_DIRECTORY+userID+"/"+fileNameWithOutExt+".txt");
		
		return newPostFileTXT; 
	}

	/**
	 * Write on file the ID and likes starting from 0
	 * @param file
	 * @throws IOException
	 */
	private void writeFile(File file) throws IOException {
		Files.write(file.toPath(), ("ID:"+nextID()+"\n"+"Likes:0").getBytes(), StandardOpenOption.APPEND);
	}

	/**
	 * Give post next ID
	 * @return
	 * @throws IOException 
	 */
	private int nextID() throws IOException {
		File nextFile = new File(POST_DIRECTORY+"nextID.txt");
		Scanner sc = new Scanner(nextFile);
		
		int previousID =Integer.parseInt(sc.nextLine());
		
		
		FileWriter fw = new FileWriter(nextFile,false);
		Files.write(nextFile.toPath(), String.valueOf((previousID+1)).getBytes(), StandardOpenOption.APPEND);
		
		sc.close();
		return previousID+1;
	}

	/**
	 * Create user Directories
	 * @param userID
	 * @throws IOException
	 */
	private void createDirectories(String userID) throws IOException {
		Path path = Paths.get("./Data/posts/"+userID);
		Files.createDirectory(path);
	}

}
