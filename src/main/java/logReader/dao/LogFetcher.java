package logReader.dao;

import logReader.model.ControllerLog;
import logReader.model.LogQuery;
import logReader.util.LogReaderUtils;

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

    private static final int DEFAULT_CONTROLLER_LIMIT = 1000;
    private DBConnector dbConnector;

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
        List<ControllerLog> controllerLogs = buildControllerLogList(rs);
        return controllerLogs;
    }

    private List<ControllerLog> buildControllerLogList(ResultSet rs) throws SQLException {
        List<ControllerLog> controllerLogs = new ArrayList<>();
        while (rs.next()) {
            ControllerLog cLog = new ControllerLog();
            cLog.setClassName(rs.getString("CONTROLLER"));
            cLog.setCustomLog(rs.getString("CUSTOM_MSG"));
            cLog.setException(rs.getString("EXCEPTION"));
            cLog.setExecutiontime(rs.getLong("EXECUTION_TIME"));
            cLog.setLogDate(rs.getTimestamp("LOG_TIME"));
            cLog.setParameter(rs.getString("PARAMETER"));
            cLog.setRetval(rs.getString("RETVAL"));
            cLog.setIp(rs.getString("REQUEST_IP"));
            cLog.setMethod(rs.getString("METHOD"));
            controllerLogs.add(cLog);
        }
        return controllerLogs;
    }

    public List<ControllerLog> fetchControllerLogs(LogQuery logQuery) throws SQLException {
        Connection connection = dbConnector.getConnection();
        Integer limit = logQuery.getLimit();
        if (limit == null)
            limit = DEFAULT_CONTROLLER_LIMIT;
        StringBuilder whereClauseBuilder = new StringBuilder();
        if (LogReaderUtils.isNotEmpty(logQuery.getMethod()))
            whereClauseBuilder.append("where METHOD = '" + logQuery.getMethod() + "'");
        String whereClause = whereClauseBuilder.toString();
        String sql = "Select * from SPG_CONTROLLER_LOG " + whereClause + " order by LOG_TIME desc limit " + limit.toString();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        List<ControllerLog> controllerLogs = buildControllerLogList(rs);
        return controllerLogs;
    }
}
