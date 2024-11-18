import java.util.ArrayList;

/**
 * CS180 Group Project
 * An interface for the Server class
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author MichaelIkriannikov
 * @version Nov 03, 2024
 */
public interface ServerInterface {

    boolean login(String username, String password);
    boolean createUser(String username, String password, String aboutMe);
    boolean deleteUser(String username, String deletedUsername);
    ArrayList<String> displayPosts(String username);
    ArrayList<String> displayComments(String postTitle);
    ArrayList<String> displayProfile(String viewerUsername, String profileUsername);
    boolean searchUser(String username);
    boolean addFriend(String username, String friendUsername);
    boolean deleteFriend(String username, String friendUsername);
    boolean blockUser(String username, String blockUsername);
    boolean unblockUser(String username, String blockUsername);
    boolean hidePost(String username, String postTitle);
    boolean unhidePost(String username, String postTitle);
    boolean createPost(String authorUsername, String title, String subtext);
    boolean deletePost(String username, String postTitle);
    boolean createComment(String postTitle, String username, String comment);
    boolean deleteComment(String postTitle, String deleterUsername, String comment);
    boolean likePost(String username, String postTitle);
    boolean unlikePost(String username, String postTitle);
    boolean dislikePost(String username, String postTitle);
    boolean undislikePost(String username, String postTitle);
    boolean likeComment(String postTitle, String likerUsername, String comment);
    boolean unlikeComment(String postTitle, String likerUsername, String comment);
    boolean dislikeComment(String postTitle, String dislikerUsername, String comment);
    boolean undislikeComment(String postTitle, String dislikerUsername, String comment);
    void setSM(SocialMediaDatabase sm);
}
