package mephi.lab2;

import java.sql.*;

public class DBConnector {
    public Connection connection(String dbName, String user, String password) throws SQLException {
        String url = "jdbc:postgresql://dpg-chlniljhp8uej7198aa0-a.frankfurt-postgres.render.com:5432/" + dbName;
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println("Connected to database: " + dbName);
        return conn;
    }
}