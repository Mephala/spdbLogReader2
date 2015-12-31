package logReader.view;

import logReader.manager.LogManager;
import logReader.model.ControllerLog;
import logReader.session.ApplicationSession;
import logReader.util.LogReaderUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

/**
 * Created by GokhanOzgozen on 12/29/2015.
 */
public class LogView extends JFrame {
    private JTable table;
    private JPanel panel;
    private JScrollPane logScrollPane;
    private JButton refreshButton;
    private int reloadValueThreshold;
    private DefaultTableModel tableModel;

    public LogView(final DefaultTableModel defaultTableModel) {
        super("Logs");
        this.tableModel = defaultTableModel;
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        table.setModel(tableModel);
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
                                Object value = tableModel.getValueAt(modelRow, 7);
                                Long threadId = (Long) value;
                                LogManager logManager = ApplicationSession.logManager;
                                Long executionTime = (Long) tableModel.getValueAt(modelRow, 1);
                                Long preciseTime = (Long) tableModel.getValueAt(modelRow, 6);
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

        logScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                synchronized (logScrollPane) {
                    reloadValueThreshold = (int) (logScrollPane.getVerticalScrollBar().getMaximum() * 0.9);
                }
                final int value = logScrollPane.getVerticalScrollBar().getValue();
                if (table.getRowCount() >= LogManager.DEFAULT_CONTROLLER_LOG_LIMIT && value >= reloadValueThreshold) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            loadMoreLogs();
                        }
                    }).start();
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    refreshButton.setEnabled(false);
                    LogManager logManager = ApplicationSession.logManager;
                    List<ControllerLog> controllerLogs = logManager.refreshControllerLogs();
                    DefaultTableModel refreshedTableModel = logManager.createTableModelFromList(controllerLogs);
                    tableModel = refreshedTableModel;
                    table.setModel(tableModel);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            LogView.this.pack();
                            LogView.this.repaint();
                        }
                    });
                } catch (Throwable t) {
                    t.printStackTrace();
                    LogReaderUtils.promptErrorMsg(t.getMessage());
                } finally {
                    refreshButton.setEnabled(true);
                }
            }
        });
    }

    private void loadMoreLogs() {
        try {
            refreshButton.setEnabled(false);
            final LogManager logManager = ApplicationSession.logManager;
            final List<ControllerLog> controllerLogList = logManager.fetchLatestControllerLogs();
            LogReaderUtils.sortControllerLogs(controllerLogList);
            synchronized (logScrollPane) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        logManager.addLogsToTable(controllerLogList, (DefaultTableModel) table.getModel());
                        reloadValueThreshold = (int) (logScrollPane.getVerticalScrollBar().getMaximum() * 0.9);
                    }
                });
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        logScrollPane.getVerticalScrollBar().setValue((int) (reloadValueThreshold * 0.9));
                    }
                });

            }
        } catch (Throwable t) {
            t.printStackTrace();
            LogReaderUtils.promptErrorMsg(t.getMessage());
        } finally {
            refreshButton.setEnabled(true);
        }
    }

}
