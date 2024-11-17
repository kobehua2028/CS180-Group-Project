import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.io.Serializable;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class SMClient implements Serializable {

    private String username;
    private Socket socket;
    private BufferedReader br;
    private PrintWriter pw;
    // [["author","text","subtext","likes","dislikes"],        ,       ,      ,       ]

    public SMClient(Socket socket) {
        this.socket = socket;
        try {
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.pw = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        // eventually going to be gui
        // maybe just terminal testing for now once server-client setup is done?
        SMClient client = new SMClient(new Socket("localhost", 8080));
        Scanner scanner = new Scanner(System.in);
        if (client.echo()) {
            System.out.println("Connected to server");
        } else {
            System.out.println("Failed to connect to server");
        }

        System.out.println("What's your usernamae");
        String username = scanner.nextLine();
        System.out.println("What's your password?");
        String password = scanner.nextLine();

        client.createUser(username, password, "aboutMe test");

        if(client.login(username, password)){
            System.out.println("Login successful");
        }

        // TEST FRIEND SYSTEM
        System.out.println("Who do you want to add as a friend: ");
        while(scanner.hasNextLine()) {
            String friendToAdd = scanner.nextLine();
            if (client.addFriend(friendToAdd)) {
                System.out.println(friendToAdd + " added");
                System.out.println(Arrays.asList(client.displayProfile(username).get(0)));
                break;
            } else {
                System.out.println(friendToAdd + " not added");
                System.out.println(Arrays.asList(client.displayProfile(username).get(0)));
            }
        }

        System.out.println("Who do you want to remove: ");
        while(scanner.hasNextLine()) {
            String friendToRemove = scanner.nextLine();
            if (client.deleteFriend(friendToRemove)) {
                System.out.println(friendToRemove + " removed");
                System.out.println(Arrays.asList(client.displayProfile(username).get(0)));
                break;
            } else {
                System.out.println(friendToRemove + " not removed");
                System.out.println(Arrays.asList(client.displayProfile(username).get(0)));
            }
        }

        System.out.println("Who do you want to block: ");
        while(scanner.hasNextLine()) {
            String userToBlock = scanner.nextLine();
            if (client.blockUser(userToBlock)) {
                System.out.println(userToBlock + " blocked");
                System.out.println(Arrays.asList(client.displayProfile(username).get(1)));
                break;
            } else {
                System.out.println(userToBlock + " not blocked");
                System.out.println(Arrays.asList(client.displayProfile(username).get(1)));
            }
        }

        System.out.println("Who do you want to unblock: ");
        while(scanner.hasNextLine()) {
            String userToUnblock = scanner.nextLine();
            if (client.unblockUser(userToUnblock)) {
                System.out.println(userToUnblock + " unblocked");
                System.out.println(Arrays.asList(client.displayProfile(username).get(1)));
                break;
            } else {
                System.out.println(userToUnblock + " not unblocked");
                System.out.println(Arrays.asList(client.displayProfile(username).get(1)));
            }
        }

        // TEST POST PROFILE COMMENT
//        System.out.println("Title of post 1:");
//        String postTitle = scanner.nextLine();
//        System.out.println("Subtext of post 1:");
//        String subText = scanner.nextLine();
//
//        if(client.createPost(postTitle, subText)){
//            System.out.println("Post created successfully");
//        }
//
//        System.out.println("Title of post 2:");
//        String postTitle2 = scanner.nextLine();
//        System.out.println("Subtext of post 2:");
//        String subText2 = scanner.nextLine();
//
//        if(client.createPost(postTitle2, subText2)){
//            System.out.println("Post 2 created successfully");
//        }
//
//        System.out.println(client.displayPosts(username).toString());
//        ArrayList<String> postInfo = client.displayPosts(username).get(0);
//        if (client.createComment(postInfo.get(0), "Nice Cat")) {
//            System.out.println("Comment created successfully");
//        }
//        client.createComment(postInfo.get(0), "Fat Cat");
//        System.out.println(client.displayComments(postInfo.get(0)).toString());
//        ArrayList<String[]> profile = client.displayProfile("Kobe");
//        System.out.println(Arrays.asList(profile.get(0)));
//        System.out.println(Arrays.asList(profile.get(1)));
//        System.out.println(Arrays.asList(profile.get(2)));
//        System.out.println(Arrays.asList(profile.get(3)));
    }
    // works
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
    // works
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
    // works
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
    //works
    public boolean deleteUser(String username) throws IOException {
        pw.println(String.format("DELETE_ACCOUNT`%s`%s", this.username, username));
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
    // works
    public ArrayList<ArrayList<String>> displayPosts(String username) throws IOException {
        ArrayList<ArrayList<String>> posts = new ArrayList<>();
        pw.println(String.format("DISPLAY_POSTS`%s", username));
        pw.flush();
        String line = br.readLine();
        while (true) {
            if (line.equals("FAIL")) {
                return null;
            }
            if (line.equals("ALL_POSTS_SENT")) {
                break;
            }
            if (line.contains("POST_")) {
                line = line.substring(line.indexOf("_") + 1);
                String[] postFields = line.split("`");
                ArrayList<String> post = new ArrayList<>();
                post.add(postFields[0]); // title
                post.add(postFields[1]); // subtext
                post.add(postFields[2]); // author name
                post.add(postFields[3]); // amount of comments
                post.add(postFields[4]); // likes
                post.add(postFields[5]); // dislikes
                posts.add(post);
            }
            line = br.readLine();
        }
        return posts;
    }
    // works
    public ArrayList<ArrayList<String>> displayComments(String postTitle) throws IOException {
        ArrayList<ArrayList<String>> comments = new ArrayList<>();
        pw.println(String.format("DISPLAY_COMMENTS`%s", postTitle));
        pw.flush();
        String line = br.readLine();
        while (true) {
            if (line.equals("FAIL")) {
                return null;
            }
            if (line.equals("ALL_COMMENTS_SENT")) {
                break;
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
            line = br.readLine();
        }
        return comments;
    }
    // works
    public ArrayList<String[]> displayProfile(String profileUsername) throws IOException {
        ArrayList<String[]> profile = new ArrayList<>();
        pw.println(String.format("DISPLAY_PROFILE`%s`%s", username, profileUsername));
        pw.flush();
        String line = br.readLine();
        String[] friends = {};
        String[] blocks = {};
        String[] posts = {};
        String[] aboutMe = new String[1];
        while (true) {
            if (line.equals("FAIL")) {
                return null;
            }
            if (line.equals("ALL_PROFILE_INFO_SENT")) {
                break;
            }
            if (line.contains("FRIENDS_LIST")) {
                friends = line.substring(line.indexOf("`") + 1).split("`");
                if (friends[0].equals("FRIENDS_LIST")) {
                    friends[0] = null;
                }
            }
            if (line.contains("BLOCKED_LIST")) {
                blocks = line.substring(line.indexOf("`") + 1).split("`");
                if (blocks[0].equals("BLOCKED_LIST")) {
                    blocks[0] = null;
                }
            }
            if (line.contains("POSTS_LIST")) {
                posts = line.substring(line.indexOf("`") + 1).split("`");
                if (posts[0].equals("POSTS_LIST")) {
                    posts[0] = null;
                }
            }
            if (line.contains("ABOUT_ME")) {
                aboutMe[0] = line.substring(line.indexOf("`") + 1);
            }
            line = br.readLine();
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

    public boolean searchUser(String username) throws IOException {
        pw.println(String.format("SEARCH_USER`%s", username));
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
        socket.close();
        br.close();
        pw.close();
    }

}






