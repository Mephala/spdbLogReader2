package logReader.main;

import logReader.util.LogReaderUtils;
import logReader.view.LoginView;

import javax.swing.*;

/**
 * Created by GokhanOzgozen on 12/24/2015.
 */
public class Main {


    private static LoginView loginView;

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            loginView = new LoginView();
            LogReaderUtils.centralizeJFrame(loginView);
            loginView.pack();
            loginView.setVisible(true);
        } catch (Throwable t) {
            t.printStackTrace();
            JOptionPane.showMessageDialog(null, "Fatal error on main process :" + t.getMessage(), "Fatal error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void loginSuccess() {
        try {
            loginView.setVisible(false);
            loginView.dispose();
            JOptionPane.showMessageDialog(null, "Mekintosh", "yeri", JOptionPane.INFORMATION_MESSAGE);
        } catch (Throwable t) {

        }
    }
}
