import java.net.*;
import java.io.*;

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

        while (true) {
            Socket socket = serverSocket.accept();
            SocialMediaServer sm = new SocialMediaServer(socket);
            new Thread(sm).start();
        }
    }

    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader ois = new ObjectInputStream(inputStream);
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);

            String[] command = ((String) ois.readObject()).split(" ");
            while (command != null) {
                switch (command[0]) {
                    case "LOGIN" -> {

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
                        System.out.println("UNKNOWN_COMMENT");
                        return;
                    }
                }
                command = ((String) ois.readObject()).split(" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public User login(String username, String password) {
        sm.readUsers();
        User loginUser = sm.findUser(username);
        if (loginUser != null) {
            if (loginUser.getPassword().equals(password)) {
                return loginUser;
            }
        }
        return null;
    }

}
