import java.util.ArrayList;


public interface PostInterface {
    /* Fields:
    static int commentCount
    static int likeCount
    static int dislikeCount  //static because they might be modified by multiple users simultaneously
    User originalPoster //The user who made this post
    String title //title of post
    ArrayList<Comment> comments //List of comments left by users.
        //Should the poster be able to delete comments on their post?
    *
    *
    *
    *
    * */
    void createComment(User author, String text);
    
    boolean equals(Post post);
    
    void addComment(Comment comment, SocialMediaDatabase sm);
    
    String getTitle();
    
    String getSubtext();
    
    User getAuthor();
    
    ArrayList<Comment> getComments();
    
    int getLikes();
    
    int getDislikes();
    
    void incrementLikes();

    public void incrementDislikes();
}
