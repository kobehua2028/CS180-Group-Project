import java.util.ArrayList;

public class Post {
    private static SocialMediaDatabase sm;
    private User author; //The user who created this post.
    private String title; //title of post.
    private String subtext; //subtext of post
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    private int likes; //number of likes this post received
    private int dislikes; //number of dislikes this post received.


    public Post(User poster) {
        author = poster;
        likes = 0;
        dislikes = 0;

    }

    public Post(String postLine, SocialMediaDatabase sm) {
        author = sm.findUser(postLine.substring(0, postLine.indexOf(",")));
        postLine = postLine.substring(postLine.indexOf(",") + 1);

        title = postLine.substring(0, postLine.indexOf(","));
        postLine = postLine.substring(postLine.indexOf(",") + 1);

        subtext = postLine;
    }

    public boolean equals(Post post) {
        return (author.equals(post.getAuthor()) && title.equals(post.getTitle()) && subtext.equals(post.getSubtext()));
    }
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public String getTitle() {
        return title;
    }

    public String getSubtext() {
        return subtext;
    }

    public User getAuthor() {
        return author;
    }

    public String toString() {
        return String.format("%s,%s,%s", author.getUsername(), title, subtext);
    }



}
