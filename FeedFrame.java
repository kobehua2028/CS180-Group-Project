import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
<<<<<<< HEAD
<<<<<<< HEAD
import java.awt.event.ActionEvent;
=======
>>>>>>> 3732f3f7acaa92a834cd7623bfad11f94461d57f
=======
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
>>>>>>> e2fc26b2b75db0db16f7da918241aad9edf3fe2d
import java.io.IOException;
import java.util.ArrayList;

public class FeedFrame extends JComponent implements Runnable {
    SMClient client;
    JFrame feedFrame;
<<<<<<< HEAD
    JButton searchButton;
    JTextField searchField;
    JButton profileButton;
    JButton createPostButton;
    JScrollPane jScrollPane;
    ArrayList Posts = new ArrayList();
=======
    ArrayList<JPanel> posts = new ArrayList<>();
>>>>>>> 3732f3f7acaa92a834cd7623bfad11f94461d57f

    FeedFrame(SMClient client) {
        this.client = client;
    }

<<<<<<< HEAD
    public void actionPerformed(ActionEvent e) throws IOException {
        if (e.getSource() == searchButton) {
            try {
                if (client.searchUser(searchField.getText())) {
                    SwingUtilities.invokeLater(new SearchFrame(client));
                    feedFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(feedFrame, "Invalid username or password", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == profileButton) {
            SwingUtilities.invokeLater(new ProfileFrame(client));
            feedFrame.dispose();
        } else if (e.getSource() == createPostButton) {

        }
    }
=======
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
                JButton buttonClicked = (JButton) e.getSource();
                if (buttonClicked.getText().contains("\uD83D\uDC4D")) {
                    for (JPanel p : posts) {
                        if (buttonClicked.getParent().getParent().getName().equals(p.getName())) {
                            try {
                                if (buttonClicked.getName().equals("notliked") && client.likePost(p.getName())) {
                                    buttonClicked.setText("\uD83D\uDC4D" + String.valueOf(Integer.parseInt(buttonClicked.getText().replaceAll("[^0-9]", "")) + 1));
                                    buttonClicked.setName("liked");
                                } else if (buttonClicked.getName().equals("liked") && client.unlikePost(p.getName())) {
                                    buttonClicked.setText("\uD83D\uDC4D" + String.valueOf(Integer.parseInt(buttonClicked.getText().replaceAll("[^0-9]", "")) - 1));
                                    buttonClicked.setName("notliked");
                                }
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
                if (buttonClicked.getText().contains("\uD83D\uDC4E")) {
                    for (JPanel p : posts) {
                        if (buttonClicked.getParent().getParent().getName().equals(p.getName())) {
                            try {
                                if (buttonClicked.getName().equals("notdisliked") && client.dislikePost(p.getName())) {
                                    buttonClicked.setText("\uD83D\uDC4E" + String.valueOf(Integer.parseInt(buttonClicked.getText().replaceAll("[^0-9]", "")) + 1));
                                    buttonClicked.setName("disliked");
                                } else if (buttonClicked.getName().equals("disliked") && client.undislikePost(p.getName())) {
                                    buttonClicked.setText("\uD83D\uDC4E" + String.valueOf(Integer.parseInt(buttonClicked.getText().replaceAll("[^0-9]", "")) - 1));
                                    buttonClicked.setName("notdisliked");
                                }
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
                if (buttonClicked.getText().contains("\uD83D\uDDD1")) {
                    for (JPanel p : posts) {
                        if (buttonClicked.getParent().getParent().getName().equals(p.getName())) {
                            try {
                                if (buttonClicked.getName().equals("nothidden")) {
                                    buttonClicked.getParent().getParent().setVisible(false);
                                    System.out.println("1");
                                    if (client.hidePost(p.getName())) {
                                        buttonClicked.setName("hidden");
                                        System.out.println("2");
                                    }
                                }
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
//                if (buttonClicked.getText().contains("\uD83D\uDCAC")) {
//                    for (JPanel p : posts) {
//                        if(buttonClicked.getParent().getParent().getName().equals(p.getName())) {
//                            SwingUtilities.invokeLater(new FocusPostFrame(client, buttonClicked.getParent().getParent().getName()))
//                            buttonClicked.setName("commentclicked");
//                        }
//                    }
//                }
            }
        }
    };
>>>>>>> e2fc26b2b75db0db16f7da918241aad9edf3fe2d

    @Override
    public void run() {
        feedFrame = new JFrame("Feed");
        feedFrame.setLayout(new BorderLayout());
        feedFrame.setSize(1280, 720);
        feedFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        feedFrame.setLocationRelativeTo(null);

        // Top panel with buttons
        JPanel topPanel = new JPanel();
<<<<<<< HEAD
        topPanel.setLayout(new FlowLayout());
<<<<<<< HEAD
        searchButton = new JButton("Search");
        searchField = new JTextField();
        profileButton = new JButton("Profile");
        createPostButton = new JButton("Create Post");
        topPanel.add(searchButton);
        topPanel.add(searchField);
        topPanel.add(profileButton);
        topPanel.add(createPostButton);
        contentPane.add(topPanel, BorderLayout.NORTH);

        jScrollPane = new JScrollPane(jTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        feedFrame.setLocationRelativeTo();
=======
=======
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
>>>>>>> e2fc26b2b75db0db16f7da918241aad9edf3fe2d
        JButton searchButton = new JButton("Search");
        JButton profileButton = new JButton("Profile");
        JButton createPostButton = new JButton("Create post");
        topPanel.add(searchButton);
        topPanel.add(profileButton);
        topPanel.add(createPostButton);

        // Scrollable feed panel
        JPanel feed = new JPanel();
        feed.setLayout(new BoxLayout(feed, BoxLayout.Y_AXIS));
        loadPosts();
        for (JPanel panel : posts) {
            feed.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between posts
            panel.setPreferredSize(new Dimension(500,300));
            panel.setMaximumSize(new Dimension(500,300));
            feed.add(panel);
        }
<<<<<<< HEAD

        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(feed, BorderLayout.CENTER);

        feedFrame.setLocationRelativeTo(null);
>>>>>>> 3732f3f7acaa92a834cd7623bfad11f94461d57f
=======
        JScrollPane scrollPane = new JScrollPane(feed);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        // Add components to frame
        feedFrame.add(topPanel, BorderLayout.NORTH);
        feedFrame.add(scrollPane, BorderLayout.CENTER);
>>>>>>> e2fc26b2b75db0db16f7da918241aad9edf3fe2d
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
                        BorderFactory.createLineBorder(Color.GRAY, 1),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
                postPanel.setBackground(Color.WHITE);
                postPanel.setName(post.get(0));

                // Top: Title and Author
                JPanel topPanel = new JPanel(new BorderLayout());
                topPanel.setOpaque(false);
                JLabel titleLabel = new JLabel(post.get(0));
                titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
                JButton authorButton = new JButton("By " + post.get(2));
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
                JButton likesButton = new JButton("\uD83D\uDC4D " + post.get(4));
                likesButton.setName("notliked");
                JButton dislikesButton = new JButton("\uD83D\uDC4E " + post.get(5));
                dislikesButton.setName("notdisliked");
                JButton commentsButton = new JButton("\uD83D\uDCAC " + post.get(3));
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

                posts.add(postPanel);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(feedFrame, "No posts to show", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void enableDarkMode() {
        UIManager.put("Panel.background", new ColorUIResource(43, 43, 43)); // Dark gray
        UIManager.put("Panel.foreground", new ColorUIResource(187, 187, 187)); // Light gray
        UIManager.put("Label.foreground", new ColorUIResource(187, 187, 187)); // Light gray
        UIManager.put("Button.background", new ColorUIResource(60, 63, 65)); // Darker gray for buttons
        UIManager.put("Button.foreground", new ColorUIResource(187, 187, 187)); // Light gray
        UIManager.put("TextArea.background", new ColorUIResource(43, 43, 43)); // Dark gray for text area
        UIManager.put("TextArea.foreground", new ColorUIResource(187, 187, 187)); // Light gray for text
        UIManager.put("ScrollPane.background", new ColorUIResource(43, 43, 43)); // Dark gray for scroll pane
        UIManager.put("ScrollBar.thumb", new ColorUIResource(60, 63, 65)); // Scrollbar thumb
        UIManager.put("ScrollBar.background", new ColorUIResource(43, 43, 43)); // Scrollbar track
        UIManager.put("BorderFactory.lineBorder", new ColorUIResource(60, 63, 65)); // Borders
    }
}
