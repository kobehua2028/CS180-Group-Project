import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SocialMediaServerTest {
    private SocialMediaDatabase sm;
    private User testUser;
    private Post testPost;
    private SocialMediaServer testServer;


    @Before
    public void setUp() throws Exception {

        sm = new SocialMediaDatabase("users.dat", "posts.dat");

        try {
            testServer = new SocialMediaServer(null);
            testServer.setSM(sm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        testUser = new User("Dunsmore","CS180istheBest",
                "I teach CS180", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), sm);
        testPost = new Post(testUser, "Purdue CS180",
                "Purdue CS 180 is the best CS class", new ArrayList<Comment>(), 0, 0, sm);




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
        this.testUser = null;
        this.testPost = null;
        sm = null;
        testServer = null;
    }

    @Test
    public void testLogIn() {
        boolean output1 = testServer.login("Dunsmore", "CS180istheBest");
        boolean output2 = testServer.login("Dunsmore", "CS180istheWorst");
        boolean output3 = testServer.login("Hubert", "NubertInTheTrash");


        assertTrue(output1);
        assertFalse(output2);
        assertFalse(output3);
    }

    @Test
    public void testCreateUser() {
        boolean output = testServer.createUser("PurduePete", "LayerCake", "HammerUp!");

        assertTrue(output);
        assertEquals("PurduePete", sm.getUsers().get(sm.getUsers().size() - 1).getUsername());
    }

    @Test
    public void testDeleteUser() {
        boolean output3 = testServer.deleteUser("Dunsmore", "PurduePete");
        boolean output2 = testServer.deleteUser("PurduePete", "Brutus");
        boolean output1 = testServer.deleteUser("Dunsmore", "Dunsmore");

        assertFalse(output3);
        assertFalse(output2);
        assertTrue(output1);
        assertFalse(sm.getUsers().contains(testUser));
    }

    //INCOMPLETE
    @Test
    public void testDisplayPosts() {
        ArrayList<String> expectedOutput = new ArrayList<String>();

    }

    @Test
    public void testSearchUser() {
        boolean output = testServer.searchUser("Dunsmore");
        boolean output2 = testServer.searchUser("PurdueBeef");

        assertTrue(output);
        assertFalse(output2);
    }

    @Test
    public void testAddFriend() {
        testServer.createUser("Purdue","petyrBaelish"," Ham");

        boolean addingNullFriend = testServer.addFriend("Dunsmore", "Nobody");
        boolean addingFriendSuccess = testServer.addFriend("Dunsmore", "Purdue");
        boolean alreadyFriend = testServer.addFriend("Dunsmore", "Purdue");
        //One for adding a freind that doesnt exist
        //one for adding a friend without logging in
        //one for adding a friend
        //one for adding a non-friend



        assertFalse(addingNullFriend);
        assertTrue(addingFriendSuccess);
        assertFalse(alreadyFriend);
    }

    @Test
    public void testDeleteFriend() {
        testServer.createUser("Purdue","petyrBaelish"," Ham");

        testServer.addFriend("Dunsmore", "Purdue");
        boolean removed = testServer.deleteFriend("Dunsmore", "Purdue");
        boolean removeNonFriend=  testServer.deleteFriend("Dunsmore", "Sandwich");

        assertTrue(removed);
        assertFalse(removeNonFriend);
    }

    @Test
    public void testBlockUser() {
        testServer.createUser("Purdue","petyrBaelish"," Ham");

        boolean blocked = testServer.blockUser("Dunsmore", "Purdue");
        boolean blockedNonExistent = testServer.blockUser("Dunsmore", "Beef");

        assertTrue(blocked);
        assertFalse(blockedNonExistent);
    }

    @Test
    public void testUnblockUser() {
        testServer.createUser("Purdue","petyrBaelish"," Ham");

        testServer.blockUser("Dunsmore", "Purdue");
        boolean unblocked = testServer.unblockUser("Dunsmore", "Purdue");
        boolean unblockedNonExistent = testServer.unblockUser("Dunsmore", "Beef");

        assertTrue(unblocked);
        assertFalse(unblockedNonExistent);
    }

    @Test
    public void testCreatePost() {
        boolean postCreated = testServer.createPost("Dunsmore", "Congress Banned the ArrayList!",
                "Eh, linked lists are better, anyway...");

        assertTrue(postCreated);
        assertEquals("Congress Banned the ArrayList!",
                sm.getPosts().get(sm.getPosts().size() - 1).getTitle());
    }

    @Test
    public void testHidePost() {
        testServer.createUser("Purdue","petyrBaelish"," Ham");

        testServer.createPost("Purdue", "Congress Banned the ArrayList!",
                "Eh, linked lists are better, anyway...");
        Post testPost = sm.getPosts().get(sm.getPosts().size() - 1);

        boolean postHidden = testServer.hidePost("Dunsmore", "Congress Banned the ArrayList!");

        assertTrue(postHidden);
        assertTrue(sm.getUsers().get(0).getHiddenPosts().contains(testPost));
    }

    @Test
    public void testUnhidePost() {
        testServer.createPost("Dunsmore", "Congress Banned the ArrayList!",
                "Eh, linked lists are better, anyway...");
        Post testPost = sm.getPosts().get(sm.getPosts().size() - 1);

        testServer.hidePost("Dunsmore", "Congress Banned the ArrayList!");
        boolean postUnhidden = testServer.unhidePost("Dunsmore", "Congress Banned the ArrayList!");

        assertTrue(postUnhidden);
        assertTrue(!(sm.getUsers().get(0).getHiddenPosts().contains(testPost)));
    }

    @Test
    public void testDeletePost() {
        testServer.createUser("Purdue","petyrBaelish"," Ham");

        for (int i = 0; i < sm.getPosts().size(); i++)
            System.out.println(sm.getPosts().get(i).getTitle());

        testServer.createPost("Dunsmore", "Congress Banned the ArrayList!",
                "Eh, linked lists are better, anyway...");


        boolean postDeleted = testServer.deletePost("Dunsmore", "Congress Banned the ArrayList!");

        assertTrue(postDeleted);

        assertEquals(null, (sm.findPost("Congress Banned the ArrayList!")));
    }

    @Test
    public void testCreateComment() {
        testServer.createPost("Dunsmore", "Congress Banned the ArrayList!",
                "Eh, linked lists are better, anyway...");
        boolean commentCreated = testServer.createComment("Congress Banned the ArrayList!",
                "Dunsmore", "or just break the law lmao");

        assertTrue(commentCreated);
        assertEquals("or just break the law lmao",
                sm.getPosts().get(sm.getPosts().size() - 1).getComments().get(0).getText());
    }

    @Test
    public void testLikePost() {
        testServer.createPost("Dunsmore", "Congress Banned the ArrayList!",
                "Eh, linked lists are better, anyway...");

        boolean postLiked = testServer.likePost("Dunsmore", "Congress Banned the ArrayList!");

        boolean likeTwice = testServer.likePost("Dunsmore", "Congress Banned the ArrayList!");

        assertTrue(postLiked);
        assertEquals(1, sm.getPosts().get(sm.getPosts().size() - 1).getLikes());
        assertFalse(likeTwice);
    }

    @Test
    public void testUnlikePost() {
        testServer.createPost("Dunsmore", "Congress Banned the ArrayList!",
                "Eh, linked lists are better, anyway...");

        testServer.likePost("Dunsmore", "Congress Banned the ArrayList!");
        boolean postUnliked = testServer.unlikePost("Dunsmore", "Congress Banned the ArrayList!");

        boolean unlikedTwice = testServer.unlikePost("Dunsmore", "Congress Banned the ArrayList!");

        assertTrue(postUnliked);
        assertFalse(unlikedTwice);
        assertEquals(0, sm.getPosts().get(sm.getPosts().size() - 1).getLikes());
    }

    @Test
    public void testDislikePost() {
        testServer.createPost("Dunsmore", "Congress Banned the ArrayList!",
                "Eh, linked lists are better, anyway...");

        boolean postDisliked = testServer.dislikePost("Dunsmore", "Congress Banned the ArrayList!");

        boolean dislikeTwice = testServer.dislikePost("Dunsmore", "Congress Banned the ArrayList!");

        assertTrue(postDisliked);
        assertEquals(1, sm.getPosts().get(sm.getPosts().size() - 1).getDislikes());
        assertFalse(dislikeTwice);
    }

    @Test
    public void testUndislikePost() {
        testServer.createPost("Dunsmore", "Congress Banned the ArrayList!",
                "Eh, linked lists are better, anyway...");

        testServer.dislikePost("Dunsmore", "Congress Banned the ArrayList!");
        boolean postUndisliked = testServer.undislikePost("Dunsmore", "Congress Banned the ArrayList!");

        boolean undislikedTwice = testServer.undislikePost("Dunsmore", "Congress Banned the ArrayList!");

        assertTrue(postUndisliked);
        assertFalse(undislikedTwice);
        assertEquals(0, sm.getPosts().get(sm.getPosts().size() - 1).getDislikes());
    }
}