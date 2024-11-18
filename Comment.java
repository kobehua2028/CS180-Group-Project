import java.io.Serializable;
import java.util.ArrayList;

/**
 * CS180 Group Project
 * A class to handle commenting on posts.
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author Levin
 * @author Misha
 * @version Nov 03, 2024
 */
public class Comment implements Serializable, CommentInterface {
    private final String text;
    private int likes;
    private final ArrayList<User> likers = new ArrayList<>();
    private final ArrayList<User> dislikers = new ArrayList<>();
    private int dislikes;
    private final User author;
    private final Post post; //the post to which this comment belongs
    private final SocialMediaDatabase sm;

    public Comment(User author, String text, int likes, int dislikes, Post post, SocialMediaDatabase sm) {
        this.author = author;
        this.text = text;
        this.likes = likes;
        this.dislikes = dislikes;
        this.post = post;
        this.sm = sm;
        post.addComment(this);
    }

    public String getText() {
        return text;
    }

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public User getAuthor() {
        return author;
    }

    public Post getPost() {
        return post;
    }

    public ArrayList<User> getLikers() {
        return likers;
    }

    public ArrayList<User> getDislikers() {
        return dislikers;
    }

    public void incrementLikes() {
        synchronized (new Object()) {
            likes++;
        }
        sm.writePost(post);
    }

    public void incrementDislikes() {
        synchronized (new Object()) {
            dislikes++;
        }
        sm.writePost(post);
    }

    public void removeLike() {
        synchronized (new Object()) {
            if (likes > 0) {
                likes--;
            }
        }
        sm.writePost(post);

    }

    public void removeDislike() {
        synchronized (new Object()) {
            if (dislikes > 0) {
                dislikes--;
            }
        }
        sm.writePost(post);
    }

    public void addLiker(User user) {
        synchronized (new Object()) {
            this.likers.add(user);
        }
        sm.writePost(post);
    }

    public void addDisliker(User user) {
        synchronized (new Object()) {
            this.dislikers.add(user);
        }
        sm.writePost(post);
    }

    public void removeLiker(User user) {
        synchronized (new Object()) {
            this.likers.remove(user);
        }
        sm.writePost(post);
    }

    public void removeDisliker(User user) {
        synchronized (new Object()) {
            this.dislikers.remove(user);
        }
    }
}