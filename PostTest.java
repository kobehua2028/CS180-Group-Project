import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class PostTest {
    String testPostString = "PurduePete,Project Help,Hey guys, I'm having a hard time figuring out the project.";

    private SocialMediaDatabase sm = new SocialMediaDatabase("users,txt", "posts.txt","comments.txt");
    //private User testUser = new User(testUserString, sm);

    @Test
    public void testPost() {
        Post testPost = new Post(, "MyPurdue1234", );

        String expectedAuthor = "PurduePete,MyPurdue1234,Hey guys it's me, Pete from Purdue!\n";
        String expectedTitle = "Project Help";
        String expectedSubtext = "Hey guys, I'm having a hard time figuring out the project.\n";

        assertEquals(expectedAuthor, testPost.getAuthor());
        assertEquals(expectedTitle, testPost.getTitle());
        assertEquals(expectedSubtext, testPost.getSubtext());
    }

}