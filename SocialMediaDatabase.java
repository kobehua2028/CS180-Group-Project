import java.lang.reflect.Array;
import java.util.ArrayList;
import java.io.*;
import javax.swing.JOptionPane;

public class SocialMediaDatabase {
    private ArrayList<Post> posts = new ArrayList<Post>(); //list of all posts on the platform
                                                    // (in reverse chronological order?)
    private ArrayList<User> users = new ArrayList<User>(); //list of all user accounts on the platform
    private String usersIn; //filename that supplied a list of all initial users
    private String postsIn;
    private String commentsIn;



    public SocialMediaDatabase(String usersIn, String postsIn, String commentsIn) {
        this.usersIn = usersIn;
        this.postsIn = postsIn;
        this.commentsIn = commentsIn;
    }

    public void readUsers() {
        try (BufferedReader bfr = new BufferedReader(new FileReader(usersIn))) {
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
        try (BufferedReader bfr = new BufferedReader(new FileReader(postsIn))) {
            while (true) {
                String postLine = bfr.readLine();
                if (postLine == null)
                    break;
                posts.add(new Post(postLine));
            }
        } catch (IOException e) {
            return;
        }
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

    public ArrayList<User> getUsers() {
        return users;
    }

}
