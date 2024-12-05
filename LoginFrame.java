import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginFrame implements Runnable {

    SMClient client;
    JFrame frame;
    JLabel usernameLabel;
    JLabel passwordLabel;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;
    JButton registerButton;

    public LoginFrame(SMClient smClient) {
        client = smClient;
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = "";
            String password = "";
            try {
                username = usernameField.getText();
                char[] rawPassword = passwordField.getPassword();
                StringBuilder passwordBuilder = new StringBuilder();
                passwordBuilder.append(rawPassword);
                password = passwordBuilder.toString();
            } catch (NullPointerException e1) {
                JOptionPane.showMessageDialog(frame, "Username or password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (e.getSource() == loginButton) {
                try {
                    if (client.login(username, password)) {
                        // invoke newsfeed
                        SwingUtilities.invokeLater(new FeedFrame(client));
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid username or password");
                    }
                } catch (IOException e1) {
                        JOptionPane.showMessageDialog(frame, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (e.getSource() == registerButton) {
                try {
                    if (client.createUser(username, password, "I love long walks on the beach")) {
                        frame.dispose();
                        SwingUtilities.invokeLater(new FeedFrame(client));
                    }
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(frame, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    };

    public void run() {
        frame = new JFrame("Social Media App");
        frame.setSize(600, 300);
        Container frameContentPane = frame.getContentPane();
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        usernameLabel.setBounds(150, 30, 100, 30);
        usernameField.setBounds(300, 50, 150, 30);
        passwordLabel.setBounds(150, 80, 100, 30);
        passwordField.setBounds(300, 80, 150, 30);
        loginButton.setBounds(180, 160, 100, 30);
        registerButton.setBounds(290, 160, 100, 30);

        loginButton.addActionListener(actionListener);
        registerButton.addActionListener(actionListener);

        frameContentPane.add(usernameLabel);
        frameContentPane.add(usernameField);
        frameContentPane.add(passwordLabel);
        frameContentPane.add(passwordField);
        frameContentPane.add(loginButton);
        frameContentPane.add(registerButton);

        frame.setVisible(true);

    }

}
