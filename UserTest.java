import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.Assert.assertEquals;

public class UserTest {
    String testUserString = "PurduePete,MyPurdue1234,Hey guys it's me, Pete from Purdue!";
    String testPostString = "PurduePete,Project Help,Hey guys, I'm having a hard time figuring out the project.";

    private SocialMediaDatabase sm = new SocialMediaDatabase("users,txt", "posts.txt","comments.txt");
    private User testUser = new User(testUserString, sm);
    private Post testPost = new Post(testPostString, sm);

    @Test
    public void testUser() {
        String input = "PurduePete,MyPurdue1234,Hey guys it's me, Pete from Purdue!";
        SocialMediaDatabase sm = new SocialMediaDatabase("users,txt", "posts.txt","comments.txt");
        User testUser = new User(testUserString, sm);

        String expectedUsername = "PurduePete";
        String expectedPassword = "MyPurdue1234";
        String expectedAboutMe = "Hey guys it's me, Pete from Purdue!";

        assertEquals(expectedUsername, testUser.getUsername());
        assertEquals(expectedPassword, testUser.getPassword());
        assertEquals(expectedAboutMe, testUser.getAboutMe());
    }



}