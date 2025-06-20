package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Test class to verify the Doctor table structure and data
 */
public class TestDoctorTable {
    
    public static void main(String[] args) {
        System.out.println("Starting test for Doctor table");
        
        try {
            // Get database connection
            DBContext dbContext = new DBContext();
            Connection conn = dbContext.getConnection();
            
            if (conn == null) {
                System.out.println("Failed to establish database connection");
                return;
            }
            
            System.out.println("Database connection established successfully");
            
            // Check if Doctor table exists
            checkTableExists(conn, "Doctor");
            
            // Check table structure
            checkTableStructure(conn);
            
            // Get all doctors
            getAllDoctors(conn);
            
            // Check doctor accounts
            checkDoctorAccounts(conn);
            
            System.out.println("Test completed successfully");
            
        } catch (Exception e) {
            System.out.println("Test failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void checkTableExists(Connection conn, String tableName) throws SQLException {
        String sql = "SELECT OBJECT_ID(?, 'U') AS table_id";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tableName);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Object tableId = rs.getObject("table_id");
                    if (tableId != null) {
                        System.out.println("Table '" + tableName + "' exists");
                    } else {
                        System.out.println("Table '" + tableName + "' does not exist");
                    }
                }
            }
        }
    }
    
    private static void checkTableStructure(Connection conn) throws SQLException {
        String sql = "SELECT COLUMN_NAME, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH " +
                    "FROM INFORMATION_SCHEMA.COLUMNS " +
                    "WHERE TABLE_NAME = 'Doctor' " +
                    "ORDER BY ORDINAL_POSITION";
        
        System.out.println("\nDoctor table structure:");
        System.out.println("---------------------");
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String dataType = rs.getString("DATA_TYPE");
                Object maxLength = rs.getObject("CHARACTER_MAXIMUM_LENGTH");
                
                System.out.println(columnName + " - " + dataType + 
                                 (maxLength != null ? "(" + maxLength + ")" : ""));
            }
        }
    }
    
    private static void getAllDoctors(Connection conn) throws SQLException {
        String sql = "SELECT d.*, s.Specialization_Name " +
                    "FROM Doctor d " +
                    "JOIN Specialization s ON d.Specialization_Id = s.Specialization_Id";
        
        System.out.println("\nAll doctors:");
        System.out.println("-----------");
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            int count = 0;
            while (rs.next()) {
                count++;
                int id = rs.getInt("Doctor_Id");
                int specId = rs.getInt("Specialization_Id");
                String specName = rs.getString("Specialization_Name");
                String status = rs.getString("Doctor_Status");
                
                System.out.println("Doctor ID: " + id + 
                                 ", Specialization: " + specId + " (" + specName + ")" +
                                 ", Status: " + status);
            }
            
            if (count == 0) {
                System.out.println("No doctors found in the database");
            } else {
                System.out.println("Total doctors: " + count);
            }
        }
    }
    
    private static void checkDoctorAccounts(Connection conn) throws SQLException {
        String sql = "SELECT d.Doctor_Id, a.Account_Fullname, a.Account_Image, a.Account_Status, " +
                    "s.Specialization_Name " +
                    "FROM Doctor d " +
                    "JOIN Account a ON d.Doctor_Id = a.Account_Id " +
                    "JOIN Specialization s ON d.Specialization_Id = s.Specialization_Id";
        
        System.out.println("\nDoctor accounts:");
        System.out.println("--------------");
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            int count = 0;
            while (rs.next()) {
                count++;
                int id = rs.getInt("Doctor_Id");
                String name = rs.getString("Account_Fullname");
                String image = rs.getString("Account_Image");
                String status = rs.getString("Account_Status");
                String specialization = rs.getString("Specialization_Name");
                
                System.out.println("Doctor ID: " + id + 
                                 ", Name: " + name +
                                 ", Image: " + image +
                                 ", Status: " + status +
                                 ", Specialization: " + specialization);
            }
            
            if (count == 0) {
                System.out.println("No doctor accounts found");
            } else {
                System.out.println("Total doctor accounts: " + count);
            }
        } catch (SQLException e) {
            System.out.println("Error checking doctor accounts: " + e.getMessage());
            
            // Check if Account_Image column exists
            String checkColumnSql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS " +
                                  "WHERE TABLE_NAME = 'Account' AND COLUMN_NAME = 'Account_Image'";
            
            try (PreparedStatement checkStmt = conn.prepareStatement(checkColumnSql);
                 ResultSet checkRs = checkStmt.executeQuery()) {
                
                if (checkRs.next()) {
                    System.out.println("Account_Image column exists in Account table");
                } else {
                    System.out.println("Account_Image column does NOT exist in Account table");
                    
                    // Show actual columns
                    String showColumnsSql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS " +
                                          "WHERE TABLE_NAME = 'Account'";
                    
                    try (PreparedStatement showStmt = conn.prepareStatement(showColumnsSql);
                         ResultSet showRs = showStmt.executeQuery()) {
                        
                        System.out.println("Actual columns in Account table:");
                        while (showRs.next()) {
                            System.out.println("- " + showRs.getString("COLUMN_NAME"));
                        }
                    }
                }
            }
            
            throw e;
        }
    }
} 