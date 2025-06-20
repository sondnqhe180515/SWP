package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {
    protected Connection connection;
    
    public DBContext() {
        try {
            System.out.println("DBContext: Attempting to establish database connection");
            // Edit server name, database name, username and password according to your setup
            String url = "jdbc:sqlserver://localhost:1433;databaseName=SWP391_G5;encrypt=true;trustServerCertificate=true";
            String username = "admin";
            String password = "12345";
            
            System.out.println("DBContext: Loading JDBC driver");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            System.out.println("DBContext: Connecting to database with URL: " + url);
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("DBContext: Database connection established successfully");
        } catch (ClassNotFoundException ex) {
            System.out.println("DBContext: JDBC Driver not found: " + ex.getMessage());
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.out.println("DBContext: Database connection error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public Connection getConnection() {
        try {
            // Check if connection is closed or null, and reconnect if needed
            if (connection == null || connection.isClosed()) {
                System.out.println("DBContext: Connection is null or closed, attempting to reconnect");
                String url = "jdbc:sqlserver://localhost:1433;databaseName=SWP391_G5;encrypt=true;trustServerCertificate=true";
                String username = "admin";
                String password = "12345";
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("DBContext: Database connection reestablished");
            } else {
                System.out.println("DBContext: Returning existing connection");
            }
        } catch (SQLException e) {
            System.out.println("DBContext: Error checking connection status: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
} 