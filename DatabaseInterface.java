import java.util.ArrayList;

public interface DatabaseInterface {
    /*
    * ArrayList<User> users //All users that exist on the platform.
    * ArrayList<Post> posts //All posts that exist on the platform.
      ArrayList<Comment> comments //All comments that exist on the platform, including replies.
    *
    *
    *  */
    User findUser(String username);

    boolean userExists(String username)
    
    Post findPost(String title);

    ArrayList<User> getUsers();

    ArrayList<Post> getPosts();

    void addUser(User user);

    void addPost(Post post);

    void overwriteUser(User user);

    void overwritePost(Post post);

    void readUsers();

    void readPosts();

    void writeUser(User user);

    void writePost(Post post);
}
