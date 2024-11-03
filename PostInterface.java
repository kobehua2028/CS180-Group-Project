import java.util.ArrayList;

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
}
