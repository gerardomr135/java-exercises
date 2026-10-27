package com.fireal99;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionManager {
    private final String url;
    private final Properties props;
    public DatabaseConnectionManager( String host, String databaseName, String username, String password)
    {
        this.url = "jdbc:postgresql://" + host + "/" + databaseName;
        this.props = new Properties();
        this.props.setProperty("user", username);
        this.props.setProperty("password", password);
    }

    public Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection( url, props );
    }
}
