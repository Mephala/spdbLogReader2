package logReader.test;

import logReader.dao.DBConnector;
import logReader.dao.LogFetcher;
import logReader.exception.LogReaderException;
import logReader.model.ControllerLog;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.SQLException;
import java.util.List;

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

}
