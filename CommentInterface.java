import java.util.ArrayList;

public interface CommentInterface {
    /* Fields:
    SocialMediaDatabase platform;
    static int likeCount
    static int dislikeCount
    static int replyCount //Unnecessary???
    User commentor //The user who created this comment
    ArrayList<Comment> replies //List of replies, represented by comment objects.
    String commentText //The actual text that makes up a comment (rename?)

    *
    *
    *
    *
    *
    *
    * */

    public void addLike();
    public void removeLike();

    public void addDislike();
    public void removeDislike();

    void incrementReplyCount();
    void decrementReplyCount();

    boolean setCommentText(String newText); //

}
