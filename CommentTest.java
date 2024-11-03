import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class CommentTest {
    private User testUser;
    private Post testPost;
    private SocialMediaDatabase sm;
    private Comment testComment;

    @BeforeEach
    void setUp() {
        sm = new SocialMediaDatabase("users.txt", "posts.txt");
        testUser = new User("PurduePete",
                "password123",
                "asd",
                new ArrayList<>(),
                new ArrayList<>(),
                sm);
        testPost = new Post(testUser,
                "Test Post",
                "This is a post",
                new ArrayList<>(),
                4,
                2,
                sm);
        testComment = new Comment(testUser, "This is a post", 4, 2, testPost, sm);
    }

    @Test
    void testGetters() {
        //Test all getter methods
        assertEquals("This is a post", testComment.getText());
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
