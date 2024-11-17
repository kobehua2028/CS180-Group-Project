import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.io.Serializable;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class SMClient implements Serializable {

    private String username;
    private static SocialMediaDatabase sm;
    // [["author","text","subtext","likes","dislikes"],        ,       ,      ,       ]

    public SMClient(SocialMediaDatabase sm) {
        SMClient.sm = sm;
    }

    public static void main(String[] args) throws IOException {
        // eventually going to be gui
        // maybe just terminal testing for now once server-client setup is done?
        Socket socket = new Socket("localhost", 8080);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw = new PrintWriter(socket.getOutputStream());

        if (echo(br, pw)) {
            System.out.println("Connected to server");
        } else {
            System.out.println("Failed to connect to server");
        }

    }

    public static boolean echo(BufferedReader br, PrintWriter pw) throws IOException {
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

    public static boolean login(BufferedReader br, PrintWriter pw, String username, String password) throws IOException {
        pw.println(String.format("LOGIN`%s`%s", username, password));
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

    public static boolean createUser(BufferedReader br, PrintWriter pw, String username, String password, String aboutMe) throws IOException {
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

    public static boolean deleteUser(BufferedReader br, PrintWriter pw, String username) throws IOException {
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

    public static ArrayList<ArrayList<String>> displayPosts(BufferedReader br, PrintWriter pw, String username) throws IOException {
        ArrayList<ArrayList<String>> posts = new ArrayList<>();
        pw.println(String.format("DISPLAY_POSTS,%s", username));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
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

    public static ArrayList<ArrayList<String>> displayComments(BufferedReader br, PrintWriter pw, String postTitle) throws IOException {
        ArrayList<ArrayList<String>> comments = new ArrayList<>();
        pw.println(String.format("DISPLAY_COMMENTS,%s", postTitle));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
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



}
//    public static ArrayList<ArrayList<String>> displayProfile(BufferedReader br, PrintWriter pw, String viewerUsername, String profileUsername) {
//        ArrayList<ArrayList<String>> profile = new ArrayList<>();
//        pw.println(String.format("DISPLAY_PROFILE,%s,%s", viewerUsername));
//    }
//
//
//}















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