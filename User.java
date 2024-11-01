import java.util.ArrayList;

public class User {
    private ArrayList<User> friendsList = new ArrayList<User>(); //list of users that are friends/followed by this user
    private ArrayList<User> blockedList = new ArrayList<User>(); //list of users that are blocked by this user
    private static SocialMediaDatabase sm;
    private String username; //the name of this account
    private String password; //the password to this account
    private String aboutMe; //The "about me" section

    //The following two constructors are initial prototypes. Still haven't decided how a new User would be created.
  /*  public User(String username, String password, String aboutMe) { //passwords must be at least eight characters long?
        this.username = username;
        this.aboutMe = aboutMe;
        if (password.length() >= 8)
            this.password = password;
    }
    public User(String username, String password) { //passwords must be at least eight characters long?
        this.username = username;
        if (password.length() >= 8)
            this.password = password;
    } */

    public User(String userString, SocialMediaDatabase platform) {
        username = userString.substring(0, userString.indexOf(","));
        userString = userString.substring(userString.indexOf(",") + 1);

        password = userString.substring(0, userString.indexOf(","));
        userString = userString.substring(userString.indexOf(",") + 1);

        aboutMe = userString;

        sm = platform;
    }

    public boolean equals(Object account) { //checks if two accounts are the same
        if (account instanceof User) {
            return (username == getUsername() && password == getPassword());
        }
        return false;
    }

    public void removeFriend(User formerFriend) {
        try {
            for (int i = 0; i < friendsList.size(); i++) {
                if (formerFriend.getUsername().equals(friendsList.get(i).getUsername())) {
                    friendsList.remove(i);
                    break;
                }
            }

            throw new UserNotFoundException("No results for " + formerFriend.getUsername());
        } catch (UserNotFoundException e) {
            System.out.printf("%s is not in your friends list.", formerFriend.getUsername());
        }
    }

    public void addFriend(User newFriend) {
        try {
            ArrayList<User> users = sm.getUsers();

            if (sm.findUser(newFriend.getUsername()) != null) {
                if (friendsList.contains(newFriend)) { //checks if new friend is already in the user's friends list
                    System.out.printf("%s is already in your friends list.\n", newFriend.getUsername());
                    return;
                } else if ((blockedList.contains(newFriend))) {
                    System.out.printf("%s is blocked.\n", newFriend.getUsername());
                    return;
                } else {
                    friendsList.add(newFriend);
                    return;
                }
            }
            throw new UserNotFoundException("No results for " + newFriend.getUsername());
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void block(User blockedUser) {
        try {

            if (blockedList.contains(blockedUser)) { //checks if blockedUser is already blocked.
                System.out.printf("%s is already blocked.\n", blockedUser.getUsername());
                return;
            }

            ArrayList<User> users = sm.getUsers();
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).equals(blockedUser)) {
                    blockedList.add(blockedUser);
                    if (friendsList.contains(blockedUser))
                        friendsList.remove(blockedUser);
                    return;
                }
            }
            throw new UserNotFoundException("No results for " + blockedUser.getUsername());
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean equals(User user) {
        return (username.equals(user.getUsername()) && password.equals(user.getPassword()) &&
                aboutMe.equals(user.getAboutMe()));
    }

    public void createPost() {
        
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

    public String toString() {
        return String.format("%s,%s,%s", username, password, aboutMe);
    }
}
