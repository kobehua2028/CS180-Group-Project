/**
 * CS180 Group Project
 * An interface for the FeedFrame
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author MichaelIkriannikov
 * @version Dec 07, 2024
 */
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
