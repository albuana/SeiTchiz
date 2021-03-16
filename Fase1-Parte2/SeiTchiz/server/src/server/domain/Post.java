package server.domain;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import server.Server;
import server.exceptions.post.HaveNoPhotosExeption;
import server.exceptions.post.NoPostExeption;

/**
 * 
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 *
 */
public class Post {

	//Post ID counter
	private static Post INSTANCE = null;
	private static final String POST_DIRECTORY = Server.DATA_PATH+"posts/";

	/**
	 * Returns post instance
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
	public String createPost(byte[] buff, String fileName ,User userID) throws IOException {

		String extensao = fileName.substring(fileName.lastIndexOf('.') + 1);
		String fileNameWEx = fileName.replaceFirst("[.][^.]+$", "");


		File file = new File(POST_DIRECTORY+fileName);


		File dir = new File(POST_DIRECTORY);
		File[] directoryListing = dir.listFiles();


		for(File userFolder: directoryListing) {
			//Cheks if the user has any post
			if(userFolder.getName().equals(userID.getUsername())) {

				//Creates .txt file with info about the post
				File postFile = createFile(fileNameWEx ,userID);
				if(postFile.exists())
					return "The post already exist.";

				postFile.createNewFile();

				//Creates Post
				createPostFile(buff, extensao, fileName ,userID);

				//Write on file the id and likes
				writeFile(postFile, extensao);

				return "Post sent.";
			}
		}

		//Creates directories
		//Creates post
		createDirectories(userID);

		//Creates .txt file with info about the post
		File postFile = createFile(fileNameWEx, userID);
		if(postFile.exists())
			return "The post already exist.";

		postFile.createNewFile();

		//Creates Post
		createPostFile(buff, extensao, fileName ,userID);

		//Write on file the id and likes
		writeFile(postFile, extensao);

		return "Post sent.";

	}


	/**
	 * return a list with n post 
	 * @param n
	 * @param username
	 * @return
	 * @throws FileNotFoundException 
	 * @throws HaveNoPhotosExeption 
	 */
	public String wall(int n, User username) throws FileNotFoundException, HaveNoPhotosExeption {
		int count = n;
		StringBuilder ret = new StringBuilder();
		ArrayList<String> followedUsers = getFollowed(username.getUsername());

		if(followedUsers == null)
			return "O user nao segue ninguem";


		File dir = new File(POST_DIRECTORY);
		File[] directoryListing = dir.listFiles();

		//Iterate throught users post folders
		for(File userFolder: directoryListing) {
			//Checks if the folder name is in the follow list
			if(followedUsers.contains(userFolder.getName()) && count != 0) {

				File dir2 = new File(POST_DIRECTORY+"/"+userFolder.getName());
				File[] directoryListing2 = dir2.listFiles();

				//Give diferent treatment from images and .txt info
				for(File userPosts: directoryListing2) {


					String fileNameWEx =  userPosts.getName().replaceFirst("[.][^.]+$", "");

					//To see the likes and id 
					if(userPosts.getPath().substring(userPosts.getPath().lastIndexOf(".")).equals(".txt") && count != 0) {

						Scanner sc = new Scanner(userPosts);
						String id = sc.nextLine();
						String likes = sc.nextLine();
						String ext = sc.nextLine();

						ret.append(fileNameWEx+"."+ext);
						ret.append("\n"+id);
						ret.append("\n"+likes+"\n\n");

						count--;
						sc.close();
					}
				}
			}
		}

		if(count == n) 
			throw new HaveNoPhotosExeption(); //se os utilizadores que sigo nao têm fotos no mural

		if(count !=0)
			ret.append("That's all.");

		return ret.toString();
	}

	/**
	 * Give like on a Post
	 * @param postID
	 * @param username
	 * @return
	 * @throws IOException 
	 * @throws NoPostExeption 
	 */
	public String like(String postID, User username) throws IOException, NoPostExeption {

		File dir = new File(POST_DIRECTORY);
		File[] directoryListing = dir.listFiles();

		try {
			//Iterate through users post folders
			for(File userFolder: directoryListing) {
				if(userFolder.isDirectory()) {

					File dir2 = new File(POST_DIRECTORY+"/"+userFolder.getName());
					File[] directoryListing2 = dir2.listFiles();

					//Iterate through userPosts
					for(File userPosts: directoryListing2) {

						//making changes only in txt files
						if(userPosts.getPath().substring(userPosts.getPath().lastIndexOf(".")).equals(".txt")){
							Scanner sc = new Scanner(userPosts);

							String idLine = sc.nextLine();
							String id = idLine.substring(idLine.lastIndexOf(":")+1);

							if(id.equals(postID)) {
								String likesLine = sc.nextLine().substring(idLine.lastIndexOf(":")+4);
								String extensao = sc.nextLine();
								int likes = Integer.parseInt(likesLine);
								Files.write(userPosts.toPath(), (idLine+"\n"+"Likes:"+String.valueOf(likes+1)+"\n"+extensao).getBytes());
								sc.close();
								return "Like done.";
							}

							sc.close();
						}
					}
				}
			}

		}catch(NullPointerException e) {
			throw new NoPostExeption(); //se nao existir nenhuma foto para meter like
		}
		return new NoPostExeption().getMessage();
	}


	/**
	 * @param username
	 * @return list with the users the current user follows
	 * @throws FileNotFoundException
	 */
	private ArrayList<String> getFollowed(String username) throws FileNotFoundException{
		ArrayList<String> userList = new ArrayList<String>();

		File userFile = new File("./Data/follows/"+username+"/following.txt");

		if(!userFile.getAbsoluteFile().exists())
			return null;

		Scanner sc = new Scanner(userFile);

		//Search through the file
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			userList.add(line);
		}

		sc.close();
		return userList;
	}


	/**
	 * Create Post File
	 * @param file
	 * @param userID
	 * @throws IOException
	 */
	private void createPostFile(byte[] buff ,String extensao, String file, User userID) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(buff);
		BufferedImage bImage2 = ImageIO.read(bis);
		ImageIO.write(bImage2, extensao, new File(POST_DIRECTORY+"/"+userID.getUsername()+"/"+file));
	}

	/**
	 * Create File
	 * @param file
	 * @param userID
	 * @return
	 * @throws IOException
	 */
	private File createFile(String file, User userID) throws IOException {
		File newFile = new File(POST_DIRECTORY+"/"+userID.getUsername()+"/"+file+".txt");
		return newFile;
	}

	/**
	 * Write on file the ID and likes starting from 0
	 * @param file
	 * @throws IOException
	 */
	private void writeFile(File file, String extensao) throws IOException {
		Files.write(file.toPath(), ("ID:"+nextID()+"\n"+"Likes:0\n"+extensao).getBytes(), StandardOpenOption.APPEND);
	}

	/**
	 * Give post next ID
	 * @return
	 * @throws IOException 
	 */
	private int nextID() throws IOException {
		File nextFile = new File(POST_DIRECTORY+"nextID.txt");

		if(!nextFile.exists()) {
			nextFile.createNewFile();
			Files.write(nextFile.toPath(), String.valueOf("0").getBytes());
		}
		Scanner sc = new Scanner(nextFile);

		int previousID =Integer.parseInt(sc.nextLine());

		Files.write(nextFile.toPath(), String.valueOf((previousID+1)).getBytes());

		sc.close();
		return previousID+1;
	}

	/**
	 * Create user Directories
	 * @param userID
	 * @throws IOException
	 */
	private void createDirectories(User userID) throws IOException {
		Path path = Paths.get("./Data/posts/"+userID);
		Files.createDirectory(path);
	}

}
