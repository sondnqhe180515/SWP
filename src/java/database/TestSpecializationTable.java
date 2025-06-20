package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Test class to verify the Specialization table structure and data
 */
public class TestSpecializationTable {
    
    public static void main(String[] args) {
        System.out.println("Starting test for Specialization table");
        
        try {
            // Get database connection
            DBContext dbContext = new DBContext();
            Connection conn = dbContext.getConnection();
            
            if (conn == null) {
                System.out.println("Failed to establish database connection");
                return;
            }
            
            System.out.println("Database connection established successfully");
            
            // Check if Specialization table exists
            checkTableExists(conn, "Specialization");
            
            // Check table structure
            checkTableStructure(conn);
            
            // Get all specializations
            getAllSpecializations(conn);
            
            // Check if any doctors are assigned to specializations
            checkDoctorSpecializations(conn);
            
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
                    "WHERE TABLE_NAME = 'Specialization' " +
                    "ORDER BY ORDINAL_POSITION";
        
        System.out.println("\nSpecialization table structure:");
        System.out.println("-----------------------------");
        
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
    
    private static void getAllSpecializations(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Specialization";
        
        System.out.println("\nAll specializations:");
        System.out.println("------------------");
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            int count = 0;
            while (rs.next()) {
                count++;
                int id = rs.getInt("Specialization_Id");
                String name = rs.getString("Specialization_Name");
                String status = rs.getString("Specialization_Status");
                
                System.out.println(id + ": " + name + " (" + status + ")");
            }
            
            if (count == 0) {
                System.out.println("No specializations found in the database");
            } else {
                System.out.println("Total specializations: " + count);
            }
        }
    }
    
    private static void checkDoctorSpecializations(Connection conn) throws SQLException {
        String sql = "SELECT s.Specialization_Id, s.Specialization_Name, COUNT(d.Doctor_Id) AS doctor_count " +
                    "FROM Specialization s " +
                    "LEFT JOIN Doctor d ON s.Specialization_Id = d.Specialization_Id " +
                    "GROUP BY s.Specialization_Id, s.Specialization_Name";
        
        System.out.println("\nDoctors per specialization:");
        System.out.println("-------------------------");
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("Specialization_Id");
                String name = rs.getString("Specialization_Name");
                int doctorCount = rs.getInt("doctor_count");
                
                System.out.println(id + ": " + name + " - " + doctorCount + " doctor(s)");
            }
        }
    }
} 