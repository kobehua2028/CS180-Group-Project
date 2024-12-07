import java.util.ArrayList;

/**
 * CS180 Group Project
 * An interface for user class
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author Levin
 * @author Mishal
 * @version Nov 17, 2024
 */

public interface UserInterface {
    void setDeleted(Boolean delete);

    Boolean isDeleted();

    void changeAboutMe(String newAboutMe);

    boolean equals(Object account);

    Post createPost(String title, String subtext);

    void removeFriend(User formerFriend);

    void addFriend(User newFriend);

    void block(User blockedUser);

    void unblock(User unblockedUser, SocialMediaDatabase sm);

    boolean deletePost(Post post);

    String getPassword();

    String getUsername();

    String getAboutMe();

    ArrayList<User> getFriendsList();

    ArrayList<User> getBlockedList();

    ArrayList<Post> getUserPosts();

    ArrayList<Post> getHiddenPosts();

    void hidePost(Post post);

    ArrayList<Post> getLikedPosts();

    ArrayList<Post> getDislikedPosts();

    void addLikedPost(Post post);

    void removeLikedPost(Post post);

    void addDislikedPost(Post post);

    void removeDislikedPost(Post post);

    void unhidePost(Post post);

    boolean searchHiddenPosts(Post post);

    boolean searhBlockedList(User author);

    boolean isFriend(String username);
}
