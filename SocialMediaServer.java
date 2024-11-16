import java.net.*;
import java.io.*;
import java.util.*;

public class SocialMediaServer implements Runnable {
    private Socket socket;
    private static SocialMediaDatabase sm = new SocialMediaDatabase("users.dat", "posts.dat");

    public SocialMediaServer(Socket socket) {
        this.socket = socket;
        sm.readUsers();
        sm.readPosts();
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("SOCIAL MEDIA SERVER STARTED");
        while (true) {
            Socket socket = serverSocket.accept();
            SocialMediaServer sm = new SocialMediaServer(socket);
            new Thread(sm).start();
        }
    }

    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(outputStream);
            String line = br.readLine();
            while (line != null) {
                String[] command = line.split("`");

                System.out.print("Command received from a client: ");
                System.out.print(Arrays.toString(command) + "\n");

                boolean success;
                switch (command[0]) {
                    case "ECHO" -> {
                        pw.println();
                        pw.flush();
                    }
                    case "LOGIN" -> {
                        if(command.length != 3) {
                            pw.write("INVALID_LOGIN_ATTEMPT\n");
                        } else {
                            success = login(command[1], command[2]);
                            if (success) {
                                pw.write("SUCCESS\n");
                            } else {
                                pw.write("FAIL\n");
                            }
                        }
                    }
                    case "REGISTER_USER" -> {

                    }
                    case "LOGOUT" -> {

                    }
                    case "DELETE_ACCOUNT" -> {

                    }
                    case "DISPLAY_POSTS" -> {

                    }
                    case "DISPLAY_COMMENTS" -> {

                    }
                    case "DISPLAY_PROFILE" -> {

                    }
                    case "ADD_FRIEND" -> {

                    }
                    case "DELETE_FRIEND" -> {

                    }
                    case "BLOCK_USER" -> {

                    }
                    case "UNBLOCK_USER" -> {

                    }
                    case "HIDE_POST" -> {

                    }
                    case "UNHIDE_POST" -> {

                    }
                    case "CREATE_POST" -> {

                    }
                    case "DELETE_POST" -> {

                    }
                    case "CREATE_COMMENT" -> {

                    }
                    case "DELETE_COMMENT" -> {

                    }
                    case "LIKE_POST" -> {

                    }
                    case "UNLIKE_POST" -> {

                    }
                    case "DISLIKE_POST" -> {

                    }
                    case "UNDISLIKE_POST" -> {

                    }
                    case "LIKE_COMMENT" -> {

                    }
                    case "UNLIKE_COMMENT" -> {

                    }
                    case "DISLIKE_COMMENT" -> {

                    }
                    case "UNDISLIKE_COMMENT" -> {

                    }
                    case "HIDE_COMMENT" -> {

                    }
                    default -> {
                        // replace with error Message
                        System.out.println("UNKNOWN_COMMAND");
                        return;
                    }
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean login(String username, String password) {
        sm.readUsers();
        User loginUser = sm.findUser(username);
        if (loginUser != null) {
            if (loginUser.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public boolean createUser(String username, String password, String aboutMe) {
        try {
            sm.createUser(username, password, aboutMe);
            if (sm.findUser(username) != null) {
                return true;
            }
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean deleteUser(String username) {
        User deleteUser = sm.findUser(username);
        if (deleteUser != null) {
            sm.getUsers().remove(deleteUser);
            return true;
        } else {
            return false;
        }
    }

    public String[] displayPosts(String username) {
        User user = sm.findUser(username);
        String[] postStrings = new String[5];
        if (user == null) {
            return postStrings;
        }
        Random random = new Random();
        ArrayList<Integer> postNumbers = new ArrayList<>();
        for (int i = 0; i < 5;) {
            int j = random.nextInt(0,sm.getPosts().size() - 1);
            Post post = sm.getPosts().get(j);
            if (!user.getHiddenPosts().contains(post) && !postNumbers.contains(j)) {
                String postString = post.getTitle() + "`" + post.getSubtext() + "`" + post.getAuthor() + "`" +
                        post.getComments().size() + "`" + post.getLikes() + "`" + post.getDislikes();
                postStrings[i] = postString;
                i++;
            }
            postNumbers.add(j);
        }
        return postStrings;
    }

    public ArrayList<String> displayComments(String postTitle) {
        Post post = sm.findPost(postTitle);
        ArrayList<String> comments = new ArrayList<>();
        if (post == null) {
            return comments;
        }
        for (int i = 0; i < post.getComments().size(); i++) {
            Comment comment = post.getComments().get(i);
            String commentString = comment.getText() + "`" + comment.getAuthor() + "`" +
                    comment.getLikes() + "`" + comment.getDislikes();
            comments.add(commentString);
        }
        return comments;
    }

    public ArrayList<String> displayProfile(String viewerUsername, String profileUsername) {
        ArrayList<String> profileInfo = new ArrayList<>();
        User viewerUser = sm.findUser(viewerUsername);
        User profileUser = sm.findUser(profileUsername);
        if (viewerUser == null || profileUser == null) {
            return profileInfo;
        }
        if (profileUser.equals(viewerUser)) {
            profileInfo.add(profileUsername);
            String friendList = "FRIENDS_LIST`";
            for (int i = 0; i < profileUser.getFriendsList().size(); i++) {
                friendList += profileUser.getFriendsList().get(i).getUsername() + "`";
            }
            profileInfo.add(friendList.substring(0, friendList.length() - 1));

            String blockList = "BLOCKED_LIST`";
            for (int i = 0; i < profileUser.getBlockedList().size(); i++) {
                blockList += profileUser.getBlockedList().get(i).getUsername() + "`";
            }
            profileInfo.add(blockList.substring(0, blockList.length() - 1));

            String postList = "POSTS_LIST`";
            for (int i = 0; i < profileUser.getUserPosts().size(); i++) {
                postList += profileUser.getUserPosts().get(i).getTitle() + "`";
            }
            profileInfo.add(postList.substring(0, postList.length() - 1));
            String aboutme = "ABOUT_ME`" + profileUser.getAboutMe();
            profileInfo.add(aboutme);

        } else {
            profileInfo.add(profileUsername);
            String friendList = "FRIENDS_LIST`";
            for (int i = 0; i < profileUser.getFriendsList().size(); i++) {
                friendList += profileUser.getFriendsList().get(i).getUsername() + "`";
            }
            profileInfo.add(friendList.substring(0, friendList.length() - 1));

            String postList = "POSTS_LIST`";
            for (int i = 0; i < profileUser.getUserPosts().size(); i++) {
                postList += profileUser.getUserPosts().get(i).getTitle() + "`";
            }
            profileInfo.add(postList.substring(0, postList.length() - 1));
            String aboutme = "ABOUT_ME`" + profileUser.getAboutMe();
            profileInfo.add(aboutme);
        }
        return profileInfo;
    }

    public boolean addFriend(String username, String friendUsername) {
        User user = sm.findUser(username);
        User friend = sm.findUser(friendUsername);
        if (user == null || friend == null) {
            return false;
        } else if (user.getFriendsList().contains(friend) || user.equals(friend)) {
            return false;
        } else if (user.getBlockedList().contains(friend) || friend.getBlockedList().contains(user)) {
            return false;
        } else {
            user.addFriend(friend);
            friend.addFriend(user);
            return true;
        }
    }

    public boolean deleteFriend(String username, String friendUsername) {
        User user = sm.findUser(username);
        User friend = sm.findUser(friendUsername);
        if (user == null || friend == null) {
            return false;
        } else if (!user.getFriendsList().contains(friend) || !user.equals(friend)) {
            return false;
        } else {
            user.removeFriend(friend);
            friend.removeFriend(user);
            return true;
        }
    }

    public boolean blockUser(String username, String blockUsername) {
        User user = sm.findUser(username);
        User block = sm.findUser(blockUsername);
        if (user == null || block == null) {
            return false;
        } else if (user.getBlockedList().contains(block) || user.equals(block)) {
            return false;
        } else {
            user.block(block);
            if (user.getFriendsList().contains(block)) {
                deleteFriend(username, blockUsername);
            }
            return true;
        }
    }

    public boolean unblockUser(String username, String blockUsername) {
        User user = sm.findUser(username);
        User block = sm.findUser(blockUsername);
        if (user == null || block == null) {
            return false;
        } else if (!user.getBlockedList().contains(block) || !user.equals(block)) {
            return false;
        } else {
            user.unblock(block, sm);
            return true;
        }
    }

    public boolean hidePost(String username, String postTitle) {
        User user = sm.findUser(username);
        Post post = sm.findPost(postTitle);

        if (user == null || post == null) {
            return false;
        } else if (user.getUserPosts().contains(post) || user.getHiddenPosts().contains(post)) {
            return false;
        } else {
            user.hidePost(post);
            return true;
        }
    }

    public boolean unhidePost(String username, String postTitle) {
        User user = sm.findUser(username);
        Post post = sm.findPost(postTitle);

        if (user == null || post == null) {
            return false;
        } else if (!user.getHiddenPosts().contains(post)) {
            return false;
        } else {
            user.hidePost(post);
            return true;
        }
    }

    public boolean createPost(String authorUsername, String title, String subtext) {
        try {
            User user = sm.findUser(authorUsername);
            if (user == null) {
                throw new IllegalArgumentException("No Post Author");
            }
            user.createPost(title, subtext);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean deletePost(String username, String postTitle) {
        User user = sm.findUser(username);
        Post post = sm.findPost(postTitle);
        if (user == null || post == null) {
            return false;
        } else if (!user.getUserPosts().contains(post) || !post.getAuthor().equals(user)) {
            return false;
        } else {
            user.deletePost(post);
            return true;
        }
    }

    public boolean createComment(String postTitle, String username, String comment) {
        Post post = sm.findPost(postTitle);
        User user = sm.findUser(username);
        if (user == null || post == null || comment.isEmpty() || comment == null) {
            return false;
        } else {
            post.createComment(user, comment);
            return true;
        }
    }

    














}
