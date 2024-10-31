import java.util.ArrayList;

public interface UserInterface {
    /* Fields:
    String username
    String password
    ArrayList<User> friendsList
    ArrayList<User> blockedList
    ArrayList<Post> createdPosts (posts made by this user)
    ArrayList<Post> likedPosts (posts liked by this user)
    ArrayList<Post> dislikedPosts ???

    Profile picture (How to save an image in this???)


     */













    void setUsername(String username);
    String getUsername();

    void setPassword(String password);
    String getPassword();

    ArrayList<User> getFriendsList();
    ArrayList<User> getBlockedList();

    boolean addFriend(User newFriend); //A method that adds a user to the friend list. Return true if successful.
    boolean blockUser(User blockedUser); //A method that blocks a user. Return true if successful.

    Post createNewPost(String title); //A method that creates a new post and uploads it onto the SM database.
    Post deletePost(Post markedForDeletion); //A method that deletes a created post from the SM database;
                    //should it use the post's title instead?
    boolean likePost(Post goodPost); //A method that "likes" the post. Calls goodPost.addLike()
                                //Should it use post's title instead?
    boolean removeLikedPost(Post post); //A method that "unlikes" the post. Calls goodPost.removeLike()

    boolean dislikePost(Post badPost); //A method that "dislikes" the post. Calls goodPost.addDislike()
    boolean removeDislikePost(Post post); //A method that "undislikes" the post. Calls goodPost.addDislike()

    boolean createComment(Post post, String comment); //creates a Comment object using the supplied string and adds it
                                        //to the post's comment ArrayList.
    boolean deleteComment(Post post); //deletes comment left on post
                            //but what if the user left multiple comments?

    boolean createReply(Post post, Comment comment, String reply); //Replies to a comment left on a post.
                                                //Replies are represented by Comment objects
    boolean deleteReply(Post post, Comment comment); //Deletes reply.
}
