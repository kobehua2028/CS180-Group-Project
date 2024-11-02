import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private ArrayList<User> friendsList = new ArrayList<User>(); //list of users that are friends/followed by this user
    private ArrayList<User> blockedList = new ArrayList<User>(); //list of users that are blocked by this user
    private String username; //the name of this account
    private String password; //the password to this account
    private String aboutMe; //The "about me" section

    public User(String username, String password, String aboutMe, ArrayList<User> friendsList, ArrayList<User> blockedList, SocialMediaDatabase sm) {
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

        this.username = username;
        this.password = password;
        this.aboutMe = aboutMe;
        this.friendsList = friendsList;
        this.blockedList = blockedList;
        sm.writeUser(this);
    }

    public boolean equals(Object account) { //checks if two accounts are the same
        if (account instanceof User) {
            return (username.equals(this.getUsername()) && password.equals(this.getPassword()));
        }
        return false;
    }

    public void createPost(String title, String subtext) {
        Post post = new Post(this, title, subtext, new ArrayList<Comment>(), 0, 0, sm);
    }

    public void removeFriend(User formerFriend, SocialMediaDatabase sm) {
        if (!friendsList.remove(formerFriend)) {
            throw new IllegalArgumentException("Friend does not exist");
        }
    }

    public void addFriend(User newFriend) {
        if (friendsList.contains(newFriend)) {
            throw new IllegalArgumentException("Friend already exists");
        }
        if (blockedList.contains(newFriend)) {
            throw new IllegalArgumentException("User is blocked");
        }
        friendsList.add(newFriend);
        sm.overwriteUser(this);
    }

    public void block(User blockedUser, SocialMediaDatabase sm) {
        if (blockedList.contains(blockedUser)) {
            throw new IllegalArgumentException("User is already blocked");
        }
        blockedList.add(blockedUser);
        sm.overwriteUser(this);
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
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
}
