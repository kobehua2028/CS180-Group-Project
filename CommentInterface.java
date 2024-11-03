/**
 * CS180 Group Project
 * Interface for the Comment Class
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author
 * @version Nov 03, 2024
 */
public interface CommentInterface {
    String getText();
    int getLikes();
    int getDislikes();
    User getAuthor();
    Post getPost();
    void incrementLikes();
    void incrementDislikes();
}
