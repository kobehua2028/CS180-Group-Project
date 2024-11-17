import java.util.ArrayList;
import java.io.*;
import javax.swing.JOptionPane;
/**
 * CS180 Group Project
 * Program description here
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author Abdulmajed AlQarni
 * @author Levin
 * @author Kobe
 * @version Nov 03, 2024
 */
public class SocialMediaDatabase implements Serializable {
    private ArrayList<Post> posts = new ArrayList<Post>(); //list of all posts on the platform
    // (in reverse chronological order?)
    private ArrayList<User> users = new ArrayList<User>(); //list of all user accounts on the platform
    //private ArrayList<Comment> comments = new ArrayList<Comment>(); //This array should be in the posts imo (Abdul)
    private final String usersIn; //filename that supplied a list of all initial users
    private final String postsIn;


    public SocialMediaDatabase(String usersIn, String postsIn) {
        this.usersIn = usersIn;
        this.postsIn = postsIn;
        this.readUsers();
        this.readPosts();
    }

    public User createUser(String username, String password, String aboutMe) {
        if (aboutMe == null) {
            aboutMe = "This is me!";
        }
        try {
            return new User(username, password, aboutMe, new ArrayList<User>(), new ArrayList<User>(),
                    new ArrayList<Post>(), new ArrayList<Post>(), new ArrayList<Post>(), new ArrayList<Post>(),
                    this);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    public User findUser(String username) { //validates if there's a user with the same username
        this.readUsers();
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
        readUsers();
        return users;
    }

    public ArrayList<Post> getPosts() {
        readPosts();
        return posts;
    }

    public void addUser(User user) {
        synchronized (new Object()) {
            users.add(user);
        }
    }

    public void addPost(Post post) {
        synchronized (new Object()) {
            posts.add(post);
        }
    }

    public synchronized void readUsers() {
        if (new File(usersIn).exists() && new File(postsIn).length() > 0) {
            try (FileInputStream fis = new FileInputStream(usersIn);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                this.users = (ArrayList<User>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void readPosts() {
        if (new File(postsIn).exists() && new File(postsIn).length() > 0) {
            try (FileInputStream fis = new FileInputStream(postsIn);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                this.posts = (ArrayList<Post>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void deleteUser(User user) {
        this.users.remove(user);
        this.updateUsers();
    }

    public synchronized void writeUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
        } else {
            for (User compareUser : users) {
                if (user.getUsername().equals(compareUser.getUsername())) {
                    users.set(users.indexOf(compareUser), user);
                }
            }
        }
        updateUsers();
    }

    public synchronized void updateUsers() {
        try (FileOutputStream fos = new FileOutputStream(usersIn);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
        readUsers();
    }

    public synchronized void deletePost(Post post) {
        this.posts.remove(post);
        this.updatePosts();
    }

    public synchronized void writePost(Post post) {
        if (!posts.contains(post)) {
            posts.add(post);
        } else {
            for (Post comparePost : posts) {
                if (post.getTitle().equals(comparePost.getTitle())) {
                    posts.set(posts.indexOf(comparePost), post);
                }
            }
        }
        updatePosts();
    }

    public synchronized void updatePosts() {
        try (FileOutputStream fos = new FileOutputStream(postsIn);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(posts);
        } catch (IOException e) {
            e.printStackTrace();
        }
        readPosts();
    }
}
//    public static void main(String[] args) {
//        SocialMediaDatabase sm = new SocialMediaDatabase("usersIn", "postsIn");
//        sm.createUser("KOBE", "password", "asjajfs");
//        sm.createUser("Levin", "password", "akjhsdgjshg");
//
//        sm.readUsers();
//
//        User kobe = sm.getUsers().get(0);
//        User levin = sm.getUsers().get(1);
//        kobe.addFriend(levin);
//        kobe.createPost("Tiger", "lil dawg");
//
//
//        sm.readUsers();
//        sm.readPosts();
//
//        String username1 = sm.getUsers().get(1).getUsername();
//        String password1 = sm.getUsers().get(1).getPassword();
//        String kobeFriendusername = sm.getUsers().get(0).getFriendsList().get(0).getUsername();
//        String username2 = sm.getUsers().get(0).getUsername();
//        String password2 = sm.getUsers().get(0).getPassword();
//
//        System.out.println(username1);
//        System.out.println(password1);
//        System.out.println("KOBES FRIENDS" + kobeFriendusername);
//        System.out.println(username2);
//        System.out.println(password2);
//
//        String postTitle = sm.getPosts().get(0).getTitle();
//        System.out.println(postTitle);
//
//
//
//    }
//
//}
