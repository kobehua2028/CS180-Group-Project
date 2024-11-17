import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.io.Serializable;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class SMClient implements Serializable {

    private String username;
    private Socket socket;
    private BufferedReader br;
    private PrintWriter pw;
    // [["author","text","subtext","likes","dislikes"],        ,       ,      ,       ]

    public SMClient(Socket socket) {
        this.socket = socket;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        // eventually going to be gui
        // maybe just terminal testing for now once server-client setup is done?
        SMClient client = new SMClient(new Socket("localhost", 8080));


        if (client.echo()) {
            System.out.println("Connected to server");
        } else {
            System.out.println("Failed to connect to server");
        }

    }

    public boolean echo() throws IOException {
        pw.println("ECHO");
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("SUCCESS")) {
                return true;
            }
            line = br.readLine();
        }
        return false;
    }

    public boolean login(String username, String password) throws IOException {
        pw.println(String.format("LOGIN`%s`%s", username, password));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("SUCCESS")) {
                this.username = username;
                return true;
            }
            if (line.equals("FAIL")) {
                return false;
            }
            line = br.readLine();
        }
        return false;
    }

    public boolean createUser(String username, String password, String aboutMe) throws IOException {
        pw.println(String.format("REGISTER_USER`%s`%s`%s", username, password, aboutMe));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("SUCCESS")) {
                return true;
            }
            if (line.equals("FAIL")) {
                return false;
            }
            line = br.readLine();
        }
        return false;
    }

    public boolean deleteUser(String username) throws IOException {
        pw.println(String.format("DELETE_ACCOUNT`%s", username));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("SUCCESS")) {
                return true;
            }
            if (line.equals("FAIL")) {
                return false;
            }
            line = br.readLine();
        }
        return false;
    }

    public ArrayList<ArrayList<String>> displayPosts(String username) throws IOException {
        ArrayList<ArrayList<String>> posts = new ArrayList<>();
        pw.println(String.format("DISPLAY_POSTS`%s", username));
        pw.flush();
        String line = br.readLine();
        line = br.readLine();
        while (line != null) {
            if (line.equals("FAIL")) {
                return null;
            }
            if (line.contains("POST_")) {
                line = line.substring(line.indexOf("_") + 1);
                String[] postFields = line.split("`");
                ArrayList<String> post = new ArrayList<>();
                post.add(postFields[0]); // title
                post.add(postFields[1]); // subtext
                post.add(postFields[2]); // author
                post.add(postFields[3]); // amount of comments
                post.add(postFields[4]); // likes
                post.add(postFields[5]); // dislikes
                posts.add(post);
            }
        }
        return posts;
    }

    public ArrayList<ArrayList<String>> displayComments(String postTitle) throws IOException {
        ArrayList<ArrayList<String>> comments = new ArrayList<>();
        pw.println(String.format("DISPLAY_COMMENTS`%s", postTitle));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("FAIL")) {
                return null;
            }
            if (line.contains("COMMENT_")) {
                line = line.substring(line.indexOf("_") + 1);
                String[] commentFields = line.split("`");
                ArrayList<String> comment = new ArrayList<>();
                comment.add(commentFields[0]); // text
                comment.add(commentFields[1]); // author
                comment.add(commentFields[2]); // likes
                comment.add(commentFields[3]); // dislikes
                comments.add(comment);
            }
        }
        return comments;
    }

    public ArrayList<String[]> displayProfile(String profileUsername) throws IOException {
        ArrayList<String[]> profile = new ArrayList<>();
        pw.println(String.format("DISPLAY_PROFILE`%s`%s", username, profileUsername));
        pw.flush();
        String line = br.readLine();
        String[] friends = {};
        String[] blocks = {};
        String[] posts = {};
        String[] aboutMe = new String[1];
        while (line != null) {
            if (line.equals("FAIL")) {
                return null;
            }
            if (line.contains("FRIENDS_LIST")) {
                friends = line.substring(line.indexOf("_") + 1).split("`");
            }
            if (line.contains("BLOCKED_LIST")) {
                blocks = line.substring(line.indexOf("_") + 1).split("`");
            }
            if (line.contains("POSTS_LIST")) {
                posts = line.substring(line.indexOf("_") + 1).split("`");
            }
            if (line.contains("ABOUT_ME")) {
                aboutMe[0] = line.substring(line.indexOf("_") + 1);
            }
        }
        profile.add(friends); // profile.get(0) = ["bob", "joe", "susan"] (friends)
        profile.add(blocks);
        profile.add(posts);
        profile.add(aboutMe);
        return profile;
    }

    public boolean addFriend(String friendUsername) throws IOException {
        pw.println(String.format("ADD_FRIEND`%s`%s", username, friendUsername));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("FAIL")) {
                return false;
            }
            if (line.equals("SUCCESS")) {
                return true;
            }
            line = br.readLine();
        }
        return false;
    }

    public boolean deleteFriend(String friendUsername) throws IOException {
        pw.println(String.format("DELETE_FRIEND`%s`%s", username, friendUsername));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("FAIL")) {
                return false;
            }
            if (line.equals("SUCCESS")) {
                return true;
            }
            line = br.readLine();
        }
        return false;
    }

    public boolean blockUser(String blockUsername) throws IOException {
        pw.println(String.format("BLOCK_USER`%s`%s", username, blockUsername));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("FAIL")) {
                return false;
            }
            if (line.equals("SUCCESS")) {
                return true;
            }
            line = br.readLine();
        }
        return false;
    }

    public boolean unblockUser(String blockUsername) throws IOException {
        pw.println(String.format("UNBLOCK_USER`%s`%s", username, blockUsername));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("FAIL")) {
                return false;
            }
            if (line.equals("SUCCESS")) {
                return true;
            }
            line = br.readLine();
        }
        return false;
    }

    public boolean hidePost(String postTitle) throws IOException {
        pw.println(String.format("HIDE_POST`%s`%s", username, postTitle));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("FAIL")) {
                return false;
            }
            if (line.equals("SUCCESS")) {
                return true;
            }
        }
        return false;
    }

    public boolean unhidePost(String postTitle) throws IOException {
        pw.println(String.format("UNHIDE_POST`%s`%s", username, postTitle));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("FAIL")) {
                return false;
            }
            if (line.equals("SUCCESS")) {
                return true;
            }
            line = br.readLine();
        }
        return false;
    }

    public boolean createPost(String title, String subtext) throws IOException {
        pw.println(String.format("CREATE_POST`%s`%s`%s", username, title, subtext));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("FAIL")) {
                return false;
            }
            if (line.equals("SUCCESS")) {
                return true;
            }
            line = br.readLine();
        }
        return false;
    }

    public boolean deletePost(String postTitle) throws IOException {
        pw.println(String.format("DELETE_POST`%s`%s", username, postTitle));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("FAIL")) {
                return false;
            }
            if (line.equals("SUCCESS")) {
                return true;
            }
            line = br.readLine();
        }
        return false;
    }

    public boolean createComment(String postTitle, String comment) throws IOException {
        pw.println(String.format("CREATE_COMMENT`%s`%s`%s", postTitle, username, comment));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("FAIL")) {
                return false;
            }
            if (line.equals("SUCCESS")) {
                return true;
            }
            line = br.readLine();
        }
        return false;
    }

    public boolean deleteComment(String postTitle, String comment) throws IOException {
        pw.println(String.format("DELETE_COMMENT`%s`%s`%s", postTitle, username, comment));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("FAIL")) {
                return false;
            }
            if (line.equals("SUCCESS")) {
                return true;
            }
            line = br.readLine();
        }
        return false;
    }

    public boolean likePost(String postTitle) throws IOException {
        pw.println(String.format("LIKE_POST`%s`%s", username, postTitle));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("FAIL")) {
                return false;
            }
            if (line.equals("SUCCESS")) {
                return true;
            }
            line = br.readLine();
        }
        return false;
    }

    public boolean unlikePost(String postTitle) throws IOException {
        pw.println(String.format("UNLIKE_POST`%s`%s", username, postTitle));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("FAIL")) {
                return false;
            }
            if (line.equals("SUCCESS")) {
                return true;
            }
            line = br.readLine();
        }
        return false;
    }

    public boolean dislikePost(String postTitle) throws IOException {
        pw.println(String.format("DISLIKE_POST`%s`%s", username, postTitle));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("FAIL")) {
                return false;
            }
            if (line.equals("SUCCESS")) {
                return true;
            }
            line = br.readLine();
        }
        return false;
    }

    public boolean undislikePost(String postTitle) throws IOException {
        pw.println(String.format("UNDISLIKE_POST`%s`%s", username, postTitle));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("FAIL")) {
                return false;
            }
            if (line.equals("SUCCESS")) {
                return true;
            }
            line = br.readLine();
        }
        return false;
    }

    public boolean likeComment(String postTitle, String comment) throws IOException {
        pw.println(String.format("LIKE_COMMENT`%s`%s`%s", postTitle, username, comment));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("FAIL")) {
                return false;
            }
            if (line.equals("SUCCESS")) {
                return true;
            }
            line = br.readLine();
        }
        return false;
    }

    public boolean unlikeComment(String postTitle, String comment) throws IOException {
        pw.println(String.format("UNLIKE_COMMENT`%s`%s`%s", postTitle, username, comment));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("FAIL")) {
                return false;
            }
            if (line.equals("SUCCESS")) {
                return true;
            }
            line = br.readLine();
        }
        return false;
    }

    public boolean dislikeComment(String postTitle, String comment) throws IOException {
        pw.println(String.format("DISLIKE_COMMENT`%s`%s`%s", postTitle, username, comment));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("FAIL")) {
                return false;
            }
            if (line.equals("SUCCESS")) {
                return true;
            }
            line = br.readLine();
        }
        return false;
    }


    public boolean undislikeComment(String postTitle, String comment) throws IOException {
        pw.println(String.format("UNDISLIKE_COMMENT`%s`%s`%s", postTitle, username, comment));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("FAIL")) {
                return false;
            }
            if (line.equals("SUCCESS")) {
                return true;
            }
            line = br.readLine();
        }
        return false;
    }

    public void logout() throws IOException {
        pw.println("LOGOUT");
        pw.flush();
    }

}




















