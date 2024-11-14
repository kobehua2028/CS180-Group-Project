import java.io.Serializable;
import java.util.ArrayList;
/**
 * CS180 Group Project
 * Program description here
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author Levin
 * @author Kobe
 * @version Nov 03, 2024
 */
public class Post implements Serializable, PostInterface {
    private final User author; //The user who created this post.
    private final String title; //title of post.
    private final String subtext; //subtext of post
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    private int likes; //number of likes this post received
    private int dislikes; //number of dislikes this post received.
    private final SocialMediaDatabase sm;

    public Post(User author, String title, String subtext, ArrayList<Comment> comments,
                int likes, int dislikes, SocialMediaDatabase sm) {
        if (author == null) {
            throw new IllegalArgumentException("Author cannot be null");
        }

        if (title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        if (subtext.isEmpty()) {
            throw new IllegalArgumentException("Subtext cannot be empty");
        }

        if (likes < 0) {
            throw new IllegalArgumentException("Likes cannot be negative");
        }

        if (dislikes < 0) {
            throw new IllegalArgumentException("Dislikes cannot be negative");
        }

        this.author = author;
        this.title = title;
        this.subtext = subtext;
        this.comments = comments;
        this.likes = likes;
        this.dislikes = dislikes;
        this.sm = sm;

        sm.writePost(this);
    }

    public void createComment(User author, String text) {
        Comment comment = new Comment(author, text, 0, 0, this, sm);
        sm.writePost(this);
    }

    public boolean equals(Post post) {
        return (author.equals(post.getAuthor()) && title.equals(post.getTitle()) && subtext.equals(post.getSubtext()));
    }

    public synchronized void addComment(Comment comment) {
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

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public synchronized void incrementLikes() {
        likes++;
        sm.writePost(this);
    }

    public synchronized void incrementDislikes() {
        dislikes++;
        sm.writePost(this);
    }

    public synchronized void removeLike() {
        if (likes > 0) {
            likes--;
            sm.writePost(this);
        }
    }

    public synchronized void removeDislike() {
        if (dislikes > 0) {
            dislikes--;
            sm.writePost(this);
        }
    }

}
