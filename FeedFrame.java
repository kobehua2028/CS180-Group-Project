import javax.swing.*;
import java.awt.*;
<<<<<<< HEAD
import java.awt.event.ActionEvent;
=======
>>>>>>> 3732f3f7acaa92a834cd7623bfad11f94461d57f
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

    @Override
    public void run() {
//        feedFrame = new JFrame("Feed");
//        Container contentPane = feedFrame.getContentPane();
//        contentPane.setLayout(new BorderLayout());
//
//        JFrame frame;
//
//        JPanel topMenuPanel = new JPanel();
//
//        JPanel feedPanel;
//        ArrayList<JPanel> posts = new ArrayList<>();
//
//        JPanel bottomMenuPanel = new JPanel();
//
//        JLabel postTitle;
//        JLabel postSubtext;
//        JLabel author;
//        JButton likeButton;
//        JButton dislikeButton;
//        JButton viewCommentsButton;
//        JButton hidePostButton;
//        JButton deletePostButton;

//        feedFrame.setLocationRelativeTo(null);
//        feedFrame.setVisible(true);

        feedFrame = new JFrame("Feed");
        feedFrame.setSize(1000, 800);
        Container contentPane = feedFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
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
        JButton searchButton = new JButton("Search");
        JButton profileButton = new JButton("Profile");
        JButton createPostButton = new JButton("Create post");
        topPanel.add(searchButton);
        topPanel.add(profileButton);
        topPanel.add(createPostButton);

        loadPosts();
        JPanel feed = new JPanel();
        feed.setLayout(new BoxLayout(feed, BoxLayout.Y_AXIS));
        for (JPanel panel : posts) {
            panel.setVisible(true);
            feed.add(panel);
        }

        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(feed, BorderLayout.CENTER);

        feedFrame.setLocationRelativeTo(null);
>>>>>>> 3732f3f7acaa92a834cd7623bfad11f94461d57f
        feedFrame.setVisible(true);

    }

    public void loadPosts() {
        try {
            posts.clear();
            ArrayList<ArrayList<String>> clientPosts = client.displayPosts();
            System.out.println(clientPosts);
            for (ArrayList<String> post : clientPosts) {
                JPanel postPanel = new JPanel();
                postPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
                postPanel.setLayout(new BorderLayout());
                postPanel.setOpaque(true);
                postPanel.setBackground(Color.LIGHT_GRAY);
                postPanel.setPreferredSize(new Dimension(600, 200));
                postPanel.setMaximumSize(new Dimension(600, 200));
                String title = post.get(0);
                String subtext = post.get(1);
                String author = post.get(2);
                int comments = Integer.parseInt(post.get(3));
                int likes = Integer.parseInt(post.get(4));
                int dislikes = Integer.parseInt(post.get(5));

                JLabel titleLabel = new JLabel(title);
                JLabel subtextLabel = new JLabel(subtext);
                JButton authorButton = new JButton(author);
                JButton commentsButton = new JButton("\uD83D\uDCAC" + String.valueOf(comments));
                JButton likesButton = new JButton("\uD83D\uDC4D" + String.valueOf(likes));
                JButton dislikesButton = new JButton("\uD83D\uDC4E" + String.valueOf(dislikes));
                JButton hidePostButton = new JButton("\uD83D\uDDD1" + String.valueOf(comments));

                JPanel bottomBar = new JPanel();
                bottomBar.setOpaque(false);
                bottomBar.setLayout(new FlowLayout());
                bottomBar.add(likesButton);
                bottomBar.add(dislikesButton);
                bottomBar.add(commentsButton);
                bottomBar.add(hidePostButton);

                JPanel topPanel = new JPanel();
                topPanel.setOpaque(false);
                topPanel.setLayout(new FlowLayout());
                topPanel.add(titleLabel);
                topPanel.add(authorButton);

                JPanel centerPanel = new JPanel();
                centerPanel.setOpaque(false);
                centerPanel.setLayout(new FlowLayout());
                centerPanel.add(subtextLabel);

                postPanel.add(topPanel, BorderLayout.NORTH);
                postPanel.add(centerPanel, BorderLayout.CENTER);
                postPanel.add(bottomBar, BorderLayout.SOUTH);

                posts.add(postPanel);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(feedFrame, "No posts to show", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