//    public void displayPosts() {
//        String postString = "";
//        int postCount = 1;
//
//        if (sm.getPosts().size() - postsShown < 5) {
//            for (int i = postsShown; i < sm.getPosts().size(); i++) {
//                Post displayingPost = sm.getPosts().get(i);
//                postString += String.format("%d)\n", postCount);
//                postString += String.format("%s says:\n", displayingPost.getAuthor().getUsername());
//                postString += String.format("%s\n\n", displayingPost.getTitle());
//                postString += String.format("%s\n\n", displayingPost.getSubtext());
//                postString += String.format("Likes: %d    Dislikes: %d    Comments: %d\n",
//                        displayingPost.getLikes(), displayingPost.getDislikes(), displayingPost.getComments().size());
//                postString += "__________________________________________\n";
//                postCount++;
//            }
//            postsShown = sm.getPosts().size() - 1;
//        } else {
//            for (int i = postsShown; i < postsShown + 5; i++) {
//                Post displayingPost = sm.getPosts().get(i);
//                postString += String.format("%d)\n", postCount);
//                postString += String.format("%s says:\n", displayingPost.getAuthor().getUsername());
//                postString += String.format("%s\n\n", displayingPost.getTitle());
//                postString += String.format("%s\n\n", displayingPost.getSubtext());
//                postString += String.format("Likes: %d    Dislikes: %d    Comments: %d\n",
//                        displayingPost.getLikes(), displayingPost.getDislikes(), displayingPost.getComments().size());
//                postString += "__________________________________________\n";
//                postCount++;
//            }
//            postsShown += 5;
//        }
//        System.out.print(postString);
//    }