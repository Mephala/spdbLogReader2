package logReader.test;

import logReader.dao.DBConnector;
import logReader.dao.LogFetcher;
import logReader.exception.LogReaderException;
import logReader.manager.LogManager;
import logReader.model.ControllerLog;
import logReader.model.LogQuery;
import logReader.model.TraceLog;
import logReader.util.LogReaderUtils;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by GokhanOzgozen on 12/24/2015.
 */
@RunWith(JMockit.class)
public class TestLogFetching {

    @Test
    public void testInitializingDB(){
        String dbIp = "localhost";
        String username = "root";
        String pw = "bbbbbbbbb";
        String port = "3306";
        try {
            DBConnector dbConnector = new DBConnector(dbIp,username,pw,port);
            assertTrue(dbConnector!=null);
        } catch (Throwable t) {
            t.printStackTrace();
            fail(t.getMessage());
        }
    }

    @Test
    public void testFetchingLatestLogs() throws SQLException, LogReaderException, ClassNotFoundException {
        String dbIp = "localhost";
        String username = "root";
        String pw = "bbbbbbbbb";
        String port = "3306";
        DBConnector dbConnector = new DBConnector(dbIp,username,pw,port);
        LogFetcher logFetcher = new LogFetcher(dbConnector);
        List<ControllerLog> controllerLogList = logFetcher.fetchControllerLogs();
        assertTrue(controllerLogList!=null && controllerLogList.size()>0);
        System.out.println(controllerLogList);
    }


    @Test
    public void testFetchingLogsByQuery() throws SQLException, LogReaderException, ClassNotFoundException {
        LogQuery logQuery = new LogQuery();
        logQuery.setMethod("getNearestPosts");
        String dbIp = "localhost";
        String username = "root";
        String pw = "bbbbbbbbb";
        String port = "3306";
        DBConnector dbConnector = new DBConnector(dbIp,username,pw,port);
        LogFetcher logFetcher = new LogFetcher(dbConnector);
        List<ControllerLog> controllerLogList = logFetcher.fetchControllerLogs(logQuery);
        assertTrue(LogReaderUtils.isNotEmpty(controllerLogList));
        System.out.println(controllerLogList);
    }

    @Test
    public void testLogManagerInitialization() throws SQLException, LogReaderException, ClassNotFoundException {
        LogManager logManager = new LogManager();
        assertTrue(logManager != null);
    }

    @Test
    public void testFetchingLastControllerLogs() throws SQLException, LogReaderException, ClassNotFoundException {
        LogManager logManager = new LogManager();
        final int latestLogLimit = 10000;
        List<ControllerLog> controllerLogs = logManager.fetchLatestControllerLogs(latestLogLimit);
        assertTrue(LogReaderUtils.isNotEmpty(controllerLogs));
    }

    /**
     * There must be at least DEFAULT_CONTROLLER_LOG_SIZE + 1 for this test to pass.
     *
     * @throws SQLException
     * @throws LogReaderException
     * @throws ClassNotFoundException
     */
    @Test
    public void testReloadingControllerLogs() throws SQLException, LogReaderException, ClassNotFoundException {
        LogManager logManager = new LogManager();
        List<ControllerLog> controllerLogs = logManager.fetchLatestControllerLogs();
        assertTrue(LogReaderUtils.isNotEmpty(controllerLogs));
        LogReaderUtils.sortControllerLogs(controllerLogs);
        ControllerLog firstLogOfFirstList = controllerLogs.get(0);
        List<ControllerLog> secondList = logManager.fetchLatestControllerLogs();
        assertTrue(LogReaderUtils.isNotEmpty(secondList));
        LogReaderUtils.sortControllerLogs(secondList);
        ControllerLog firstLogOfSecondList = secondList.get(0);
        assertTrue(!firstLogOfFirstList.equals(firstLogOfSecondList));
    }

    @Test
    public void testFetchingMethodExistsInControllerLogs() throws SQLException, LogReaderException, ClassNotFoundException {
        LogManager logManager = new LogManager();
        final int latestLogLimit = 10000;
        List<ControllerLog> controllerLogs = logManager.fetchLatestControllerLogs(latestLogLimit);
        assertTrue(LogReaderUtils.isNotEmpty(controllerLogs));
        for (ControllerLog controllerLog : controllerLogs) {
            assertTrue(LogReaderUtils.isNotEmpty(controllerLog.getMethod()));
        }
    }

    @Test(expected = Exception.class)
    public void testInvalidCredentials() throws SQLException, LogReaderException, ClassNotFoundException {
        LogManager logManager = new LogManager("46.118.73.45", "root2", "bbbbbbbbb", "3306"); //wrong info
        assertTrue(logManager != null);
    }

    @Test
    public void testTracingALog() throws SQLException, LogReaderException, ClassNotFoundException {
        LogManager logManager = new LogManager();
        final int latestLogLimit = 100;
        List<ControllerLog> controllerLogs = logManager.fetchLatestControllerLogs(latestLogLimit);
        assertTrue(LogReaderUtils.isNotEmpty(controllerLogs));
        for (ControllerLog controllerLog : controllerLogs) {
            assertTrue(LogReaderUtils.isNotEmpty(controllerLog.getMethod()));
        }
        Random r = new Random();
        int logSampleIndex = r.nextInt(latestLogLimit);
        ControllerLog randomLog = controllerLogs.get(logSampleIndex);
        List<TraceLog> traceLogs = logManager.getLogTrace(randomLog);
        assertTrue(LogReaderUtils.isNotEmpty(traceLogs));
    }

    @Test
    public void testTracingALogFromThradId() throws SQLException, LogReaderException, ClassNotFoundException {
        LogManager logManager = new LogManager();
        final int latestLogLimit = 100;
        List<ControllerLog> controllerLogs = logManager.fetchLatestControllerLogs(latestLogLimit);
        assertTrue(LogReaderUtils.isNotEmpty(controllerLogs));
        for (ControllerLog controllerLog : controllerLogs) {
            assertTrue(LogReaderUtils.isNotEmpty(controllerLog.getMethod()));
        }
        Random r = new Random();
        int logSampleIndex = r.nextInt(latestLogLimit);
        ControllerLog randomLog = controllerLogs.get(logSampleIndex);
        List<TraceLog> traceLogs = logManager.getLogTrace(randomLog.getThreadId());
        assertTrue(LogReaderUtils.isNotEmpty(traceLogs));
    }

}
