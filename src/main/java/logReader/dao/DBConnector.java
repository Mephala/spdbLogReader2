package logReader.dao;

import logReader.exception.LogReaderException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by GokhanOzgozen on 12/24/2015.
 */
public class DBConnector {

    private String ip;
    private String username;
    private String pw;
    private String port;
    private Connection connection;


    public DBConnector(String ip, String username, String password, String port) throws ClassNotFoundException, SQLException, LogReaderException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://"+ip+":"+port+"/serviceprovider?user="+username+"&password="+password);
        if(connection == null)
            throw new LogReaderException("Failed to obtain connection");
        this.connection =connection;
    }

    public Connection getConnection() throws SQLException {
        connection.setAutoCommit(false);
        return connection;
    }
}
