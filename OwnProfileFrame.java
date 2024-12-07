import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class OwnProfileFrame extends JComponent implements Runnable {

    SMClient client;
    String name = "No user";
    String aboutMe = "No about me";
    String[] friends;
    String[] blocks;
    String[] hiddenPosts;
    JFrame profileFrame;

    public OwnProfileFrame(SMClient client) {
        this.client = client;
        try {
            this.name = client.getUsername();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String[]> profile = new ArrayList<>();
        try {
            profile = client.displayProfile(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.aboutMe = profile.get(4)[0];
        this.friends = profile.get(0);
        this.blocks = profile.get(1);
        this.hiddenPosts = profile.get(3);
    }

    private ActionListener actionListener = new ActionListener() {

    }

    @Override
    public void run() {
        profileFrame = new JFrame("Profile Preview");
        profileFrame.setLayout(new BorderLayout());
        profileFrame.setSize(800, 600);
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
        JButton removeSelectedFriendsButton = new JButton("Remove selected friends");
        JButton unblockSelectedBlocksButton = new JButton("Unblock selected users");

        // TODO: ADD FUNCTIONALITY FOR THESE BUTTONS ABOVE

        profileButtonPanel.add(removeSelectedFriendsButton);
        profileButtonPanel.add(unblockSelectedBlocksButton);

        topProfilePanel.add(nameLabel);
        topProfilePanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        topProfilePanel.add(aboutMeTextArea);
        topProfilePanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        topProfilePanel.add(profileButtonPanel);

        JPanel friendsBlocksPanel = new JPanel();
        friendsBlocksPanel.setLayout(new BoxLayout(friendsBlocksPanel, BoxLayout.Y_AXIS));
        friendsBlocksPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel friendsLabel = new JLabel("Friends");
        friendsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        friendsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        friendsLabel.setPreferredSize(new Dimension(400, 30));
        friendsLabel.setMaximumSize(new Dimension(400, 30));

        DefaultTableModel friendModel = new DefaultTableModel();
        friendModel.addColumn("Name", friends);
        JTable friendsTable = new JTable(friendModel);
        friendsTable.setDefaultEditor(Object.class, null);
        JScrollPane friendsScrollPane = new JScrollPane(friendsTable);
        friendsScrollPane.setPreferredSize(new Dimension(400, 200));
        friendsScrollPane.setMaximumSize(new Dimension(400, 200));

        JLabel blocksLabel = new JLabel("Blocks");
        blocksLabel.setFont(new Font("Arial", Font.BOLD, 18));
        blocksLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        blocksLabel.setPreferredSize(new Dimension(400, 30));
        blocksLabel.setMaximumSize(new Dimension(400, 30));

        DefaultTableModel blockModel = new DefaultTableModel();
        blockModel.addColumn("Name", blocks);
        JTable blocksTable = new JTable(blockModel);
        blocksTable.setDefaultEditor(Object.class, null);
        JScrollPane blocksScrollPane = new JScrollPane(blocksTable);
        friendsScrollPane.setPreferredSize(new Dimension(400, 200));
        friendsScrollPane.setMaximumSize(new Dimension(400, 200));

        friendsBlocksPanel.add(friendsLabel);
        friendsBlocksPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        friendsBlocksPanel.add(friendsScrollPane);

        friendsBlocksPanel.add(blocksLabel);
        friendsBlocksPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        friendsBlocksPanel.add(blocksScrollPane);

        profileFrame.add(topProfilePanel, BorderLayout.NORTH);
        profileFrame.add(friendsBlocksPanel, BorderLayout.CENTER);
        profileFrame.setVisible(true);
    }

}