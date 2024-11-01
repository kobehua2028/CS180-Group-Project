import java.util.ArrayList;

public interface DatabaseInterface {
    /*
    * ArrayList<User> users //All users that exist on the platform.
    * ArrayList<Post> posts //All posts that exist on the platform.
      ArrayList<Comment> comments //All comments that exist on the platform, including replies.
    *
    *
    *
    *
    *
    *  */

    //The following three methods are inspired by readResearcher() from PRJ-3
    boolean readUsers(String usersFile); //File containing all users that initially exist
    boolean readPosts(String postsFile); //File containing all posts that initially exist
    boolean readComments(String commentsFile); //File containing all comments that initially exist.


    boolean createNewUser(String username, String password); //A method that creates a new user
                                                    // if the username and password exist.

    //Ideally, the three methods would be triggered by User, Post, and Comment objects
    boolean deleteUser(User markedForDeletion); //Removes user from database
    boolean deletePost(Post markedForDeletion); //Removes post from database
    boolean deleteComment(Comment comment); //Removes comment from database

    ArrayList<Post> newFeed(User user); //returns a list of posts in reverse chronological order.
                                        //Filters posts by blocked users.
                                        //Prioritizes posts by friends?
    
    //methods used by SocialMediaDatabase:
    User findUser(String username);

    Post findPost(String title);

    void readUsers();

    void readPosts();

    void readComments();

    ArrayList<Post> getPosts();

    void createUser();

}
