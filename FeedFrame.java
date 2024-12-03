import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FeedFrame extends JComponent implements Runnable {
    SMClient client;
    JFrame feedFrame;
    ArrayList Posts = new ArrayList();

    FeedFrame(SMClient client) {
        this.client = client;
    }

    @Override
    public void run() {
        feedFrame = new JFrame("Feed");
        Container contentPane = feedFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        feedFrame.setLocationRelativeTo(null);
        feedFrame.setVisible(true);

    }
}
