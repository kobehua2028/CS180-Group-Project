import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import javax.swing.JOptionPane;

public class SocialMediaDatabase implements DatabaseInterface, Serializable {
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

    public void createUser(String username, String password, String aboutMe) {
        if (aboutMe == null) {
            aboutMe = "This is me!";
        }
        try {
            User user = new User(username, password, aboutMe, new ArrayList<User>(), new ArrayList<User>(), this);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
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

    public void readUsers() {
        try (FileInputStream fis = new FileInputStream(usersIn);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            this.users = (ArrayList<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void readPosts() {
        try (FileInputStream fis = new FileInputStream(postsIn);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            this.posts = (ArrayList<Post>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
        } else {
            for (User compareUser : users) {
                if (user.getUsername().equals(compareUser.getUsername())) {
                    users.set(users.indexOf(compareUser), user);
                }
            }
        }
        try (FileOutputStream fos = new FileOutputStream(usersIn);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writePost(Post post) {
        if (!posts.contains(post)) {
            posts.add(post);
        } else {
            for (Post comparePost : posts) {
                if (post.getTitle().equals(comparePost.getTitle())) {
                    posts.set(posts.indexOf(comparePost), post);
                }
            }
        }
        try (FileOutputStream fos = new FileOutputStream(postsIn);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(posts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
