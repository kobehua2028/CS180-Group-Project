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


    //These six methods should implement synchronized
    public void addLike();
    public void removeLike();

    public void addDislike();
    public void removeDislike();

    void incrementCommentCount();
    void decrementCommentCount();







}
