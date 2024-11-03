import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class PostTest {
    String testPostString = "PurduePete,Project Help,Hey guys, I'm having a hard time figuring out the project.";

    private SocialMediaDatabase sm = new SocialMediaDatabase("users,txt", "posts.txt");

    User test = new User("PurduePete","lYER4CAK", "AboutMe", new ArrayList<User>(),
            new ArrayList<User>(), sm);
    Post testPost = new Post(test, "Test case", "Lorem Ispum", new ArrayList<Comment>(),
            0, 0, sm);



    @Test
    public void testPost() {

        String expectedUsername = "PurduePete";
        String expectedPassword = "lYER4CAK";
        String expectedAboutMe = "AboutMe";
        String expectedTitle = "Test case";
        String expectedSubtext = "Lorem Ispum";


        assertEquals(expectedUsername, testPost.getAuthor().getUsername());
        assertEquals(expectedPassword, testPost.getAuthor().getPassword());
        assertEquals(expectedAboutMe, testPost.getAuthor().getAboutMe());

        assertEquals(expectedTitle, testPost.getTitle());
        assertEquals(expectedSubtext, testPost.getSubtext());
    }

    @Test
    public void testGetAuthor() {
        String expectedUsername = "PurduePete";
        String expectedPassword = "lYER4CAK";
        String expectedAboutMe = "AboutMe";

        assertEquals(expectedUsername, testPost.getAuthor().getUsername());
        assertEquals(expectedPassword, testPost.getAuthor().getPassword());
        assertEquals(expectedAboutMe, testPost.getAuthor().getAboutMe());
    }

    @Test
    public void testGetTitle() {
        String expectedTitle = "Test case";

        assertEquals(expectedTitle, testPost.getTitle());
    }

    @Test
    public void testGetSubtext() {
        String expectedSubtext = "Lorem Ispum";

        assertEquals(expectedSubtext, testPost.getSubtext());
    }

    @Test
    public void testGetComments() {
        Comment testCom = new Comment(test, "Hellowrld", 0, 0, testPost, sm);

        String expectedCommentText = "Hellowrld";

        assertEquals(expectedCommentText, testPost.getComments().get(0).getText());
    }


    @Test
    public void testAddComment() {
        Comment testCom = new Comment(test, "HeyThere", 0, 0, testPost, sm);
        //addComment() is called implicitly within the Comment Constructor

        String expectedCommentText = "HeyThere";

        assertEquals(expectedCommentText, testPost.getComments().get(0).getText());
    }

    @Test
    public void testGetLikes() {
        int expectedLikes = 0;
        assertEquals(expectedLikes, testPost.getLikes());
    }

    @Test
    public void testGetDislikes() {
        int expectedDislikes = 0;
        assertEquals(expectedDislikes, testPost.getDislikes());
    }

    @Test
    public void testIncrementLikes() {
        testPost.incrementLikes();

        assertEquals(1, testPost.getLikes());
    }

    @Test
    public void testIncrementDislikes() {

        testPost.incrementDislikes();

        assertEquals(1, testPost.getDislikes());
    }

    @Test
    public void testEquals() {

        assertEquals(true, testPost.equals(new Post(test, "Test case", "Lorem Ispum", new ArrayList<Comment>(),
                0, 1, sm)));
        assertEquals(false, testPost.equals(new Post(test, "Test casen't",
                "Ispum Lor", new ArrayList<Comment>(), 0, 1, sm)));
    }

    @Test
    public void testCreateComment() {
        testPost.createComment(test, "That's my post!");

        String expectedCommentText = "That's my post!";

        assertEquals(expectedCommentText, testPost.getComments().get(0).getText());
    }
}