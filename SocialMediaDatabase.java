import java.lang.reflect.Array;
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



    public SocialMediaDatabase(String usersIn, String postsIn, String commentsIn) {
        this.usersIn = usersIn;
        this.postsIn = postsIn;
        this.commentsIn = commentsIn;
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
            return;
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
