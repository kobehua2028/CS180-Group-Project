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
    void createPost(String title, String subtext);
    void addFriend(User newFriend, SocialMediaDatabase sm);
    void removeFriend(User formerFriend, SocialMediaDatabase sm);
    void block(User blockedUser, SocialMediaDatabase sm);

    String getUsername();
    String getPassword();
    String getAboutMe();
    ArrayList<User> getFriendsList();
    ArrayList<User> getBlockedList();
}
