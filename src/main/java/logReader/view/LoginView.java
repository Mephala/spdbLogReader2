package logReader.view;

import logReader.main.Main;
import logReader.manager.LogManager;
import logReader.session.ApplicationSession;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by GokhanOzgozen on 12/29/2015.
 */
public class LoginView extends JFrame {
    private JTextField ipField;
    private JTextField portField;
    private JTextField usernameField;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JPanel loginPanel;


    public LoginView() {
        super("Login to DB");
        setContentPane(loginPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButton.setEnabled(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String ip = ipField.getText();
                            String username = usernameField.getText();
                            String password = new String(passwordField1.getPassword());
                            String port = portField.getText();
                            LogManager logManager = new LogManager(ip, username, password, port);
                            ApplicationSession.logManager = logManager;
                            Main.loginSuccess();
                        } catch (Throwable t) {
                            t.printStackTrace();
                            JOptionPane.showMessageDialog(null, t.getMessage(), "Failed to connect", JOptionPane.ERROR_MESSAGE);
                        } finally {
                            loginButton.setEnabled(true);
                        }
                    }
                }).start();
            }
        });
    }

}
