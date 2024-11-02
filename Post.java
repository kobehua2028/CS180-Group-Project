import java.io.Serializable;
import java.util.ArrayList;

public class Post implements Serializable {
    private User author; //The user who created this post.
    private String title; //title of post.
    private String subtext; //subtext of post
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    private int likes; //number of likes this post received
    private int dislikes; //number of dislikes this post received.

    public Post(User author, String title, String subtext, ArrayList<Comment> comments, int likes, int dislikes, SocialMediaDatabase sm) {
        this.author = author;
        this.title = title;
        this.subtext = subtext;
        this.comments = comments;
        this.likes = likes;
        this.dislikes = dislikes;
        sm.writePost(this);
    }

    public boolean equals(Post post) {
        return (author.equals(post.getAuthor()) && title.equals(post.getTitle()) && subtext.equals(post.getSubtext()));
    }

    public void addComment(Comment comment, SocialMediaDatabase sm) {
        comments.add(comment);
        sm.overwritePost(this);
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

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void incrementLikes() {
        likes++;
    }

    public void incrementDislikes() {
        dislikes++;
    }

}
