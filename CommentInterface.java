/**
 * CS180 Group Project
 * Program description here
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
