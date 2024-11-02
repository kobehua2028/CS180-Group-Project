import java.util.Scanner;
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


    public SocialMediaDatabase(String usersIn, String postsIn) {
        this.usersIn = usersIn;
        this.postsIn = postsIn;
    }
    public User findUser(String username) { //validates if there's a user with the same username
        for (User user : users) {
            if (username.equals(user.getUsername())) {
                return user;
            }
        }
        return null;
    }

    public boolean userExists(String username) {
        for (User user : users) {
            if (username.equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public Post findPost(String title) {
        for (Post post : posts) {
            if (title.equals(post.getTitle()))
                return post;
        }
        return null;
    }

    public void readUsers() {
        //
    }

    public void readPosts() {
        //
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public void writeUser(User user) {
    }

    public void overwriteUser(User user) {
    }

    public void writePost(Post post) {
    }

    public void overwritePost(Post post) {
    }
}