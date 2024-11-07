import java.net.*;
import java.io.*;

public class SMServer implements Runnable {
    SocialMediaDatabase sm = new SocialMediaDatabase("users.dat", "posts.dat");
    Socket socket;

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
