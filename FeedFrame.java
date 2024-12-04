import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

public class FeedFrame extends JComponent implements Runnable {
    SMClient client;
    JFrame feedFrame;
    JButton searchButton;
    JTextField searchField;
    JButton profileButton;
    JButton createPostButton;
    JScrollPane jScrollPane;
    ArrayList Posts = new ArrayList();

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
        feedFrame = new JFrame("Feed");
        Container contentPane = feedFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
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
        feedFrame.setVisible(true);

    }
}
