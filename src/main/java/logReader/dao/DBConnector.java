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
        this.ip = ip;
        this.username = username;
        this.pw = password;
        this.port = port;
        Connection connection = generateNewConnection();
        this.connection =connection;
    }

    private Connection generateNewConnection() throws ClassNotFoundException, SQLException, LogReaderException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://" + this.ip + ":" + this.port + "/serviceprovider?user=" + this.username + "&password=" + this.pw);
        if (connection == null)
            throw new LogReaderException("Failed to obtain connection");
        connection.setAutoCommit(false);
        return connection;
    }

    public Connection getConnection() throws SQLException, LogReaderException, ClassNotFoundException {
        if (connection == null)
            connection = generateNewConnection();
        return connection;
    }
}
