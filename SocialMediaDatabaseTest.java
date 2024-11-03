import org.junit.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
/**
 * CS180 Group Project
 * Program description here
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author
 * @version Nov 03, 2024
 */
public class SocialMediaDatabaseTest {
    private SocialMediaDatabase sm = new SocialMediaDatabase("users.dat", "posts.dat");
    private User expectedUser = new User("Dunsmore","CS180istheBest",
            "I teach CS180", new ArrayList<>(), new ArrayList<>(), sm);
    private Post expectedPost = new Post(expectedUser, "Purdue CS180",
            "Purdue CS 180 is the best CS class", new ArrayList<Comment>(), 0, 0, sm);
    private ArrayList<User> expected = new ArrayList<User>();
    private ArrayList<Post> expectedPosts = new ArrayList<Post>();

    @Test
    public void testGetUser() {
        User expectedOutput = expectedUser;

        assertEquals(expectedOutput, sm.getUsers().get(0));
    }

    @Test
    public void testGetPost() {
        Post expectedOutput = expectedPost;

        assertEquals(expectedOutput, sm.getPosts().get(0));
    }

    @Test
    public void testAddUser() {
        sm.addUser(expectedUser);

        expected.add(expectedUser);
        expected.add(expectedUser);

        assertEquals(expected, sm.getUsers());
    }

    @Test
    public void testAddPost() {
        sm.addPost(expectedPost);

        expectedPosts.add(expectedPost);
        expectedPosts.add(expectedPost);

        assertEquals(expectedPosts, sm.getPosts());

    }

    @Test
    public void testCreateUser() {
        User test = sm.createUser("Frank", "Maximilton", "Hey I'm a robot.");
        expected = new ArrayList<User>();
        expected.add(expectedUser);
        expected.add(test);
        assertEquals(expected, sm.getUsers());
    }

    @Test
    public void testFindUser() {
        if (!sm.getUsers().contains(expectedUser)) {
            sm.createUser("Dunsmore","CS180istheBest", "I teach CS180");
        }
        User foundUser = sm.findUser("Dunsmore");
        assertEquals(expectedUser, foundUser);
    }

    @Test
    public void testUserExists() {
        if (!sm.getUsers().contains(expectedUser)) {
            sm.createUser("Dunsmore","CS180istheBest", "I teach CS180");
        }
        boolean exists = sm.userExists("Dunsmore");
        assertTrue(exists);
    }

    @Test
    public void testFindPost() {
        if (!sm.getPosts().contains(expectedPost)) {
            if (!sm.getUsers().contains(expectedUser)) {
                sm.createUser("Dunsmore","CS180istheBest", "I teach CS180");
            }
            sm.getUsers().get(0).createPost("Purdue CS180", "Purdue CS 180 is the best CS class");
        }
        Post foundPost = sm.findPost("Purdue CS180");
        assertEquals(expectedPost, foundPost);
    }

    @Test
    public void testReadUsers() {
        User test = new User("PurduePete", "MyPurdue1234", "Hey guys it's me, Pete from Purdue!",
                new ArrayList<User>(), new ArrayList<User>(), sm);
        sm.readUsers();
        ArrayList<User> expected = new ArrayList<>();
        expected.add(expectedUser);
        expected.add(test);
        assertEquals(expected, sm.getUsers());
    }


/*public void readPosts() {
        try (FileInputStream fis = new FileInputStream(postsIn);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            this.posts = (ArrayList<Post>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/

    @Test
    public void testReadPosts() {
        sm.readPosts();
        expectedPosts.add(expectedPost);

        assertEquals(true, expectedPosts.get(0).equals(sm.getPosts().get(0)));
    }

    @Test
    public void testWriteUser() {
        User newUser = new User("KrisDreemur" , "DeltaruneRef", "Hey it's me Kris",
                new ArrayList<User>(), new ArrayList<User>(), sm);
        //Creating a new User object implicitly calls writeUser()

        assertEquals(true, sm.getUsers().contains(newUser));
    }

    @Test
    public void testWritePost() {
        Post newPost = new Post(expectedUser, "Test case Object", "Blank",
                new ArrayList<Comment>(), 0, 0, sm);
        //Creating a post implicitly calls writePost()

        assertEquals(true, sm.getPosts().contains(newPost));
    }




}