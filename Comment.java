public class Comment {
    private String text;
    private int likes;
    private int dislikes;
    private User author;
    private Post post; //the post to which this comment belongs
    private static SocialMediaDatabase sm;


    public Comment(String commentLine, SocialMediaDatabase sm) {
        this.sm = sm;

        author = sm.findUser(commentLine.substring(0, commentLine.indexOf(",")));
        commentLine = commentLine.substring(commentLine.indexOf(",") + 1);

        post = sm.findPost(commentLine.substring(0, commentLine.indexOf(",")));
        commentLine = commentLine.substring(commentLine.indexOf(",") + 1);

        text = commentLine;
    }

    public String getText() {
        return text;
    }
}
