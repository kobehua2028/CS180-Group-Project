public interface FeedFrameInterface {
    Panel createTopFeedPanel();
    JPanel displayComments();
    JPanel createPostFrame();
    JPanel createCommentTopPanel();
    JTextArea createSubtextArea();
    JPanel createCommentSection();
    JScrollPane createCommentsPanel();
    JPanel createMakeCommentPanel();
    void loadComments();
}
