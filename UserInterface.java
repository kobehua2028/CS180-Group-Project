/**
 * CS180 Group Project
 * Program description here
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author
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
}
