import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class UserTest {
    private SocialMediaDatabase sm = new SocialMediaDatabase("users.dat", "posts.dat");
    private User user1 = new User("Alice", "password123", "Hello, I'm Alice!",
            new ArrayList<>(), new ArrayList<>(), sm);

    private User user2 = new User("Bob", "securePass456", "Hello, I'm Bob!",
            new ArrayList<>(), new ArrayList<>(), sm);

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
        assertEquals(true, user1.equals(user3));
        assertEquals(false, user1.equals(new User("BobbyB", "password999",
                "Hello, I'm Alice!", new ArrayList<>(), new ArrayList<>(), sm)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEqualsMethod() {
        User duplicateUser1 = new User("Alice", "password123",
                "Different bio", new ArrayList<>(), new ArrayList<>(), sm);
    }

    public void run() {
        this.testCreateUser();
        this.testAddFriend();
        this.testAddExistingFriendThrowsException();
        this.testRemoveFriend();
        this.testRemoveNonExistentFriendThrowsException();
        this.testBlockUser();
        this.testBlockAlreadyBlockedUserThrowsException();
        this.testUnblockUser();
        this.testUnblockNonBlockedUserThrowsException();
        this.testChangeAboutMe();
        this.testEqualsMethod();
    }

    /*public void createPost(String title, String subtext) {
        Post post = new Post(this, title, subtext, new ArrayList<Comment>(), 0, 0, sm);
    }*/

    @Test
    public void testCreatePost() {
        user1.createPost("My first post!!!", "Hello everyone!");
        //createPost() creates a new Post object, which implicitly calls the writePost() method

        assertEquals(true, sm.getPosts().contains(new Post(user1, "My first post!!!", "Hello everyone!",
                new ArrayList<Comment>(), 0, 0 , sm)));
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
                new ArrayList<User>(), sm);
        user1.addFriend(newUser);

        assertEquals(newUser, user1.getFriendsList().get(0));
    }

    @Test
    public void testGetBlockedList() {
        User bleh = new User("Bread", "Breadman", "Bread can't talk, dummy", new ArrayList<User>(),
                new ArrayList<User>(), sm);
        user1.block(bleh);

        assertEquals(bleh, user1.getBlockedList().get(0));
    }
}