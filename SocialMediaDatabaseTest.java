import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
/**
 * CS180 Group Project
 * Program description here
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author Kobe
 * @author Abdul
 * @version Nov 03, 2024
 */
public class SocialMediaDatabaseTest {
    private SocialMediaDatabase sm;
    private User expectedUser;
    private Post expectedPost;
    private ArrayList<User> expected;
    private ArrayList<Post> expectedPosts;
    @Before
    public void setUp() throws Exception {
        sm = new SocialMediaDatabase("users.dat", "posts.dat");
        expectedUser = new User("Dunsmore","CS180istheBest",
                "I teach CS180", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), sm);
        expectedPost = new Post(expectedUser, "Purdue CS180",
                "Purdue CS 180 is the best CS class", new ArrayList<Comment>(), 0, 0, sm);
        expected = new ArrayList<User>();
        expectedPosts = new ArrayList<Post>();
    }

    @After
    public void clearFile() throws IOException {
        File userFile = new File("users.dat");
        File postFile = new File("posts.dat");
        if (userFile.exists()) {
            userFile.delete();
        }
        if (postFile.exists()) {
            postFile.delete();
        }
        userFile.createNewFile();
        postFile.createNewFile();
        this.expectedUser = null;
        this.expectedPost = null;
        this.expected = null;
        this.expectedPosts = null;
        this.sm = null;
    }

    @Test
    public void testGetUser() {
        User expectedOutput = expectedUser;

        assertEquals(expectedOutput, sm.getUsers().get(0));
    }

    @Test
    public void testGetPost() {
        Post expectedOutput = expectedPost;

        assertEquals(expectedOutput.getTitle(), sm.getPosts().get(0).getTitle());
        assertEquals(expectedOutput.getAuthor(), sm.getPosts().get(0).getAuthor());
        assertEquals(expectedOutput.getSubtext(), sm.getPosts().get(0).getSubtext());

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
        assertEquals(true, sm.getPosts().contains(expectedPost));

    }

    @Test
    public void testCreateUser() {
        User test = sm.createUser("Frank", "Maximilton", "Hey I'm a robot.");
        assertEquals(true, sm.getUsers().contains(test));
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

        assertEquals(expectedPost.getTitle(), sm.getPosts().get(0).getTitle());
        assertEquals(expectedPost.getAuthor(), sm.getPosts().get(0).getAuthor());
        assertEquals(expectedPost.getSubtext(), sm.getPosts().get(0).getSubtext());
    }

    @Test
    public void testReadUsers() {
        User test = new User("PurduePete", "MyPurdue1234", "Hey guys it's me, Pete from Purdue!",
                new ArrayList<User>(), new ArrayList<User>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), sm);
        sm.readUsers();
        ArrayList<User> expected = new ArrayList<>();
        expected.add(expectedUser);
        expected.add(test);
        assertEquals(expected, sm.getUsers());
    }


    @Test
    public void testReadPosts() {
        sm.readPosts();
        expectedPosts.add(expectedPost);

        assertEquals(true, expectedPosts.get(0).equals(sm.getPosts().get(0)));
    }

    @Test
    public void testWriteUser() {
        User newUser = new User("KrisDreemur" , "DeltaruneRef", "Hey it's me Kris",
                new ArrayList<User>(), new ArrayList<User>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), sm);
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

    @Test
    public void testDeleteUser() {
        User markedForDeletion = new User("Terminated", "12345678", "But nobody came...",
                new ArrayList<User>(), new ArrayList<User>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(), sm);

        sm.deleteUser(markedForDeletion);
        assertEquals(false, sm.getUsers().contains(markedForDeletion));
    }

    @Test
    public void testDeletePost() {
        Post markedForDeletion = expectedPost;
        sm.deletePost(markedForDeletion);

        assertEquals(false, sm.getPosts().contains(markedForDeletion));
    }
}