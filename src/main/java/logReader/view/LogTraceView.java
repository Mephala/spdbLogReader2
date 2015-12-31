package logReader.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by GokhanOzgozen on 12/29/2015.
 */
public class LogTraceView extends JFrame {
    private JTable table;
    private JPanel panel;

    public LogTraceView(DefaultTableModel defaultTableModel) {
        super("Log Trace");
        setContentPane(panel);
        table.setModel(defaultTableModel);
        pack();
        setVisible(true);
    }
}
