import java.io.Serializable;

public class Comment implements Serializable {
    private String text;
    private int likes;
    private int dislikes;
    private User author;
    private Post post; //the post to which this comment belongs

    public Comment(User author, String text, int likes, int dislikes, Post post) {
        this.author = author;
        this.text = text;
        this.likes = likes;
        this.dislikes = dislikes;
        this.post = post;
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
}
