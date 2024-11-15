import java.net.*;
import java.io.*;

/**
 * CS180 Group Project
 * Server class for client to communicate to database
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author (update with your name if you work on the class)
 * @version Nov 13, 2024
 */

public class SMServer implements Runnable {
    private static SocialMediaDatabase sm = new SocialMediaDatabase("users.dat", "posts.dat");
    private Socket socket;

    public SMServer(Socket socket) {
        this.socket = socket;
    }

    public User logIn(String username, String password) {
        User user = sm.findUser(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    //Increment scroll each tim
    public Post[] display(int scroll) {
        Post[] feed = new Post[5];
        int postNum = sm.getPosts().size();
        for (int i = scroll * 5; i < feed.length; i++) {
            if (i == sm.getPosts().size())
                break;
            feed[i] = sm.getPosts().get(i);
        }
        return feed;
    }

    public void run() {
        User client;
        int scroll = 0; //argument for display(). Increment after each use.
        
        // TODO: we need to make this display the specific users feed, not all of the posts in the databse

        try (BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw = new PrintWriter(socket.getOutputStream())) {

            Post[] postFeed = display(scroll++);

            for(Post post : postFeed) {
                pw.print(post.toString());
                pw.println();
                pw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(4343);

            while (true) {
                Socket socket = serverSocket.accept();
                SMServer server = new SMServer(socket);
                server.sm.readUsers();
                server.sm.readPosts();
                new Thread(server).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
