import org.junit.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SocialMediaDatabaseTest {
    private SocialMediaDatabase sm = new SocialMediaDatabase("users.txt", "posts.txt","comments.txt");

    @Test
    public void testReadUsers() {
        sm.readUsers();

        ArrayList<User> expected = new ArrayList<>();
        expected.add(new User("PurduePete,MyPurdue1234,Hey guys it's me, Pete from Purdue!", sm));

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