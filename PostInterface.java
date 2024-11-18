import java.util.ArrayList;
/**
 * CS180 Group Project
 * Program description here
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author MichaelIkriannikov
 * @author Levin
 * @version Nov 03, 2024
 */
public interface PostInterface {
    void createComment(User author, String text);
    boolean equals(Post post);
    void addComment(Comment comment);
    String getTitle();
    String getSubtext();
    User getAuthor();
    ArrayList<Comment> getComments();
    int getLikes();
    int getDislikes();
    void incrementLikes();
    void incrementDislikes();
    void removeLike();
    void removeDislike();
}