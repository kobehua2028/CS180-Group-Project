import java.io.Serializable;

public class Comment implements Serializable {
    private final String text;
    private int likes;
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

    public void incrementLikes() {
        likes++;
        sm.writePost(post);
    }

    public void incrementDislikes() {
        dislikes++;
        sm.writePost(post);
    }
}
