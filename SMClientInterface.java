import java.io.IOException;
import java.util.ArrayList;

public interface SMClientInterface {

    boolean echo() throws IOException;
    boolean login(String username, String password) throws IOException;
    boolean createUser(String username, String password, String aboutMe) throws IOException;
    boolean deleteUser(String username) throws IOException;
    ArrayList<ArrayList<String>> displayPosts() throws IOException;
    ArrayList<ArrayList<String>> displayComments(String postTitle) throws IOException;

    ArrayList<String[]> displayProfile(String profileUsername) throws IOException;
    boolean addFriend(String friendUsername) throws IOException;
    boolean deleteFriend(String friendUsername) throws IOException;
    boolean blockUser(String blockUsername) throws IOException;
    boolean unblockUser(String blockUsername) throws IOException;
    boolean hidePost(String postTitle) throws IOException;
    boolean unhidePost(String postTitle) throws IOException;
    boolean createPost(String title, String subtext) throws IOException;
    boolean deletePost(String postTitle) throws IOException;
    boolean createComment(String postTitle, String comment) throws IOException;

    boolean deleteComment(String postTitle, String comment) throws IOException;
    boolean likePost(String postTitle) throws IOException;
    boolean unlikePost(String postTitle) throws IOException;
    boolean dislikePost(String postTitle) throws IOException;

    boolean undislikePost(String postTitle) throws IOException;
    boolean likeComment(String postTitle, String comment) throws IOException;
    boolean unlikeComment(String postTitle, String comment) throws IOException;
    boolean dislikeComment(String postTitle, String comment) throws IOException;
    boolean undislikeComment(String postTitle, String comment) throws IOException;
    void logout() throws IOException;
    boolean searchUser(String username) throws IOException;
}
