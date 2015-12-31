package logReader.manager;

import logReader.dao.DBConnector;
import logReader.dao.LogFetcher;
import logReader.exception.LogReaderException;
import logReader.model.ControllerLog;
import logReader.model.LogQuery;
import logReader.model.TraceLog;
import logReader.util.LogReaderUtils;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

/**
 * Created by GokhanOzgozen on 12/29/2015.
 */
public class LogManager {

    private static final Integer DEFAULT_CONTROLLER_LOG_LIMIT = 1000;
    private static final String DEFAULT_IP = "localhost";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "bbbbbbbbb";
    private static final String DEFAULT_PORT = "3306";
    private LogFetcher logFetcher;


    public LogManager() throws SQLException, LogReaderException, ClassNotFoundException {
        DBConnector dbConnector = new DBConnector(DEFAULT_IP, DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_PORT);
        LogFetcher logFetcher = new LogFetcher(dbConnector);
        this.logFetcher = logFetcher;
    }

    public LogManager(LogFetcher logFetcher) {
        this.logFetcher = logFetcher;
    }

    public LogManager(String ip, String username, String password, String port) throws SQLException, LogReaderException, ClassNotFoundException {
        DBConnector dbConnector = new DBConnector(ip, username, password, port);
        LogFetcher logFetcher = new LogFetcher(dbConnector);
        this.logFetcher = logFetcher;
    }

    public List<ControllerLog> fetchLatestControllerLogs() throws SQLException {
        return fetchLatestControllerLogs(DEFAULT_CONTROLLER_LOG_LIMIT);
    }


    public List<ControllerLog> fetchLatestControllerLogs(int latestLogLimit) throws SQLException {
        LogQuery logQuery = new LogQuery();
        logQuery.setLimit(latestLogLimit);
        return logFetcher.fetchControllerLogs(logQuery);
    }

    public DefaultTableModel createDefaultTableModel() throws SQLException {
        List<ControllerLog> controllerLogs = fetchLatestControllerLogs();
        LogReaderUtils.sortControllerLogs(controllerLogs);
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public Class getColumnClass(int c) {
                Object obj = getValueAt(0, c);
                if (obj == null)
                    return Object.class;
                else
                    return getValueAt(0, c).getClass();
            }
        };
        defaultTableModel.addColumn("METHOD");
        defaultTableModel.addColumn("EXECUTION TIME");
        defaultTableModel.addColumn("EXCEPTION");
        defaultTableModel.addColumn("IP");
        defaultTableModel.addColumn("CUSTOM LOG");
        defaultTableModel.addColumn("LOG TIME");
        defaultTableModel.addColumn("PRECISE_TIME");
        defaultTableModel.addColumn("THREAD_ID");
        for (ControllerLog controllerLog : controllerLogs) {
            defaultTableModel.addRow(new Object[]{controllerLog.getMethod(), controllerLog.getExecutiontime(),
                    controllerLog.getException(), controllerLog.getIp(), controllerLog.getCustomLog(), new Timestamp(controllerLog.getLogDate().getTime()).toString(), controllerLog.getPreciseTime(), controllerLog.getThreadId()});
        }
        return defaultTableModel;
    }

    public List<TraceLog> getLogTrace(ControllerLog controllerLog) throws SQLException {
        return logFetcher.fetchTraceLogs(controllerLog.getThreadId());
    }

    public List<TraceLog> getLogTrace(Long threadId) throws SQLException {
        return logFetcher.fetchTraceLogs(threadId);
    }

    public DefaultTableModel createLogTraceTable(Long threadId, Long executionTime, Long preciseTime) throws SQLException {
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public Class getColumnClass(int c) {
                Object obj = getValueAt(0, c);
                if (obj == null)
                    return Object.class;
                else
                    return getValueAt(0, c).getClass();
            }
        };
        defaultTableModel.addColumn("CLASS");
        defaultTableModel.addColumn("METHOD");
        defaultTableModel.addColumn("EXECUTION TIME");
        defaultTableModel.addColumn("EXCEPTION");
        defaultTableModel.addColumn("PARAMETER");
        defaultTableModel.addColumn("RETVAL");
        defaultTableModel.addColumn("LOG TIME");
        defaultTableModel.addColumn("PRECISE_TIME");
        defaultTableModel.addColumn("THREAD_ID");
        List<TraceLog> traceLogs = logFetcher.fetchTraceLogs(threadId);
        Iterator<TraceLog> traceLogIterator = traceLogs.iterator();
        final long start = preciseTime - executionTime;
        while (traceLogIterator.hasNext()) {
            TraceLog next = traceLogIterator.next();
            Long finishPreciseTime = next.getPreciseTime();
            boolean notInTraceInterval = !(finishPreciseTime < preciseTime && finishPreciseTime > start);
            if (notInTraceInterval)
                traceLogIterator.remove();
        }
        LogReaderUtils.sortTraceLogs(traceLogs);
        for (TraceLog traceLog : traceLogs) {
            defaultTableModel.addRow(new Object[]{traceLog.getClassName(), traceLog.getMethod(), traceLog.getExecutionTime(), traceLog.getException(), traceLog.getParameter(), traceLog.getRetval()
                    , traceLog.getLogTime(), traceLog.getPreciseTime(), threadId});
        }

        return defaultTableModel;
    }
}
