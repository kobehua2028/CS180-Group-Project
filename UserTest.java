import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class UserTest {
    private SocialMediaDatabase sm = new SocialMediaDatabase("users.dat", "posts.dat");
    private User user1 = new User("Alice", "password123", "Hello, I'm Alice!", new ArrayList<>(), new ArrayList<>(), sm);

    private User user2 = new User("Bob", "securePass456", "Hello, I'm Bob!", new ArrayList<>(), new ArrayList<>(), sm);

    @Test
    public void testCreateUser() {
        assertNotNull(user1);
        assertEquals("Alice", user1.getUsername());
        assertEquals("password123", user1.getPassword());
        assertEquals("Hello, I'm Alice!", user1.getAboutMe());
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
        assertEquals("Updated about me.", user1.getAboutMe());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEqualsMethod() {
        User duplicateUser1 = new User("Alice", "password123", "Different bio", new ArrayList<>(), new ArrayList<>(), sm);
    }
}
