import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


/**
 * CS180 Group Project
 * A frame class to allow users to create posts
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author Kobe Huang
 * @version Dec 07, 2024
 */

public class CreatePostFrame extends JComponent implements Runnable {
    JFrame createPostFrame;
    private SMClient client;
    private FeedFrame feedFrame;

    public CreatePostFrame(SMClient client, FeedFrame feedFrame) {
        this.client = client;
        this.feedFrame = feedFrame;
    }

    public void run() {
        createPostFrame = new JFrame("Create Post");
        createPostFrame.setLayout(new BorderLayout());
        createPostFrame.setSize(500, 600);
        createPostFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createPostFrame.setLocationRelativeTo(null);
        createPostFrame.setResizable(false);

        // Main Panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        JLabel titleLabel = new JLabel("Post Title:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        mainPanel.add(titleLabel, gbc);

        // Title Text Field
        JTextArea titleField = new JTextArea(4, 30);
        titleField.setFont(new Font("Arial", Font.PLAIN, 14));
        titleField.setLineWrap(true);
        titleField.setWrapStyleWord(true);
        JScrollPane titleScroll = new JScrollPane(titleField);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        mainPanel.add(titleScroll, gbc);

        // Subtext Label
        JLabel subTextLabel = new JLabel("Post Subtext:");
        subTextLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        mainPanel.add(subTextLabel, gbc);

        // Subtext Text Field
        JTextArea subTextField = new JTextArea(12, 30);
        subTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        subTextField.setLineWrap(true);
        subTextField.setWrapStyleWord(true);
        JScrollPane subTextScroll = new JScrollPane(subTextField);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(subTextScroll, gbc);

        // Create Post Button
        JButton postButton = new JButton("Create Post");
        postButton.setName("CREATE_NEW_POST");
        postButton.setFont(new Font("Arial", Font.BOLD, 14));
        postButton.setBackground(new Color(50, 150, 250));
        postButton.setForeground(Color.BLACK);
        postButton.setFocusPainted(false);
        postButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JButton) {
                    JButton button = (JButton) e.getSource();
                    try {
                        if ("CREATE_NEW_POST".equals(button.getName())) {
                            client.createPost(titleField.getText(), subTextField.getText());
                            createPostFrame.dispose();
                            feedFrame.run();
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Create Post failed", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(postButton, gbc);

        createPostFrame.add(mainPanel, BorderLayout.CENTER);
        createPostFrame.setVisible(true);
    }
}
