package Views;

import Controllers.LoginController;
import Models.Images.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private static LoginPanel loginPanel = new LoginPanel();

    private Toolkit tk = Toolkit.getDefaultToolkit();

    private LoginPanel() {
        setSize(tk.getScreenSize().width, tk.getScreenSize().height);
        setLayout(null);

        usernameField = new JTextField();
        JLabel usernameLabel = new JLabel("Username : ");

        passwordField = new JPasswordField();
        JLabel passwordLabel = new JLabel("Password : ");

        JButton signInButton = new JButton("Sign In");
        JButton signUpButton = new JButton("Sign Up");

        JButton exitButton = new JButton("Exit");

        // ----- SETTING SIZE ----- //

        usernameField.setSize(250, 50);
        passwordField.setSize(250, 50);
        signInButton.setSize(170, 50);
        signUpButton.setSize(170, 50);
        usernameLabel.setSize(150, 50);
        passwordLabel.setSize(150, 50);
        exitButton.setSize(90 , 40);

        // ----- FONT COLOR ----- //

        usernameLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);

        // ----- FONT ----- //

        Font f = new Font("serif", Font.PLAIN, 22);

        usernameLabel.setFont(f);
        passwordLabel.setFont(f);
        usernameField.setFont(f);
        passwordField.setFont(f);

        f = new Font("serif", Font.PLAIN, 24);

        signInButton.setFont(f);
        signUpButton.setFont(f);

        // ----- BORDERS ----- //

        usernameField.setBorder(BorderFactory.createEmptyBorder());
        passwordField.setBorder(BorderFactory.createEmptyBorder());
        signInButton.setBorder(BorderFactory.createEmptyBorder());
        signUpButton.setBorder(BorderFactory.createEmptyBorder());

        // ----- SETTING LOCATION ----- //

        usernameField.setLocation(tk.getScreenSize().width / 2 - 125, tk.getScreenSize().height / 2 - 60);
        usernameLabel.setLocation(tk.getScreenSize().width / 2 - 250, tk.getScreenSize().height / 2 - 60);
        passwordField.setLocation(tk.getScreenSize().width / 2 - 125, tk.getScreenSize().height / 2);
        passwordLabel.setLocation(tk.getScreenSize().width / 2 - 250, tk.getScreenSize().height / 2);
        signUpButton.setLocation(tk.getScreenSize().width / 2 - 50, tk.getScreenSize().height / 2 + 70);
        signInButton.setLocation(tk.getScreenSize().width / 2 - 250, tk.getScreenSize().height / 2 + 70);
        exitButton.setLocation(0 , 0);

        // ----- ADDING Listeners ----- //

        LoginController loginController = new LoginController();

        signInButton.addActionListener(loginController);
        signUpButton.addActionListener(loginController);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });

        // ----- ADDING COMPONENTS ----- //

        add(usernameField);
        add(usernameLabel);
        add(passwordField);
        add(passwordLabel);
        add(signInButton);
        add(signUpButton);
        add(exitButton);
    }

    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(ImageLoader.loadingPage, 0, 0, null);

    }

    public String getUsername(){
        return usernameField.getText();
    }

    public String getPassword(){
        return new String(passwordField.getPassword());
    }

    public static LoginPanel getLoginPanel() {
        return loginPanel;
    }
}
