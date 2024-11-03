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
    private ArrayList<User> expected = new ArrayList<>();

    @Test
    public void testCreateUser() {
        sm.createUser("Dunsmore","CS180istheBest", "I teach CS180");
        expected.add(expectedUser);
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
            sm.getUsers().getFirst().createPost("Purdue CS180", "Purdue CS 180 is the best CS class");
        }
        Post foundPost = sm.findPost("Purdue CS180");
        assertEquals(expectedPost, foundPost);
    }

    @Test
    public void testReadUsers() {
        sm.createUser("PurduePete", "MyPurdue1234", "Hey guys it's me, Pete from Purdue!");
        sm.readUsers();
        ArrayList<User> expected = new ArrayList<>();
        expected.add(new User("Dunsmore","CS180istheBest",
                "I teach CS180", new ArrayList<>(), new ArrayList<>(), sm));
        expected.add(new User("PurduePete", "MyPurdue1234",
                "Hey guys it's me, Pete from Purdue!", new ArrayList<>(), new ArrayList<>(), sm));
        assertEquals(expected, sm.getUsers());
    }

}
