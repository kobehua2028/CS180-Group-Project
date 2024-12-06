import java.io.*;
import java.net.Socket;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * CS180 Group Project
 * Client class
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author Abdulmajed AlQarni
 * @author Levin
 * @author Kobe
 * @version Nov 17, 2024
 */

public class SMClient extends JComponent implements Runnable, Serializable, SMClientInterface {

    private String username;
    private final Socket socket;
    private BufferedReader br;
    private PrintWriter pw;
    private SMClient client;
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

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {}
    };

    public void run() {
        JFrame frame = new JFrame("Social Media Name");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        client = new SMClient(socket);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        JPanel loginPanel = new JPanel();
        JPanel commentPanel = new JPanel();
        JPanel feedPanel = new JPanel();
        JPanel searchPanel = new JPanel();
        mainPanel.add(loginPanel);

        // Login Stuff
        JLabel userNameLabel = new JLabel("Username:");
        userNameLabel.setBounds(300, 250, 300, 40);
        loginPanel.add(userNameLabel);

//        JButton loginButton = new JButton("Log In");
//        JButton signUpButton = new JButton("Sign Up");
//        JPanel optionPanel = new JPanel();
//        optionPanel.setSize(500, 300);
//        optionPanel.setLayout(new BorderLayout());
//        optionPanel.add(loginButton, BorderLayout.WEST);
//        optionPanel.add(signUpButton, BorderLayout.EAST);
//        loginPanel.add(optionPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
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
    public boolean login(String username1, String password) throws IOException {
        pw.println(String.format("LOGIN`%s`%s", username1, password));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("SUCCESS")) {
                this.username = username1;
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
    public boolean createUser(String username1, String password, String aboutMe) throws IOException {
        pw.println(String.format("REGISTER_USER`%s`%s`%s", username1, password, aboutMe));
        pw.flush();
        String line = br.readLine();
        while (line != null) {
            if (line.equals("SUCCESS")) {
                this.username = username1;
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
    public boolean deleteUser(String username1) throws IOException {
        pw.println(String.format("DELETE_ACCOUNT`%s`%s", this.username, username1));
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
    public ArrayList<ArrayList<String>> displayPosts() throws IOException {
        ArrayList<ArrayList<String>> posts = new ArrayList<>();
        pw.println(String.format("DISPLAY_POSTS`%s", this.username));
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

    public boolean searchUser(String username1) throws IOException {
        pw.println(String.format("SEARCH_USER`%s", username1));
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

    public boolean changeAboutMe(String username, String aboutMe) throws IOException {
        pw.println(String.format("CHANGE_ABOUT_ME`%s`%s", username, aboutMe));
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

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1111);

        SMClient client1 = new SMClient(socket);
        client1.createUser("Bob", "Bob123123", "IM BOB YO");
        client1.login("Bob", "Bob123123");
        client1.createPost("HI im bob!", "thisi ssubtext");

        SMClient client2 = new SMClient(socket);
        client2.createUser("Jimmy", "Yo123123", "its jimmerson");
        client2.login("Jimmy", "Yo123123");
        client2.createPost("Bob sucks", "<html>I hate bob fuck bob fuck bob i hate bob fuck bob I hate bob fuck bob fuck bob i hate bob fuck bob I hate bob fuck bob fuck bob i hate bob fuck bob I hate bob fuck bob fuck bob i hate bob fuck bob I hate bob fuck bob fuck bob i hate bob fuck bob I hate bob fuck bob fuck bob i hate bob fuck bob I hate bob fuck bob fuck bob i hate bob fuck bob I hate bob fuck bob fuck bob i hate bob fuck bob I hate bob fuck bob fuck bob i hate bob fuck bob I hate bob fuck bob fuck bob i hate bob fuck bob I hate bob fuck bob fuck bob i hate bob fuck bob I hate bob fuck bob fuck bob i hate bob fuck bob I hate bob fuck bob fuck bob i hate bob fuck bob I hate bob fuck bob fuck bob i hate bob fuck bob I hate bob fuck bob fuck bob i hate bob fuck bob I hate bob fuck bob fuck bob i hate bob fuck bob </html>");
        client2.likePost("HI im bob!");
        client2.createComment("HI im bob!", "fuck you bob");
        client2.createComment("HI im bob!", "fuck you bob22");

        SMClient client = new SMClient(socket);
        client.createUser("Levin", "Levin2020", "Aboot");
        SwingUtilities.invokeLater(new LoginFrame(client));
    }

    public String getUsername() throws IOException {
        return this.username;
    }
}






