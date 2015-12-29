package logReader.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by GokhanOzgozen on 12/29/2015.
 */
public class LogView extends JFrame {
    private JTable table;
    private JPanel panel;

    public LogView(DefaultTableModel defaultTableModel) {
        super("Logs");
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        table.setModel(defaultTableModel);
    }

}
