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

    @Test
    public void testCreateUser() {
        sm.createUser("Professor Dunsmore","CS180istheBest", "I teach CS180");

        ArrayList<User> expected = new ArrayList<>();
        expected.add(new User("Professor Dunsmore","CS180istheBest",
                "I teach CS180", new ArrayList<>(), new ArrayList<>(), sm));
        assertEquals(expected, sm.getUsers());
    }

    @Test
    public void testReadUsers() {
        sm.createUser("PurduePete", "MyPurdue1234", "Hey guys it's me, Pete from Purdue!");
        sm.readUsers();
        ArrayList<User> expected = new ArrayList<>();
        expected.add(new User("Professor Dunsmore","CS180istheBest",
                "I teach CS180", new ArrayList<>(), new ArrayList<>(), sm));
        expected.add(new User("PurduePete", "MyPurdue1234",
                "Hey guys it's me, Pete from Purdue!", new ArrayList<>(), new ArrayList<>(), sm));
        assertEquals(expected, sm.getUsers());
    }

    @Test
    public void testReadPosts() {
        sm.readUsers();
        sm.readPosts();

        ArrayList<Post> expected = new ArrayList<>();
        expected.add(new Post
                ("PurduePete,Project Help,Hey guys, I'm having a hard time figuring out the project.", sm));

        assertEquals(true, sm.getPosts().get(0).equals(expected.get(0)));
    }


}
