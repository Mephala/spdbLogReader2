package logReader.view;

import logReader.manager.LogManager;
import logReader.session.ApplicationSession;

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

    public LogView(final DefaultTableModel defaultTableModel) {
        super("Logs");
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        table.setModel(defaultTableModel);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON2) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                int rowNum = table.getSelectedRow();
                                int modelRow = table.convertRowIndexToModel(rowNum);
                                Object value = defaultTableModel.getValueAt(modelRow, 7);
                                Long threadId = (Long) value;
                                LogManager logManager = ApplicationSession.logManager;
                                Long executionTime = (Long) defaultTableModel.getValueAt(modelRow, 1);
                                Long preciseTime = (Long) defaultTableModel.getValueAt(modelRow, 6);
                                DefaultTableModel traceTable = logManager.createLogTraceTable(threadId, executionTime, preciseTime);
                                new LogTraceView(traceTable);
                            } catch (Throwable t) {
                                t.printStackTrace();
                                JOptionPane.showMessageDialog(null, t.getMessage(), "Failed to fetch traceLogs.", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }).start();

                }
            }
        });
    }

}
