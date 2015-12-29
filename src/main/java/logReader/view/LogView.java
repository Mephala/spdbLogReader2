package logReader.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON2) {
                    LogTraceView logTraceView = new LogTraceView();
                }
            }
        });
    }

}
