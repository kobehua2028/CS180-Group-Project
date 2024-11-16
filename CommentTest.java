import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
/**
 * CS180 Group Project
 * A class for testing the functionaly of the Comment class
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author MichaelIkriannikov
 * @author Abdul
 * @version Nov 03, 2024
 */
class CommentTest {
    private User testUser;
    private Post testPost;
    private SocialMediaDatabase sm;
    private Comment testComment;

    @BeforeEach
    public void setUp() {
        sm = new SocialMediaDatabase("users.dat", "posts.dat");
        testUser = new User("PurduePete", "password123", "asd", new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), sm);
        testPost = new Post(testUser, "Test Post", "This is a post", new ArrayList<>(), 4,
                2, sm);
        testComment = new Comment(testUser, "This is a comment", 4, 2, testPost, sm);
    }

    @AfterEach
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
        this.sm = null;
        this.testUser = null;
        this.testPost = null;
        this.testComment = null;
    }

    @Test
    void testGetters() {
        //Test all getter methods
        assertEquals("This is a comment", testComment.getText());
        assertEquals(4, testComment.getLikes());
        assertEquals(2, testComment.getDislikes());
        assertEquals(testUser, testComment.getAuthor());
        assertEquals(testPost, testComment.getPost());
    }

    @Test
    void testIncrementLikes() {
        //Test incrementing likes
        int initialLikes = testComment.getLikes();
        testComment.incrementLikes();
        assertEquals(initialLikes + 1, testComment.getLikes());
    }

    @Test
    void testIncrementDislikes() {
        //Test incrementing dislikes
        int initialDislikes = testComment.getDislikes();
        testComment.incrementDislikes();
        assertEquals(initialDislikes + 1, testComment.getDislikes());
    }
}