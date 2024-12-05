import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ProfileFrame extends JComponent implements Runnable {

    SMClient client;
    String name = "No user";
    String aboutMe = "No about me";
    String[] friends;
    String[] blocks;
    String[] postTitles;
    JFrame profileFrame;
    boolean friendsWith;

    public ProfileFrame(SMClient client, String profileUsername) {
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

    }

    @Override
    public void run() {
        profileFrame = new JFrame("Feed");
        profileFrame.setLayout(new BorderLayout());
        profileFrame.setSize(900, 600);
        profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        profileFrame.setLocationRelativeTo(null);
        profileFrame.setResizable(false);

        JPanel topProfilePanel = new JPanel();
        topProfilePanel.setLayout(new BoxLayout(topProfilePanel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel(this.name);
        JTextArea aboutMeTextArea = new JTextArea(this.aboutMe);

        topProfilePanel.add(nameLabel);
        topProfilePanel.add(aboutMeTextArea);

        JPanel friendsPanel = new JPanel();
        friendsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name", friends);
        JTable friendsTable = new JTable(model);
        friendsPanel.add(friendsTable);

        profileFrame.add(topProfilePanel, BorderLayout.NORTH);
        profileFrame.add(friendsPanel, BorderLayout.CENTER);
        profileFrame.setVisible(true);
    }
}