package logReader.view;

import javax.swing.*;

/**
 * Created by GokhanOzgozen on 12/29/2015.
 */
public class LogTraceView extends JFrame {
    private JTable table1;
    private JPanel panel1;

    public LogTraceView() {
        super("Log Trace");
        setContentPane(panel1);
        pack();
        setVisible(true);
    }
}
