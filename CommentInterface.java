/**
 * CS180 Group Project
 * Interface for the Comment Class
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author Levin
 * @author Abdul
 * @version Nov 03, 2024
 */
public interface CommentInterface {
    String getText();
    int getLikes();
    int getDislikes();
    User getAuthor();
    Post getPost();
    ArrayList<User> getLikers();
    ArrayList<User> getDislikers();
    void incrementLikes();
    void incrementDislikes();
    void removeLike();
    void removeDislike();
    void addLiker(User user);
    void addDisliker(User user);
    void removeLiker(User user);
    void removeDisliker(User user);

}
