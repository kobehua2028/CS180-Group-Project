import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * CS180 Group Project
 * An test class for the User class
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author Sharma
 * @author Abdul
 * @version Nov 03, 2024
 */

public class UserTest {
    private SocialMediaDatabase sm;
    private User user1;
    private User user2;

    @Before
    public void setUp() throws Exception {
        sm = new SocialMediaDatabase("users.dat", "posts.dat");
        user1 = new User("Alice", "password123", "Hello, I'm Alice!",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), sm);
        user2 = new User("Bob", "securePass456", "Hello, I'm Bob!",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), sm);
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
        this.sm = null;
        this.user1 = null;
        this.user2 = null;
    }

    @Test
    public void testCreateUser() {
        String expectedUsername = "Alice";
        String expectedPassword = "password123";
        String expectedAboutMe = "Hello, I'm Alice!";

        assertNotNull(user1);
        assertEquals(expectedUsername, user1.getUsername());
        assertEquals(expectedPassword, user1.getPassword());
        assertEquals(expectedAboutMe, user1.getAboutMe());
    }

    @Test
    public void testAddFriend() {
        user1.addFriend(user2);
        assertTrue(user1.getFriendsList().contains(user2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddExistingFriendThrowsException() {
        user1.addFriend(user2);
        user1.addFriend(user2); // Should throw an exception
    }

    @Test
    public void testRemoveFriend() {
        user1.addFriend(user2);
        user1.removeFriend(user2);
        assertFalse(user1.getFriendsList().contains(user2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNonExistentFriendThrowsException() {
        user1.removeFriend(user2); // Should throw an exception
    }

    @Test
    public void testBlockUser() {
        user1.block(user2);
        assertTrue(user1.getBlockedList().contains(user2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBlockAlreadyBlockedUserThrowsException() {
        user1.block(user2);
        user1.block(user2); // Should throw an exception
    }

    @Test
    public void testUnblockUser() {
        user1.block(user2);
        user1.unblock(user2, sm);
        assertFalse(user1.getBlockedList().contains(user2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnblockNonBlockedUserThrowsException() {
        user1.unblock(user2, sm); // Should throw an exception
    }

    @Test
    public void testChangeAboutMe() {
        user1.changeAboutMe("Updated about me.");

        String expectedAboutMe = "Updated about me.";
        assertEquals(expectedAboutMe, user1.getAboutMe());
    }

    @Test
    public void testEquals() {
        User user3 = user1;
        assertEquals(user1, user3);
        assertNotEquals(user1, new User("BobbyB", "password999",
                "Hello, I'm Alice!", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), sm));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEqualsMethod() {
        User duplicateUser1 = new User("Alice", "password123",
                "Different bio", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), sm);
    }

    @Test
    public void testCreatePost() {
        user1.createPost("My first post!!!", "Hello everyone!");
        //createPost() creates a new Post object, which implicitly calls the writePost() method

        assertTrue(sm.getPosts().contains(new Post(user1, "My first post!!!", "Hello everyone!",
                new ArrayList<Comment>(), 0, 0, sm)));
    }

    @Test
    public void testGetUsername() {
        String expectedUsername1 = "Alice";
        String expectedUsername2 = "Bob";

        assertEquals(expectedUsername1, user1.getUsername());
        assertEquals(expectedUsername2, user2.getUsername());
    }

    @Test
    public void testGetPassword() {
        String expectedPassword1 = "password123";
        String expectedPassword2 = "securePass456";

        assertEquals(expectedPassword1, user1.getPassword());
        assertEquals(expectedPassword2, user2.getPassword());
    }


    @Test
    public void testGetAboutMe() {
        String expectedAboutMe1 = "Hello, I'm Alice!";
        String expectedAboutMe2 = "Hello, I'm Bob!";

        assertEquals(expectedAboutMe1, user1.getAboutMe());
        assertEquals(expectedAboutMe2, user2.getAboutMe());
    }

    @Test
    public void testGetFriendsList() {
        User newUser = new User("Fred", "Friddler", "Me fred", new ArrayList<User>(),
                new ArrayList<User>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), sm);
        user1.addFriend(newUser);

        assertEquals(newUser, user1.getFriendsList().get(0));
    }

    @Test
    public void testGetBlockedList() {
        User bleh = new User("Bread", "Breadman", "Bread can't talk, dummy",
                new ArrayList<User>(),
                new ArrayList<User>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), sm);
        user1.block(bleh);

        assertEquals(bleh, user1.getBlockedList().get(0));
    }

    @Test
    public void testHidePost() {
        user2.createPost("Hi", "Bob here");

        Post testPost = user2.getUserPosts().get(0);

        user1.hidePost(testPost);

        assertTrue(user1.getHiddenPosts().contains(testPost));
    }

    @Test
    public void testGetHiddenPost() {
        user2.createPost("Hi", "Bob here");

        Post testPost = user2.getUserPosts().get(0);

        user1.hidePost(testPost);

        assertEquals(testPost, user1.getHiddenPosts().get(0));
    }

    @Test
    public void testGetUserPosts() {
        user2.createPost("Hi", "Bob here");
        assertTrue(user2.getUserPosts().get(0).equals(new Post(user2, "Hi", "Bob here", new ArrayList<>(),
                0, 0, sm)));
    }

    @Test
    public void testGetLikedPosts() {
        user2.createPost("Hi", "Bob here");

        Post testPost = user2.getUserPosts().get(0);

        user1.addLikedPost(user2.getUserPosts().get(0));

        assertEquals(testPost, user1.getLikedPosts().get(0));
    }

    @Test
    public void testGetDislikedPosts() {
        user2.createPost("Hi", "Bob here");

        Post testPost = user2.getUserPosts().get(0);

        user1.addDislikedPost(user2.getUserPosts().get(0));

        assertEquals(testPost, user1.getDislikedPosts().get(0));
    }

    @Test
    public void testRemoveDislikedPost() {
        user2.createPost("Hi", "Bob here");

        Post testPost = user2.getUserPosts().get(0);

        user1.addDislikedPost(user2.getUserPosts().get(0));
        user1.removeDislikedPost(user2.getUserPosts().get(0));

        assertFalse(user1.getDislikedPosts().contains(user2.getUserPosts().get(0)));
    }

    @Test
    public void testRemoveLikedPost() {
        user2.createPost("Hi", "Bob here");

        Post testPost = user2.getUserPosts().get(0);

        user1.addLikedPost(user2.getUserPosts().get(0));
        user1.removeLikedPost(user2.getUserPosts().get(0));

        assertFalse(user1.getLikedPosts().contains(user2.getUserPosts().get(0)));
    }

    @Test
    public void testDeletePost() {
        user2.createPost("Hi", "Bob here");
        Post testPost = user2.getUserPosts().get(0);
        user2.deletePost(testPost);

        assertFalse(sm.getPosts().contains(testPost));

    }

    @Test
    public void testUnhidePost() {
        user2.createPost("Hi", "Bob here");
        Post testPost = user2.getUserPosts().get(0);
        user1.hidePost(testPost);
        assertEquals("Hi", (sm.findUser(user1.getUsername()).getHiddenPosts().get(0).getTitle()));

    }

}