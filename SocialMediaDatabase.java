import java.util.ArrayList;
import java.io.*;
import javax.swing.JOptionPane;

public class SocialMediaDatabase {
    private ArrayList<Post> posts = new ArrayList<Post>(); //list of all posts on the platform
                                                    // (in reverse chronological order?)
    private ArrayList<User> users = new ArrayList<User>(); //list of all user accounts on the platform
    //private ArrayList<Comment> comments = new ArrayList<Comment>(); //This array should be in the posts imo (Abdul)
    private String usersIn; //filename that supplied a list of all initial users
    private String postsIn;
    private String commentsIn;
    private String friendsIn;
    private String blockedIn;


    public SocialMediaDatabase(String usersIn, String postsIn, String commentsIn, String friendsIn, String blockedIn) {
        this.usersIn = usersIn;
        this.postsIn = postsIn;
        this.commentsIn = commentsIn;
        this.friendsIn = friendsIn;
        this.blockedIn = blockedIn;
    }
    public User findUser(String username) { //validates if there's a user with the same username
        for (int i = 0; i < users.size(); i++) {
            if (username.equals(users.get(i).getUsername()))
                return users.get(i);
        }
        return null;
    }
    public Post findPost(String title) {
        for (int i = 0; i < posts.size(); i++) {
            if (title.equals(posts.get(i).getTitle()))
                return posts.get(i);
        }
        return null;
    }

    public void readUsers() {
        try (BufferedReader bfr = new BufferedReader(new FileReader(new File(usersIn)))) {
             while (true) {
                 String userLine = bfr.readLine();
                 if (userLine == null)
                     break;
                 users.add(new User(userLine, this));
             }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readPosts() {
        try (BufferedReader bfr = new BufferedReader(new FileReader(new File(postsIn)))) {
            while (true) {
                String postLine = bfr.readLine();
                if (postLine == null)
                    break;
                if (findUser(postLine.substring(0, postLine.indexOf(","))) != null)
                    posts.add(new Post(postLine, this));
            }
        } catch (IOException e) {
            return;
        }
    }

    public void readComments() {
        try (BufferedReader bfr = new BufferedReader(new FileReader(new File(commentsIn)))) {
            while (true) {
                String commentLine = bfr.readLine();
                String commentLineCopy = commentLine;
                if (commentLine == null)
                    break;
                String author = commentLineCopy.substring(0, commentLineCopy.indexOf(","));
                commentLineCopy = commentLineCopy.substring(commentLineCopy.indexOf(",") + 1);
                String title = commentLineCopy.substring(0, commentLineCopy.indexOf(","));

                if (findUser(author) != null && findPost(title) != null)
                    findPost(title).addComment(new Comment(commentLine, this));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFriends() {
        try (BufferedReader bfr = new BufferedReader(new FileReader(new File(friendsIn)))) {
            while (true) {
                String friendsLine = bfr.readLine();
                if (friendsLine == null)
                    break;
                String user = friendsLine.substring(0, friendsLine.indexOf(","));
                if (findUser(user) != null) {
                    String[] friendsList = friendsLine.split(",");
                    for (int j = 1; j < friendsList.length; j++) {
                        if (findUser(friendsList[j]) != null)
                            findUser(user).addFriend(findUser(friendsList[j]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readBlocked() {
        try (BufferedReader bfr = new BufferedReader(new FileReader(new File(blockedIn)))) {
            while (true) {
                String blockedLine = bfr.readLine();
                if (blockedLine == null)
                    break;
                String user = blockedLine.substring(0, blockedLine.indexOf(","));
                if (findUser(user) != null) {
                    String[] blockedList = blockedLine.split(",");
                    for (int j = 1; j < blockedList.length; j++) {
                        if (findUser(blockedList[j]) != null)
                            findUser(user).block(findUser(blockedList[j]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void createUser() {
        try {
            String username;
            String password;
            String message = "";
            do {
                message += "Create a username:";
                username = JOptionPane.showInputDialog(message);

                if (!Character.isLetter(username.charAt(0))) {
                    message = "Invalid Username: Must begin with a letter.\n";
                }

                else {
                    message = ""; //resets message variable
                    do {
                        message += "Create a password:";
                        password = JOptionPane.showInputDialog(message);

                        if (password.length() < 8)
                            message = "Invalid Password: Must be at least eight characters long.\n";

                    } while (password.length() < 8); //More requirements? Special characters?
                }

            } while (!Character.isLetter(username.charAt(0)));
            JOptionPane.showMessageDialog(null, "Your username is: " + username);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
