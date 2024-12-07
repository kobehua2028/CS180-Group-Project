import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * CS180 Group Project
 * A frame class that displays info about other users
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author Levin Cozza
 * @version Dec 07, 2024
 */

public class OtherProfileFrame extends JComponent implements Runnable, OtherProfileFrameInterface {

    SMClient client;
    FeedFrame feedFrame;
    String name = "No user";
    String aboutMe = "No about me";
    String[] friends;
    String[] blocks;
    String[] postTitles;
    JFrame profileFrame;
    boolean friendsWith = false;
    boolean blocked = false;
    JButton friendButton = new JButton("");
    JButton blockButton = new JButton("");


    public OtherProfileFrame(FeedFrame feedFrame, SMClient client, String profileUsername) {
        this.feedFrame = feedFrame;
        this.client = client;
        this.name = profileUsername;
        ArrayList<String[]> profile = new ArrayList<>();
        try {
            profile = client.displayProfile(profileUsername);
         } catch (IOException e) {
            e.printStackTrace();
        }
        this.aboutMe = profile.get(3)[0];
        this.friends = profile.get(0);
        this.blocks = profile.get(1);
        this.postTitles = profile.get(2);

        ArrayList<String[]> viewerProfile = new ArrayList<>();
        try {
            viewerProfile = client.displayProfile(client.getUsername());
            for (int i = 0; i < viewerProfile.get(0).length; i++) {
                try {
                    if (viewerProfile.get(0)[i].equals(name)) {
                        friendsWith = true;
                    }
                } catch (NullPointerException e) {
                    friendsWith = false;
                }
            }
            for (int i = 0; i < viewerProfile.get(1).length; i++) {
                try {
                    if (viewerProfile.get(1)[i].equals(name)) {
                        blocked = true;
                    }
                } catch (NullPointerException e) {
                    blocked = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
                JButton buttonClicked = (JButton) e.getSource();
                if (buttonClicked.getText().equals("Unblock")) {
                    if (blocked && !name.equals("No user")) {
                        try {
                            client.unblockUser(name);
                            blocked = false;
                            friendButton.setText("Add Friend");
                            friendButton.setVisible(true);
                            blockButton.setText("Block");
                            feedFrame.run();
                        } catch (IOException e1) {
                            JOptionPane.showMessageDialog(null, "Failed to unblock user", "Error", JOptionPane.ERROR_MESSAGE);                        }
                    }
                } else if (buttonClicked.getText().equals("Block")) {
                    if (!blocked && !name.equals("No user")) {
                        try {
                            client.blockUser(name);
                            blocked = true;
                            friendsWith = false;
                            friendButton.setText("");
                            friendButton.setVisible(false);
                            blockButton.setText("Unblock");
                            feedFrame.run();
                        } catch (IOException e1) {
                            JOptionPane.showMessageDialog(null, "Failed to block user", "Error", JOptionPane.ERROR_MESSAGE);                        }
                    }
                }
                if (buttonClicked.getText().equals("Add Friend")) {
                    if (!name.equals("No user")) {
                        if (blocked) {
                            JOptionPane.showMessageDialog(null, "You have blocked this user, first unblock them to add them as a friend", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        try {
                            client.addFriend(name);
                            friendsWith = true;
                            friendButton.setText("Remove Friend");
                            friendButton.setVisible(true);
                            blockButton.setText("Block");
                            feedFrame.run();
                        } catch (IOException e1) {
                            JOptionPane.showMessageDialog(null, "Failed to add user", "Error", JOptionPane.ERROR_MESSAGE);                        }
                    }
                } else if (buttonClicked.getText().equals("Remove Friend")) {
                    if (!name.equals("No user")) {
                        try {
                            client.deleteFriend(name);
                            friendsWith = false;
                            friendButton.setText("Remove Friend");
                            friendButton.setVisible(true);
                            blockButton.setText("Block");
                            feedFrame.run();
                        } catch (IOException e1) {
                            JOptionPane.showMessageDialog(null, "Failed to add user", "Error", JOptionPane.ERROR_MESSAGE);                        }
                    }
                }
            }

        }
    };

    @Override
    public void run() {
        profileFrame = new JFrame("Profile Preview");
        profileFrame.setLayout(new BorderLayout());
        profileFrame.setSize(400, 600);
        profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        profileFrame.setLocationRelativeTo(null);
        profileFrame.setResizable(false);

        JPanel topProfilePanel = new JPanel();
        topProfilePanel.setLayout(new BoxLayout(topProfilePanel, BoxLayout.Y_AXIS));
        topProfilePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel(this.name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Larger and bold font
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setPreferredSize(new Dimension(400, 30));
        nameLabel.setMaximumSize(new Dimension(400, 30));


        JTextArea aboutMeTextArea = new JTextArea(this.aboutMe);
        aboutMeTextArea.setFont(new Font("Arial", Font.PLAIN, 12)); // Smaller text
        aboutMeTextArea.setLineWrap(true);
        aboutMeTextArea.setWrapStyleWord(true);
        aboutMeTextArea.setEditable(false);
        aboutMeTextArea.setPreferredSize(new Dimension(400, 100)); // Reduced height
        aboutMeTextArea.setMaximumSize(new Dimension(400, 100));
        aboutMeTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutMeTextArea.setBackground(new Color(245, 245, 245));

        JPanel profileButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        if (friendsWith && !blocked) {
            friendButton.setText("Remove Friend");
            blockButton.setText("Block");
        } else if (!friendsWith && blocked) {
            blockButton.setText("Unblock");
        } else if (!friendsWith && !blocked) {
            friendButton.setText("Add Friend");
            blockButton.setText("Block");
        }

        profileButtonPanel.add(friendButton);
        profileButtonPanel.add(blockButton);

        profileButtonPanel.revalidate();
        profileButtonPanel.repaint();

        if (!friendsWith && blocked) {
            friendButton.setVisible(false);
        }

        friendButton.addActionListener(actionListener);
        blockButton.addActionListener(actionListener);

        topProfilePanel.add(nameLabel);
        topProfilePanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        topProfilePanel.add(aboutMeTextArea);
        topProfilePanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        topProfilePanel.add(profileButtonPanel);

        JPanel friendsPanel = new JPanel();
        friendsPanel.setLayout(new BoxLayout(friendsPanel, BoxLayout.Y_AXIS));
        friendsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel friendsLabel = new JLabel("Friends");
        friendsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        friendsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        friendsLabel.setPreferredSize(new Dimension(400, 30));
        friendsLabel.setMaximumSize(new Dimension(400, 30));

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name", friends);
        JTable friendsTable = new JTable(model);
        JScrollPane friendsScrollPane = new JScrollPane(friendsTable);
        friendsScrollPane.setPreferredSize(new Dimension(400, 200));
        friendsScrollPane.setMaximumSize(new Dimension(400, 200));

        friendsPanel.add(friendsLabel);
        friendsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        friendsPanel.add(friendsScrollPane);

        profileFrame.add(topProfilePanel, BorderLayout.NORTH);
        profileFrame.add(friendsPanel, BorderLayout.CENTER);
        profileFrame.setVisible(true);
    }

}
