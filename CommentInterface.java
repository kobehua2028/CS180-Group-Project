public interface CommentInterface {
    String getText();
    int getLikes();
    int getDislikes();
    User getAuthor();
    Post getPost();
    void incrementLikes();
    void incrementDislikes();
}