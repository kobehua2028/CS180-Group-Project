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
    ArrayList<Post> getHiddenPosts();
    ArrayList<Post> getUserPosts();

    ArrayList<Post> getLikedPosts();
    ArrayList<Post> getDislikedPosts();

    ArrayList<Comment> getDislikedComments();
    ArrayList<Comment> getLikedComments();

    void addLikedPost(Post post);
    void removeLikedPost(Post post);

    void addDislikedPost(Post post);
    void removeDislikedPost(Post post);

    void addLikedComment(Comment comment);
    void removeLikedComment(Comment comment);

    void addDislikedComment(Comment comment);
    void removeDislikedComment(Comment comment);
}
