import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePostFrame extends JComponent implements Runnable {
    JFrame createPostframe;
    private SMClient client;
    public CreatePostFrame(SMClient client) {
        this.client = client;
    }

    private ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {

        }
    };

    public void run() {
        createPostframe = new JFrame("Create Post");
        createPostframe.setLayout(new BoxLayout(createPostframe, BoxLayout.Y_AXIS));
        createPostframe.setSize(600, 720);
        createPostframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createPostframe.setLocationRelativeTo(null);
        createPostframe.setForeground(new Color(171,171,171));
        createPostframe.setBackground(new Color(23,23,23));
        createPostframe.setResizable(false);

        JLabel title = new JLabel("Post Title:");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField titleField = new JTextField();
        titleField.setPreferredSize(new Dimension(500, 30));

        JLabel subText = new JLabel("Post Subtext:");
        subText.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField subTextField = new JTextField();
        subTextField.setPreferredSize(new Dimension(500, 200));

        createPostframe.setVisible(true);
    }

}
