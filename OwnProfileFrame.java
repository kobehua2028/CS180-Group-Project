import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Arrays;
import java.util.List;

public class OwnProfileFrame extends JComponent implements Runnable {

    SMClient client;
    String name = "No user";
    String aboutMe = "No about me";
    String[] friends;
    String[] blocks;
    String[] hiddenPosts;
    JFrame profileFrame;
    JTextArea aboutMeTextArea = new JTextArea();
    JList<String> friendsList = new JList<>();
    JList<String> blocksList = new JList<>();
    JList<String> hiddenList = new JList<>();


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
        this.aboutMe = profile.get(3)[0];
        this.friends = profile.get(0);
        this.blocks = profile.get(1);
        this.hiddenPosts = profile.get(4);

        System.out.println(profile.toString());
        System.out.println(Arrays.asList(friends).toString());
        System.out.println(Arrays.asList(blocks).toString());
        System.out.println(Arrays.asList(hiddenPosts).toString());
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
                JButton buttonClicked = (JButton) e.getSource();
                if (buttonClicked.getText().equals("Edit About Me")) {
                    buttonClicked.setText("Save Edited About Me");
                    aboutMeTextArea.setEditable(true);
                } else if (buttonClicked.getText().equals("Save Edited About Me")) {
                    aboutMeTextArea.setEditable(false);
                    try {
                        if (!client.changeAboutMe(client.getUsername(), aboutMeTextArea.getText())) {
                            JOptionPane.showMessageDialog(profileFrame, "Failed to change about me", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(profileFrame, "Failed to change about me", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    buttonClicked.setText("Edit About Me");
                }
                if (buttonClicked.getText().equals("Remove selected friends")) {
                    //
                }
                if (buttonClicked.getText().equals("Unblock selected users")) {
                    //
                }
                if (buttonClicked.getText().equals("Unhide selected posts")) {
                    //
                    feed
                }
            }
        }
        //
    };

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


        aboutMeTextArea = new JTextArea(this.aboutMe);
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
        JButton unhideSelectedPostsButton = new JButton("Unhide selected posts");

        // TODO: ADD FUNCTIONALITY FOR THESE BUTTONS ABOVE

        JButton changeAboutMeButton = new JButton("Edit About Me");

        profileButtonPanel.add(removeSelectedFriendsButton);
        profileButtonPanel.add(unblockSelectedBlocksButton);
        profileButtonPanel.add(changeAboutMeButton);

        removeSelectedFriendsButton.addActionListener(actionListener);
        unblockSelectedBlocksButton.addActionListener(actionListener);
        unhideSelectedPostsButton.addActionListener(actionListener);
        changeAboutMeButton.addActionListener(actionListener);

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

        DefaultListModel<String> friendModel = new DefaultListModel<>();
        for (String friend : friends) {
            friendModel.addElement(friend);
        }
        friendsList = new JList<>(friendModel);
        friendsList.setVisibleRowCount(10);
        friendsList.setFixedCellHeight(30);
        friendsList.setFixedCellWidth(200);
        friendsList.setFont(new Font("Arial", Font.PLAIN, 16));
        friendsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane friendsScrollPane = new JScrollPane(friendsList);
        friendsScrollPane.setPreferredSize(new Dimension(400, 200));
        friendsScrollPane.setMaximumSize(new Dimension(400, 200));
        friendsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JLabel blocksLabel = new JLabel("Blocks");
        blocksLabel.setFont(new Font("Arial", Font.BOLD, 18));
        blocksLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        blocksLabel.setPreferredSize(new Dimension(400, 30));
        blocksLabel.setMaximumSize(new Dimension(400, 30));

        DefaultListModel<String> blockModel = new DefaultListModel<>();
        for (String block : blocks) {
            blockModel.addElement(block);
        }
        blocksList = new JList<>(blockModel);
        blocksList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        blocksList.setVisibleRowCount(10);
        blocksList.setFixedCellHeight(30);
        blocksList.setFixedCellWidth(200);
        blocksList.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane blocksScrollPane = new JScrollPane(blocksList);
        blocksScrollPane.setPreferredSize(new Dimension(400, 200));
        blocksScrollPane.setMaximumSize(new Dimension(400, 200));
        blocksScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JLabel hiddenPostsLabel = new JLabel("Hidden Posts");
        hiddenPostsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        hiddenPostsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        hiddenPostsLabel.setPreferredSize(new Dimension(400, 30));
        hiddenPostsLabel.setMaximumSize(new Dimension(400, 30));

        DefaultListModel<String> hiddenModel = new DefaultListModel<>();
        for (String hidden : hiddenPosts) {
            hiddenModel.addElement(hidden);
        }
        hiddenList = new JList<>(hiddenModel);
        hiddenList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        hiddenList.setVisibleRowCount(10);
        hiddenList.setFixedCellHeight(30);
        hiddenList.setFixedCellWidth(200);
        hiddenList.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane hiddenScrollPane = new JScrollPane(hiddenList);
        hiddenScrollPane.setPreferredSize(new Dimension(400, 200));
        hiddenScrollPane.setMaximumSize(new Dimension(400, 200));
        hiddenScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        friendsBlocksPanel.add(friendsLabel);
        friendsBlocksPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        friendsBlocksPanel.add(friendsScrollPane);

        friendsBlocksPanel.add(blocksLabel);
        friendsBlocksPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        friendsBlocksPanel.add(blocksScrollPane);

        friendsBlocksPanel.add(hiddenPostsLabel);
        friendsBlocksPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        friendsBlocksPanel.add(hiddenScrollPane);

        profileFrame.add(topProfilePanel, BorderLayout.NORTH);
        profileFrame.add(friendsBlocksPanel, BorderLayout.CENTER);
        profileFrame.setVisible(true);
    }

}