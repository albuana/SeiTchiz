package server.handler;

import java.io.IOException;

import server.FileManager;
import server.domain.Post;
import server.domain.User;

/**
 * Handles creating a photo
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 *
 */
public class PostHandler {

    private byte[] file;
    private User userID;
    private String fileName;

    /*
     * Constructor
     */
    public PostHandler(byte[] data, String fileName ,User userID) {
        this.file = data;
        this.userID = userID;
        this.fileName = fileName;
        FileManager manager = new FileManager("./Data/posts","");
    }

    /*
     * Create a Post
     * @param file
     * @param userID
     * @return
     * @throws IOException
     */
    public String createPost() throws IOException {
        return Post.getInstance().createPost(file, fileName, userID);
    }

}