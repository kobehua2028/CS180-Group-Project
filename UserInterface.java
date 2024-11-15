import java.util.ArrayList;

/**
 * CS180 Group Project
 * An interface for the User class
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author MichaelIkriannikov
 * @version Nov 03, 2024
 */
public interface UserInterface {
    
    boolean equals(Object account);
    
    void createPost(String title, String subtext);
    
    void removeFriend(User formerFriend);
    
    void addFriend(User newFriend);
    
    void block(User blockedUser);
    
    String getPassword();
    
    String getUsername();
    
    String getAboutMe();
    
    ArrayList<User> getFriendsList();
    
    ArrayList<User> getBlockedList();

    void hidePost(Post post);
}
