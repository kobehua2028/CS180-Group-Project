import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class FeedFrame extends JComponent implements Runnable {
    private SMClient client;
    private JFrame feedFrame;
    private JButton dislikesButton;
    private JButton likesButton;
    private JButton commentsButton;
    private ArrayList<JPanel> posts = new ArrayList<>();

    private ArrayList<JPanel> comments = new ArrayList<>();
    private String title;
    private String subtext;
    private String author;
    private int numberOfComments;
    private int likes;
    private int dislikes;


    FeedFrame(SMClient client) {
        this.client = client;
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
                JButton buttonClicked = (JButton) e.getSource();

                System.out.println(buttonClicked.getParent().getParent().getName());
                System.out.println(buttonClicked.getParent().getName());
                System.out.println(buttonClicked.getName());
                String [] componentInfo = buttonClicked.getParent().getParent().getName().split("`");
                String holder;
                if (buttonClicked.getText().contains("\uD83D\uDC4D")) {
                    try {
                        if (buttonClicked.getName().equals("notliked") && client.likePost(componentInfo[0])) {
                            buttonClicked.setText("\uD83D\uDC4D" + String.valueOf(Integer.parseInt(componentInfo[4]) + 1));
                            holder = componentInfo[4];
                            componentInfo[4] = String.valueOf(Integer.parseInt(holder) + 1);
                            buttonClicked.setName("liked");
                        } else if (buttonClicked.getName().equals("liked") && client.unlikePost(componentInfo[0])) {
                            buttonClicked.setText("\uD83D\uDC4D" + String.valueOf(Integer.parseInt(componentInfo[4]) - 1));
                            holder = componentInfo[4];
                            componentInfo[4] = String.valueOf(Integer.parseInt(holder) - 1);
                            buttonClicked.setName("notliked");
                        } else if (buttonClicked.getName().equals("commentnotliked") && client.likeComment(title, componentInfo[0])) {
                            buttonClicked.setText("\uD83D\uDC4D" + String.valueOf(Integer.parseInt(componentInfo[2]) + 1));
                            holder = componentInfo[2];
                            componentInfo[2] = String.valueOf(Integer.parseInt(holder) + 1);
                            buttonClicked.setName("commentliked");
                        } else if (buttonClicked.getName().equals("commentliked") && client.unlikeComment(title, componentInfo[0])) {
                            buttonClicked.setText("\uD83D\uDC4D" + String.valueOf(Integer.parseInt(componentInfo[2]) - 1));
                            holder = componentInfo[2];
                            componentInfo[2] = String.valueOf(Integer.parseInt(holder) - 1);
                            buttonClicked.setName("commentnotliked");
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                if (buttonClicked.getText().contains("\uD83D\uDC4E")) {
                    try {
                        if (buttonClicked.getName().equals("notdisliked") && client.dislikePost(componentInfo[0])) {
                            buttonClicked.setText("\uD83D\uDC4E" + String.valueOf(Integer.parseInt(componentInfo[5]) + 1));
                            holder = componentInfo[5];
                            componentInfo[5] = String.valueOf(Integer.parseInt(holder) + 1);
                            buttonClicked.setName("disliked");
                        } else if (buttonClicked.getName().equals("disliked") && client.undislikePost(componentInfo[0])) {
                            buttonClicked.setText("\uD83D\uDC4E" + String.valueOf(Integer.parseInt(componentInfo[5]) - 1));
                            holder = componentInfo[5];
                            componentInfo[5] = String.valueOf(Integer.parseInt(holder) - 1);
                            buttonClicked.setName("notdisliked");
                        } else if(buttonClicked.getName().equals("commentnotdisliked") && client.dislikeComment(title, componentInfo[0])) {
                            buttonClicked.setText("\uD83D\uDC4E" + String.valueOf(Integer.parseInt(componentInfo[3]) + 1));
                            holder = componentInfo[3];
                            componentInfo[3] = String.valueOf(Integer.parseInt(holder) + 1);
                            buttonClicked.setName("commentdisliked");
                        } else if (buttonClicked.getName().equals("commentdisliked") && client.undislikeComment(title, componentInfo[0])) {
                            buttonClicked.setText("\uD83D\uDC4E" + String.valueOf(Integer.parseInt(componentInfo[3]) - 1));
                            holder = componentInfo[3];
                            componentInfo[3] = String.valueOf(Integer.parseInt(holder) - 1);
                            buttonClicked.setName("commentnotdisliked");
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                if (buttonClicked.getText().contains("\uD83D\uDDD1")) {
                    try {
                        if (buttonClicked.getName().equals("nothidden") && client.hidePost(componentInfo[0])) {
                            buttonClicked.getParent().getParent().setVisible(false);
                            try {
                                Component existingCommentsPanel = feedFrame.getContentPane().getComponent(2); // EAST region is index 2 in BorderLayout
                                if (existingCommentsPanel != null && existingCommentsPanel.getName().equals(componentInfo[0])) {
                                    existingCommentsPanel.setVisible(false);
                                }
                            } catch (IndexOutOfBoundsException e3) {
                            }
                            buttonClicked.setName("hidden");
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                if (buttonClicked.getText().contains("\uD83D\uDCAC")) {
                    title = componentInfo[0];
                    subtext = componentInfo[1];
                    author = componentInfo[2];
                    numberOfComments = Integer.parseInt(componentInfo[3]);
                    likes = Integer.parseInt(componentInfo[4]);
                    dislikes = Integer.parseInt(componentInfo[5]);

                    // Remove the existing comment section (if any)
                    try {
                        Component existingCommentsPanel = feedFrame.getContentPane().getComponent(2); // EAST region is index 2 in BorderLayout
                        if (existingCommentsPanel != null) {
                            feedFrame.remove(existingCommentsPanel);
                        }
                        feedFrame.add(displayComments(), BorderLayout.EAST);
                    } catch (IndexOutOfBoundsException e3) {
                        feedFrame.add(displayComments(), BorderLayout.EAST);
                    }

                    // Ensure the frame updates
                    feedFrame.revalidate();
                    feedFrame.repaint();
                    buttonClicked.setName("commentclicked");
                }
                if (buttonClicked.getText().equals("Create Post")) {
                    SwingUtilities.invokeLater(new CreatePostFrame(client));
                }
                String newName = "";
                for (String info : componentInfo) {
                    newName += info + "`";
                }
                buttonClicked.getParent().getParent().setName(newName.substring(0, newName.length() - 1));
                if (buttonClicked.getText().equals("Profile")) {
                    System.out.println("Profile clicked");
                    SwingUtilities.invokeLater(new OwnProfileFrame(client));
                }
            }
        }
    };

//    contains -> notauthor:
//    split by ":" and take everything after the : (thats the username of profile)
//    invoke ProfileFrame(client, profileUsername)

    @Override
    public void run() {
        feedFrame = new JFrame("Feed");
        feedFrame.setLayout(new BorderLayout());
        feedFrame.setSize(1280, 720);
        feedFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        feedFrame.setLocationRelativeTo(null);
        feedFrame.setResizable(false);


        // Top panel with buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton searchButton = new JButton("Search");
        JTextField searchText = new JTextField();
        searchText.setSize(new Dimension(150, 30));
        searchText.setPreferredSize(new Dimension(150, 30));
        searchButton.setMaximumSize(new Dimension(150, 30));
        JButton profileButton = new JButton("Profile");
        profileButton.setName("Profile");
        JButton createPostButton = new JButton("Create Post");
        topPanel.add(searchText);
        topPanel.add(searchButton);
        topPanel.add(profileButton);
        topPanel.add(createPostButton);

        // ACtion listenr
        profileButton.addActionListener(actionListener);
        createPostButton.addActionListener(actionListener);

        // Scrollable feed panel
        JPanel feed = new JPanel();
        feed.setLayout(new BoxLayout(feed, BoxLayout.Y_AXIS));
        feed.setPreferredSize(new Dimension(640, 720));
        feed.setPreferredSize(new Dimension(640, 720));
        loadPosts();
        for (JPanel panel : posts) {
            feed.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between posts
            panel.setPreferredSize(new Dimension(500,300));
            panel.setMaximumSize(new Dimension(500,300));
            feed.add(panel);
        }
        JScrollPane scrollPane = new JScrollPane(feed);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add components to frame
        feedFrame.add(topPanel, BorderLayout.NORTH);
        feedFrame.add(scrollPane, BorderLayout.WEST);
        feedFrame.setVisible(true);
    }

    public void loadPosts() {
        try {
            posts.clear();
            ArrayList<ArrayList<String>> clientPosts = client.displayPosts();
            for (ArrayList<String> post : clientPosts) {
                JPanel postPanel = new JPanel();
                postPanel.setLayout(new BorderLayout());
                postPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0,0,0,10), 2),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));

                // Top: Title and Author
                JPanel topPanel = new JPanel(new BorderLayout());
                topPanel.setOpaque(false);
                JLabel titleLabel = new JLabel(post.get(0));
                titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
                JButton authorButton = new JButton("- " + post.get(2));
                authorButton.setFont(new Font("Arial", Font.PLAIN, 12));
                authorButton.setOpaque(false);
                authorButton.setContentAreaFilled(false);
                topPanel.add(titleLabel, BorderLayout.WEST);
                topPanel.add(authorButton, BorderLayout.EAST);

                // Center: Subtext (Wrapped)
                JTextArea subtextArea = new JTextArea(post.get(1));
                subtextArea.setFont(new Font("Arial", Font.PLAIN, 14));
                subtextArea.setLineWrap(true);
                subtextArea.setWrapStyleWord(true);
                subtextArea.setEditable(false);
                subtextArea.setBackground(Color.WHITE);
                subtextArea.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

                // Bottom: Buttons (Like, Dislike, Comments, Hide)
                JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                bottomPanel.setOpaque(false);
                likesButton = new JButton("\uD83D\uDC4D " + post.get(4));
                likesButton.setName("notliked");
                dislikesButton = new JButton("\uD83D\uDC4E " + post.get(5));
                dislikesButton.setName("notdisliked");
                commentsButton = new JButton("\uD83D\uDCAC " + post.get(3));
                JButton hidePostButton = new JButton("\uD83D\uDDD1");
                hidePostButton.setName("nothidden");
                likesButton.addActionListener(actionListener);
                dislikesButton.addActionListener(actionListener);
                commentsButton.addActionListener(actionListener);
                hidePostButton.addActionListener(actionListener);
                bottomPanel.add(likesButton);
                bottomPanel.add(dislikesButton);
                bottomPanel.add(commentsButton);
                bottomPanel.add(hidePostButton);

                postPanel.add(topPanel, BorderLayout.NORTH);
                postPanel.add(subtextArea, BorderLayout.CENTER);
                postPanel.add(bottomPanel, BorderLayout.SOUTH);

                postPanel.setName(String.format("%s`%s`%s`%s`%s`%s", post.get(0), post.get(1), post.get(2), post.get(3), post.get(4), post.get(5)));
                posts.add(postPanel);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(feedFrame, "No posts to show", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JPanel displayComments () {
        JPanel postFrame = new JPanel();
        postFrame.setName(title);
        postFrame.setLayout(new BorderLayout());
        postFrame.setPreferredSize(new Dimension(600, 720));
        postFrame.setMaximumSize(new Dimension(600, 720));

        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(new Color(240, 240, 240));

        // Title and Author
        JPanel titleAuthorPanel = new JPanel();
        titleAuthorPanel.setLayout(new BoxLayout(titleAuthorPanel, BoxLayout.Y_AXIS));
        titleAuthorPanel.setBackground(new Color(240, 240, 240));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JLabel authorLabel = new JLabel("by " + author);
        authorLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        titleAuthorPanel.add(titleLabel);
        titleAuthorPanel.add(authorLabel);
        topPanel.add(titleAuthorPanel, BorderLayout.CENTER);

        // Subtext area
        JTextArea subtextArea = new JTextArea(subtext);
        subtextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        subtextArea.setLineWrap(true);
        subtextArea.setWrapStyleWord(true);
        subtextArea.setEditable(false);
        subtextArea.setBackground(Color.WHITE);
        subtextArea.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Holds both the comments and the createComment panel
        JPanel commentSection = new JPanel(new BorderLayout());
        commentSection.setPreferredSize(new Dimension(600, 350));
        commentSection.setMaximumSize(new Dimension(600, 350));

        // Comments panel
        JPanel commentsPanel = new JPanel();
        commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));
        loadComments();
        for (JPanel comment : comments) {
            comment.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            comment.setBackground(Color.WHITE);
            commentsPanel.add(comment);
            commentsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
        }

        // Scroll pane for comments
        JScrollPane scrollPane = new JScrollPane(commentsPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        commentSection.add(scrollPane, BorderLayout.CENTER); // Set scroll pane in the center

        // Create Comment Panel
        JPanel makeComment = new JPanel();
        makeComment.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JTextField commentTextField = new JTextField();
        commentTextField.setPreferredSize(new Dimension(400, 30));
        JButton commentButton = new JButton("Comment");
        makeComment.add(commentTextField);
        makeComment.add(commentButton);
        commentSection.add(makeComment, BorderLayout.SOUTH); // Set makeComment panel at the bottom

        commentButton.addActionListener(actionListener);

        // Add components to the frame
        postFrame.add(topPanel, BorderLayout.NORTH);
        postFrame.add(subtextArea, BorderLayout.CENTER);
        postFrame.add(commentSection, BorderLayout.SOUTH);
        postFrame.setVisible(true);
        return postFrame;
    }

    public void loadComments() {
        try {
            comments.clear();
            ArrayList<ArrayList<String>> postComments = client.displayComments(this.title);
            for (ArrayList<String> comment : postComments) {
                JPanel commentPanel = new JPanel();
                commentPanel.setBorder(BorderFactory.createBevelBorder(1));
                commentPanel.setLayout(new BorderLayout());

                commentPanel.setPreferredSize(new Dimension(600, 100));
                commentPanel.setMaximumSize(new Dimension(600, 100));

                String commentText = comment.get(0);
                String commentAuthor = comment.get(1);
                int commentLikes = Integer.parseInt(comment.get(2));
                int commentDislikes = Integer.parseInt(comment.get(3));

                // Center: Subtext (Wrapped)
                JTextArea subtextArea = new JTextArea(commentText);
                subtextArea.setFont(new Font("Arial", Font.PLAIN, 14));
                subtextArea.setLineWrap(true);
                subtextArea.setWrapStyleWord(true);
                subtextArea.setEditable(false);
                subtextArea.setBackground(Color.WHITE);
                subtextArea.setPreferredSize(new Dimension(400, 200));
                subtextArea.setMaximumSize(new Dimension(400, 200));
                subtextArea.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
                JButton commentLikesButton = new JButton("\uD83D\uDC4D" + commentLikes);
                commentLikesButton.setName("commentnotliked");
                JButton commentDislikesButton = new JButton("\uD83D\uDC4E" + commentDislikes);
                commentDislikesButton.setName("commentnotdisliked");
                buttonPanel.add(commentLikesButton);
                buttonPanel.add(commentDislikesButton);
                if (client.getUsername().equals(commentAuthor)) {
                    JButton deleteComment = new JButton("\uD83D\uDDD1");
                    deleteComment.setName("notdeleted");
                    buttonPanel.add(deleteComment);
                    deleteComment.addActionListener(actionListener);
                } else {
                    JButton authorButton = new JButton(commentAuthor);
                    authorButton.setName("notauthor:" + commentAuthor);
                    buttonPanel.add(authorButton);
                    authorButton.addActionListener(actionListener);
                }

                commentLikesButton.addActionListener(actionListener);
                commentDislikesButton.addActionListener(actionListener);

                commentPanel.add(buttonPanel, BorderLayout.EAST);
                commentPanel.add(subtextArea, BorderLayout.WEST);
                commentPanel.setPreferredSize(new Dimension(600, 100));
                commentPanel.setMaximumSize(new Dimension(600, 100));

                commentPanel.setName(String.format("%s`%s`%s`%s", comment.get(0), comment.get(1), comment.get(2), comment.get(3)));

                comments.add(commentPanel);
            }
            if (comments.isEmpty()) {
                throw new Exception();
            }
        } catch (Exception e) {
            // add a bottom Panel that states no comments
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            JLabel titleLabel = new JLabel("No Comments on post");
            panel.add(titleLabel);
            panel.setPreferredSize(new Dimension(600, 100));
            panel.setMaximumSize(new Dimension(600, 100));
            panel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));
            comments.add(panel);
        }
    }
}
