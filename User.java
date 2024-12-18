import java.io.Serializable;
import java.util.ArrayList;

/**
 * CS180 Group Project
 * User class that implements user functionality and fields
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author Abdulmajed AlQarni
 * @author Michael Ikriannikov
 * @author Levin
 * @author Kobe
 * @version Nov 03, 2024
 */

public class User implements Serializable, UserInterface {
    private static final long serialVersionUID = 1810526504588534166L;
    private final String username; //the name of this account
    private final String password; //the password to this account
    private final SocialMediaDatabase sm;
    public int i = 0;
    private ArrayList<User> friendsList = new ArrayList<User>(); //list of users that are friends/followed by this user
    private ArrayList<User> blockedList = new ArrayList<User>(); //list of users that are blocked by this user
    private ArrayList<Post> likedPosts = new ArrayList<Post>();
    private ArrayList<Post> dislikedPosts = new ArrayList<Post>();
    private ArrayList<Post> hiddenPosts = new ArrayList<>();
    private ArrayList<Post> userPosts = new ArrayList<>();
    private String aboutMe; //The "about me" section
    private boolean isDeleted;

    public User(String username, String password, String aboutMe, ArrayList<User> friendsList,
                ArrayList<User> blockedList, ArrayList<Post> likedPosts, ArrayList<Post> dislikedPosts,
                ArrayList<Post> hiddenPosts, ArrayList<Post> userPosts, SocialMediaDatabase sm) {
        isDeleted = false;

        // check if len(password) > 5 & < 50
        if (password.length() < 5 || password.length() > 50) {
            throw new IllegalArgumentException("Password must be between 5 and 50 characters");
        }

        // check if username starts with letter
        char first = username.charAt(0);
        if ((first < 65 || first > 90) && (first < 97 || first > 122)) {
            throw new IllegalArgumentException("Usernames must start with a letter");
        }

        // check if len(username) > 3 & < 15
        if (username.length() < 3 || username.length() > 15) {
            throw new IllegalArgumentException("Usernames must be between 3 and 15 characters");
        }

        // check if username exists already
        if (sm.userExists(username)) {
            throw new IllegalArgumentException("Username " + username + " already exists");
        }

        this.username = username;
        this.password = password;
        this.aboutMe = aboutMe;
        this.friendsList = friendsList;
        this.blockedList = blockedList;
        this.likedPosts = likedPosts;
        this.dislikedPosts = dislikedPosts;
        this.hiddenPosts = hiddenPosts;
        this.userPosts = userPosts;
        this.sm = sm;
        sm.writeUser(this);
    }

    public void setDeleted(Boolean delete) {
        isDeleted = delete;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void changeAboutMe(String newAboutMe) {
        this.aboutMe = newAboutMe;
    }

    public boolean equals(Object account) { //checks if two accounts are the same
        if (account instanceof User) {
            return (username.equals(((User) account).getUsername()) && password.equals(((User) account).getPassword()));
        }
        return false;
    }

    public Post createPost(String title, String subtext) {
        Post post = new Post(this, title, subtext, new ArrayList<Comment>(), 0, 0, sm);
        userPosts.add(post);
        sm.writeUser(this);
        return post;
    }

    public void removeFriend(User formerFriend) {
        if (!friendsList.remove(formerFriend)) {
            throw new IllegalArgumentException("Friend does not exist");
        }
        sm.writeUser(this);
    }

    public void addFriend(User newFriend) {
        if (friendsList.contains(newFriend)) {
            throw new IllegalArgumentException("Friend already exists");
        }
        if (blockedList.contains(newFriend)) {
            throw new IllegalArgumentException("User is blocked");
        }
        friendsList.add(newFriend);
        sm.writeUser(this);
    }

    public void block(User blockedUser) {
        if (blockedList.contains(blockedUser)) {
            throw new IllegalArgumentException("User is already blocked");
        }

        blockedList.add(blockedUser);
        sm.writeUser(this);
    }

    public void unblock(User unblockedUser, SocialMediaDatabase sm1) {
        if (!blockedList.remove(unblockedUser)) {
            throw new IllegalArgumentException("User is not blocked");
        }
        sm1.writeUser(this);
    }

    public boolean deletePost(Post post) {
        for (int i = 0; i < sm.getPosts().size(); i++) {
            if (this.equals(sm.getPosts().get(i))) {
                synchronized (new Object()) {
                    sm.getPosts().remove(i);
                    userPosts.remove(post);
                }
                return true;
            }
        }
        return false;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        if (isDeleted) {
            return "[Deleted]";
        }
        return username;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public ArrayList<User> getFriendsList() {
        return friendsList;
    }

    public ArrayList<User> getBlockedList() {
        return blockedList;
    }

    public ArrayList<Post> getUserPosts() {
        return userPosts;
    }

    public ArrayList<Post> getHiddenPosts() {
        return hiddenPosts;
    }

    public void hidePost(Post post) {
        if (!hiddenPosts.contains(post)) {
            hiddenPosts.add(post);
        } else {
            throw new IllegalArgumentException("Post already hidden");
        }
        sm.writeUser(this);
    }

    public ArrayList<Post> getLikedPosts() {
        return likedPosts;
    }

    public ArrayList<Post> getDislikedPosts() {
        return dislikedPosts;
    }

    public void addLikedPost(Post post) {
        likedPosts.add(post);
    }

    public void removeLikedPost(Post post) {
        for (int i = 0; i < likedPosts.size(); i++) {
            if (likedPosts.get(i).equals(post)) {
                likedPosts.remove(i);
            }
        }
    }

    public void addDislikedPost(Post post) {
        dislikedPosts.add(post);
    }

    public void removeDislikedPost(Post post) {
        for (int i = 0; i < dislikedPosts.size(); i++) {
            if (post.equals(dislikedPosts.get(i))) {
                dislikedPosts.remove(i);
            }
        }
    }

    public void unhidePost(Post post) {
        for (int i = 0; i < hiddenPosts.size(); i++) {
            if (post.equals(hiddenPosts.get(i))) {
                hiddenPosts.remove(i);
            }
        }
    }

    public boolean searchHiddenPosts(Post post) {
        for (int i = 0; i < hiddenPosts.size(); i++) {
            if (post.getTitle().equals(hiddenPosts.get(i).getTitle()) &&
                    post.getSubtext().equals(hiddenPosts.get(i).getSubtext()) &&
                    post.getAuthor().getUsername().equals(hiddenPosts.get(i).getAuthor().getUsername())) {
                return true;
            }
        }
        return false;
    }

    public boolean searhBlockedList(User author) {
        for (int i = 0; i < blockedList.size(); i++) {
            if (blockedList.get(i).getUsername().equals(author.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public boolean isFriend(String username) {
        for (User friend : friendsList) {
            if (friend.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}