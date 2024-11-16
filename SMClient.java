import java.io.IOException;
import java.io.Serializable;
import java.net.*;
import java.io.*;

public class SMClient implements Serializable {

    private int postsShown;

    SocialMediaDatabase sm;

    public SMClient(SocialMediaDatabase sm) {
        postsShown = 0;
        this.sm = sm;
    }

    public static void main(String[] args) throws IOException {
        // eventually going to be gui
        // maybe just terminal testing for now once server-client setup is done?
        Socket socket = new Socket("localhost", 8080);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw = new PrintWriter(socket.getOutputStream());





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