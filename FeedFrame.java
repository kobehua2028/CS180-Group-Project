import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class FeedFrame extends JComponent implements Runnable {
    SMClient client;
    JFrame feedFrame;
    ArrayList<JPanel> posts = new ArrayList<>();

    FeedFrame(SMClient client) {
        this.client = client;
    }

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

    @Override
    public void run() {
        feedFrame = new JFrame("Feed");
        feedFrame.setLayout(new BorderLayout());
        feedFrame.setSize(1280, 720);
        feedFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        feedFrame.setLocationRelativeTo(null);

        // Top panel with buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
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
        JScrollPane scrollPane = new JScrollPane(feed);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        // Add components to frame
        feedFrame.add(topPanel, BorderLayout.NORTH);
        feedFrame.add(scrollPane, BorderLayout.CENTER);
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
