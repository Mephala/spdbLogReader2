package logReader.dao;

import logReader.model.ControllerLog;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GokhanOzgozen on 12/24/2015.
 */
public class LogFetcher {

    private DBConnector dbConnector;
    private static final int DEFAULT_CONTROLLER_LIMIT = 500;

    public LogFetcher(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    /**
     * If not given, returns latest 500 logs from db.
     *
     * @return
     */
    public List<ControllerLog> fetchControllerLogs() throws SQLException {
        return fetchControllerLogsByLimit(DEFAULT_CONTROLLER_LIMIT);
    }

    private List<ControllerLog> fetchControllerLogsByLimit(int defaultControllerLimit) throws SQLException {
        Connection connection = dbConnector.getConnection();
        String sql = "Select * from SPG_CONTROLLER_LOG order by LOG_TIME desc limit " + defaultControllerLimit;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<ControllerLog> controllerLogs = new ArrayList<>();
        while (rs.next()) {
            ControllerLog cLog = new ControllerLog();
            cLog.setClassName(rs.getString("CONTROLLER"));
            cLog.setCustomLog(rs.getString("CUSTOM_MSG"));
            cLog.setException(rs.getString("EXCEPTION"));
            cLog.setExecutiontime(rs.getLong("EXECUTION_TIME"));
            cLog.setLogDate(rs.getDate("LOG_TIME"));
            cLog.setParameter(rs.getString("PARAMETER"));
            cLog.setRetval(rs.getString("RETVAL"));
            controllerLogs.add(cLog);
        }
        return controllerLogs;
    }
}
