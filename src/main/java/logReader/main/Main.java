package logReader.main;

import logReader.exception.LogReaderException;
import logReader.manager.LogManager;
import logReader.session.ApplicationSession;
import logReader.view.LogView;
import logReader.view.LoginView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;

/**
 * Created by GokhanOzgozen on 12/24/2015.
 */
public class Main {


    private static LoginView loginView;

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            test();
//            loginView = new LoginView();
//            LogReaderUtils.centralizeJFrame(loginView);
//            loginView.pack();
//            loginView.setVisible(true);
        } catch (Throwable t) {
            t.printStackTrace();
            JOptionPane.showMessageDialog(null, "Fatal error on main process :" + t.getMessage(), "Fatal error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void test() throws SQLException, LogReaderException, ClassNotFoundException {

        LogManager logManager = ApplicationSession.logManager = new LogManager();
        DefaultTableModel defaultTableModel = logManager.createDefaultTableModel();
        LogView logView = new LogView(defaultTableModel);
        logView.pack();
        logView.setVisible(true);

    }

    public static void loginSuccess() {
        try {
            loginView.setVisible(false);
            loginView.dispose();
            DefaultTableModel defaultTableModel = ApplicationSession.logManager.createDefaultTableModel();
            LogView logView = new LogView(defaultTableModel);
            logView.pack();
            logView.setVisible(true);
        } catch (Throwable t) {

        }
    }
}
