import java.util.ArrayList;
/**
 * CS180 Group Project
 * An interface for SocialMediaDatabase
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author MichaelIkriannikov
 * @version Nov 03, 2024
 */
public interface DatabaseInterface {
    
    User findUser(String username);
    
    boolean userExists(String username);
    
    Post findPost(String title);
    
    ArrayList<User> getUsers();
    
    ArrayList<Post> getPosts();
    
    void addUser(User user);
    
    void addPost(Post post);
    
    void readUsers();
    
    void readPosts();
    
    void writeUser(User user);
    
    void writePost(Post post);
}
