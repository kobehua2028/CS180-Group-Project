import java.net.*;
import java.io.*;
import java.util.*;

public class SocialMediaServer implements Runnable {
    private Socket socket;
    private static SocialMediaDatabase sm = new SocialMediaDatabase("users.dat", "posts.dat");
    private static Object lock = new Object();

    public SocialMediaServer(Socket socket) {
        this.socket = socket;
        sm.readUsers();
        sm.readPosts();
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("SOCIAL MEDIA SERVER STARTED");
//        System.out.println(sm.createUser("Bob", "Password5", "sdgsdg").getUsername());

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
                        pw.println("SUCCESS");
                        pw.flush();
                    }
                    case "LOGIN" -> {
                        if (command.length != 3) {
                            pw.println("FAIL");
                            pw.flush();
                        } else {
                            success = login(command[1], command[2]);
                            System.out.println(success + command[1] + command[2]);
                            if (success) {
                                pw.println("SUCCESS");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "REGISTER_USER" -> {
                        if (command.length != 4) {
                            pw.println("FAIL");
                            pw.flush();
                        } else {
                            success = createUser(command[1], command[2], command[3]);
                            System.out.println(success + command[1] + command[2]);
                            if (success) {
                                pw.println("SUCCESS");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "LOGOUT" -> {
                        // might need to be something different
                        br.close();
                        pw.close();
                    }
                    case "DELETE_ACCOUNT" -> {
                        if (command.length != 3) {
                            pw.println("FAIL");
                            pw.flush();
                        } else {
                            success = deleteUser(command[1], command[2]);
                            if (success) {
                                pw.println("SUCCESS");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "DISPLAY_POSTS" -> {
                        if (command.length != 2) {
                            pw.println("FAIL");
                            pw.flush();
                        } else {
                            ArrayList<String> posts = displayPosts(command[1]);
                            success = (!posts.isEmpty());
                            if (success) {
                                pw.println("SUCCESS_POST");
                                for (String post : posts) {
                                    pw.println(post);
                                    pw.flush();
                                }
                                pw.println("ALL_POSTS_SENT");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "DISPLAY_COMMENTS" -> {
                        if (command.length != 2) {
                            pw.println("FAIL");
                        } else {
                            ArrayList<String> comments = displayComments(command[1]);
                            success = (!comments.isEmpty());
                            if (success) {
                                pw.println("SUCCESS");
                                for (String comment : comments) {
                                    pw.println(comment);
                                    pw.flush();
                                }
                                pw.println("ALL_COMMENTS_SENT");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "DISPLAY_PROFILE" -> {
                        if (command.length != 3) {
                            pw.println("FAIL");
                        } else {
                            ArrayList<String> profileInfo = displayProfile(command[1], command[2]);
                            success = (!profileInfo.isEmpty());
                            if (success) {
                                pw.println("SUCCESS");
                                for (String info : profileInfo) {
                                    pw.println(info);
                                    pw.flush();
                                }
                                pw.println("ALL_PROFILE_INFO_SENT");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "SEARCH_USER" -> {
                        if (command.length != 2) {
                            pw.println("FAIL");
                        } else {
                            success = searchUser(command[1]);
                            if (success) {
                                pw.println("SUCCESS");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "ADD_FRIEND" -> {
                        if (command.length != 3) {
                            pw.println("FAIL");
                        } else {
                            success = addFriend(command[1], command[2]);
                            if (success) {
                                pw.println("SUCCESS");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "DELETE_FRIEND" -> {
                        if (command.length != 3) {
                            pw.println("FAIL");
                        } else {
                            success = deleteFriend(command[1], command[2]);
                            if (success) {
                                pw.println("SUCCESS");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "BLOCK_USER" -> {
                        if (command.length != 3) {
                            pw.println("FAIL");
                        } else {
                            success = blockUser(command[1], command[2]);
                            if (success) {
                                pw.println("SUCCESS");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "UNBLOCK_USER" -> {
                        if (command.length != 3) {
                            pw.println("FAIL");
                        } else {
                            success = unblockUser(command[1], command[2]);
                            if (success) {
                                pw.println("SUCCESS");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "HIDE_POST" -> {
                        if (command.length != 3) {
                            pw.println("FAIL");
                        } else {
                            success = hidePost(command[1], command[2]);
                            if (success) {
                                pw.println("SUCCESS");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "UNHIDE_POST" -> {
                        if (command.length != 3) {
                            pw.println("FAIL");
                        } else {
                            success = unhidePost(command[1], command[2]);
                            if (success) {
                                pw.println("SUCCESS");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "CREATE_POST" -> {
                        if (command.length != 4) {
                            pw.println("FAIL");
                        } else {
                            success = createPost(command[1], command[2], command[3]);
                            if (success) {
                                pw.println("SUCCESS");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "DELETE_POST" -> {
                        if (command.length != 3) {
                            pw.println("FAIL");
                        } else {
                            success = deletePost(command[1], command[2]);
                            if (success) {
                                pw.println("SUCCESS");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "CREATE_COMMENT" -> {
                        if (command.length != 4) {
                            pw.println("FAIL");
                        } else {
                            success = createComment(command[1], command[2], command[3]);
                            if (success) {
                                pw.println("SUCCESS");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "DELETE_COMMENT" -> {
                        if (command.length != 4) {
                            pw.println("FAIL");
                        } else {
                            success = deleteComment(command[1], command[2], command[3]);
                            if (success) {
                                pw.println("SUCCESS");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "LIKE_POST" -> {
                        if (command.length != 3) {
                            pw.println("FAIL");
                        } else {
                            success = likePost(command[1], command[2]);
                            if (success) {
                                pw.println("SUCCESS");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "UNLIKE_POST" -> {
                        if (command.length != 3) {
                            pw.println("FAIL");
                        } else {
                            success = unlikePost(command[1], command[2]);
                            if (success) {
                                pw.println("SUCCESS");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "DISLIKE_POST" -> {
                        if (command.length != 3) {
                            pw.println("FAIL");
                        } else {
                            success = dislikePost(command[1], command[2]);
                            if (success) {
                                pw.println("SUCCESS");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "UNDISLIKE_POST" -> {
                        if (command.length != 3) {
                            pw.println("FAIL");
                        } else {
                            success = undislikePost(command[1], command[2]);
                            if (success) {
                                pw.println("SUCCESS");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "LIKE_COMMENT" -> {
                        if (command.length != 4) {
                            pw.println("FAIL");
                        } else {
                            success = likeComment(command[1], command[2], command[3]);
                            if (success) {
                                pw.println("SUCCESS");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "UNLIKE_COMMENT" -> {
                        if (command.length != 4) {
                            pw.println("FAIL");
                        } else {
                            success = unlikeComment(command[1], command[2], command[3]);
                            if (success) {
                                pw.println("SUCCESS");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "DISLIKE_COMMENT" -> {
                        if (command.length != 4) {
                            pw.println("FAIL");
                        } else {
                            success = dislikeComment(command[1], command[2], command[3]);
                            if (success) {
                                pw.println("SUCCESS");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    case "UNDISLIKE_COMMENT" -> {
                        if (command.length != 4) {
                            pw.println("FAIL");
                        } else {
                            success = undislikeComment(command[1], command[2], command[3]);
                            if (success) {
                                pw.println("SUCCESS");
                                pw.flush();
                            } else {
                                pw.println("FAIL");
                                pw.flush();
                            }
                        }
                    }
                    default -> {
                        // replace with error Message
                        System.out.println("UNKNOWN_COMMAND");
                        return;
                    }
                }
                try {
                    line = br.readLine();
                } catch (IOException e) {
                    break;
                }
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

    public synchronized boolean createUser(String username, String password, String aboutMe) {
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

    public synchronized boolean deleteUser(String username, String deletedUsername) {
        User deleteUser = sm.findUser(username);
        User deletedAccount = sm.findUser(deletedUsername);
        if (deleteUser != null && deletedAccount.equals(deleteUser)) {
            sm.deleteUser(deleteUser);
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<String> displayPosts(String username) {
        User user = sm.findUser(username);
        ArrayList<String> postStrings = new ArrayList<>();
        if (user == null) {
            return postStrings;
        }
        int postLimit = Integer.min(5, sm.getPosts().size());
        int i = 0;
        int postsShown = 0;
        while (postsShown < postLimit) {
            Post post = sm.getPosts().get(sm.getPosts().size() - i - 1);
            if (!user.getHiddenPosts().contains(post) && !user.getBlockedList().contains(post.getAuthor())) {
                String postString = "POST_" + post.getTitle() + "`" + post.getSubtext() + "`" + post.getAuthor().getUsername() + "`" +
                        post.getComments().size() + "`" + post.getLikes() + "`" + post.getDislikes();
                postStrings.add(postString);
                postsShown++;
            }
            i++;
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
            String commentString = "COMMENT_" + comment.getText() + "`" + comment.getAuthor().getUsername() + "`" +
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

    public boolean searchUser(String username) {
        if (sm.findUser(username) != null) {
            return true;
        }
        return false;
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
            sm.writeUser(friend);
            sm.writeUser(user);
            return true;
        }
    }

    public boolean deleteFriend(String username, String friendUsername) {
        User user = sm.findUser(username);
        User friend = sm.findUser(friendUsername);
        if (user == null || friend == null) {
            return false;
        } else if (!user.getFriendsList().contains(friend) || user.equals(friend)) {
            return false;
        } else {
            user.removeFriend(friend);
            friend.removeFriend(user);
            sm.writeUser(friend);
            sm.writeUser(user);
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
            sm.writeUser(user);
            return true;
        }
    }

    public boolean unblockUser(String username, String blockUsername) {
        User user = sm.findUser(username);
        User block = sm.findUser(blockUsername);
        if (user == null || block == null) {
            return false;
        } else if (!user.getBlockedList().contains(block) || user.equals(block)) {
            return false;
        } else {
            user.unblock(block, sm);
            sm.writeUser(user);
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
            sm.writeUser(user);
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
            sm.writeUser(user);
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
            sm.writeUser(user);
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
            sm.writeUser(user);
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
            sm.writeUser(user);
            return true;
        }
    }

    public boolean deleteComment(String postTitle, String deleterUsername, String comment) {
        Post post = sm.findPost(postTitle);
        User deleter = sm.findUser(deleterUsername);
        if (post == null || deleter == null) {
            return false;
        }
        for(int i = 0; i < post.getComments().size(); i++) {
            if (post.getComments().get(i).getText().equals(comment)) {
                post.deleteComment(deleter, post.getComments().get(i));
                sm.writePost(post);
                return true;
            }
        }
        return false;
    }

    public boolean likePost(String username, String postTitle) {
        User user = sm.findUser(username);
        Post post = sm.findPost(postTitle);
        if (user == null || post == null) {
            return false;
        } else if (user.getLikedPosts().contains(post) && post.getAuthor().getBlockedList().contains(user)) {
            return false;
        } else {
            post.incrementLikes();
            user.addLikedPost(post);
            sm.writeUser(user);
            return true;
        }
    }

    public boolean unlikePost(String username, String postTitle) {
        User user = sm.findUser(username);
        Post post = sm.findPost(postTitle);
        if (user == null || post == null) {
            return false;
        } else if (user.getLikedPosts().contains(post) && post.getAuthor().getBlockedList().contains(user)) {
            return false;
        } else {
            post.removeLike();
            user.removeLikedPost(post);
            sm.writeUser(user);
            return true;
        }
    }

    public boolean dislikePost(String username, String postTitle) {
        User user = sm.findUser(username);
        Post post = sm.findPost(postTitle);
        if (user == null || post == null) {
            return false;
        } else if (user.getLikedPosts().contains(post) && post.getAuthor().getBlockedList().contains(user)) {
            return false;
        } else {
            post.incrementDislikes();
            user.addDislikedPost(post);
            sm.writeUser(user);
            return true;
        }
    }

    public boolean undislikePost(String username, String postTitle) {
        User user = sm.findUser(username);
        Post post = sm.findPost(postTitle);
        if (user == null || post == null) {
            return false;
        } else if (user.getLikedPosts().contains(post) && post.getAuthor().getBlockedList().contains(user)) {
            return false;
        } else {
            post.removeDislike();
            user.removeDislikedPost(post);
            sm.writeUser(user);
            return true;
        }
    }

    public boolean likeComment(String postTitle, String likerUsername, String comment) {
        Post post = sm.findPost(postTitle);
        User liker = sm.findUser(likerUsername);
        if (post == null || liker == null) {
            return false;
        }
        for(int i = 0; i < post.getComments().size(); i++) {
            if (post.getComments().get(i).getText().equals(comment)
                    && !post.getComments().get(i).getLikers().contains(liker)) {
                post.getComments().get(i).incrementLikes();
                post.getComments().get(i).addLiker(liker);
                sm.writePost(post);
                return true;
            }
        }
        return false;
    }

    public boolean unlikeComment(String postTitle, String likerUsername, String comment) {
        Post post = sm.findPost(postTitle);
        User liker = sm.findUser(likerUsername);
        if (post == null || liker == null) {
            return false;
        }
        for(int i = 0; i < post.getComments().size(); i++) {
            if (post.getComments().get(i).getText().equals(comment) &&
                    post.getComments().get(i).getLikers().contains(liker)) {
                post.getComments().get(i).removeLike();
                post.getComments().get(i).removeLiker(liker);
                sm.writePost(post);
                return true;
            }
        }
        return false;
    }

    public boolean dislikeComment(String postTitle, String dislikerUsername, String comment) {
        Post post = sm.findPost(postTitle);
        User disliker = sm.findUser(dislikerUsername);
        if (post == null || disliker == null) {
            return false;
        }
        for(int i = 0; i < post.getComments().size(); i++) {
            if (post.getComments().get(i).getText().equals(comment)
                    && !post.getComments().get(i).getDislikers().contains(disliker)) {
                post.getComments().get(i).incrementDislikes();
                post.getComments().get(i).addDisliker(disliker);
                sm.writePost(post);
                return true;
            }
        }
        return false;
    }

    public boolean undislikeComment(String postTitle, String dislikerUsername, String comment) {
        Post post = sm.findPost(postTitle);
        User disliker = sm.findUser(dislikerUsername);
        if (post == null || disliker == null) {
            return false;
        }
        for(int i = 0; i < post.getComments().size(); i++) {
            if (post.getComments().get(i).getText().equals(comment)
                    && post.getComments().get(i).getDislikers().contains(disliker)) {
                post.getComments().get(i).incrementDislikes();
                post.getComments().get(i).removeDisliker(disliker);
                sm.writePost(post);
                return true;
            }
        }
        return false;
    }
}
