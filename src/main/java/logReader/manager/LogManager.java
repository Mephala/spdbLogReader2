package logReader.manager;

import logReader.dao.DBConnector;
import logReader.dao.LogFetcher;
import logReader.exception.LogReaderException;
import logReader.model.ControllerLog;
import logReader.model.LogQuery;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.sql.Timestamp;
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
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("METHOD");
        defaultTableModel.addColumn("EXECUTION TIME");
        defaultTableModel.addColumn("EXCEPTION");
        defaultTableModel.addColumn("IP");
        defaultTableModel.addColumn("CUSTOM LOG");
        defaultTableModel.addColumn("LOG TIME");
        for (ControllerLog controllerLog : controllerLogs) {
            defaultTableModel.addRow(new Object[]{controllerLog.getMethod(), controllerLog.getExecutiontime(),
                    controllerLog.getException(), controllerLog.getIp(), controllerLog.getCustomLog(), new Timestamp(controllerLog.getLogDate().getTime()).toString()});
        }
        return defaultTableModel;
    }
}