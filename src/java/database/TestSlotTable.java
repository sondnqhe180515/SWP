package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Test class to verify that the Slot table exists and has data
 */
public class TestSlotTable {
    public static void main(String[] args) {
        try {
            System.out.println("Testing connection to Slot table...");
            
            // Create database connection
            DBContext dbContext = new DBContext();
            Connection conn = dbContext.getConnection();
            
            if (conn != null) {
                System.out.println("Database connection successful!");
                
                // Check if Slot table exists
                try (Statement stmt = conn.createStatement()) {
                    // Try to query the Slot table
                    String sql = "SELECT Slot_Id, Start_Time, End_Time FROM Slot";
                    ResultSet rs = stmt.executeQuery(sql);
                    
                    System.out.println("\nSlot table data:");
                    System.out.println("----------------------------------");
                    System.out.println("ID | Start Time | End Time");
                    System.out.println("----------------------------------");
                    
                    boolean hasData = false;
                    while (rs.next()) {
                        hasData = true;
                        int id = rs.getInt("Slot_Id");
                        String startTime = rs.getTime("Start_Time").toString();
                        String endTime = rs.getTime("End_Time").toString();
                        System.out.println(id + " | " + startTime + " | " + endTime);
                    }
                    
                    if (!hasData) {
                        System.out.println("No data found in Slot table. You need to insert time slots.");
                        System.out.println("\nExecuting INSERT statements to create default slots...");
                        
                        // Insert default time slots if none exist
                        String[] insertSql = {
                            "INSERT INTO Slot (Start_Time, End_Time) VALUES ('08:00:00', '09:00:00')",
                            "INSERT INTO Slot (Start_Time, End_Time) VALUES ('09:00:00', '10:00:00')",
                            "INSERT INTO Slot (Start_Time, End_Time) VALUES ('10:00:00', '11:00:00')",
                            "INSERT INTO Slot (Start_Time, End_Time) VALUES ('13:30:00', '14:30:00')",
                            "INSERT INTO Slot (Start_Time, End_Time) VALUES ('14:30:00', '15:30:00')",
                            "INSERT INTO Slot (Start_Time, End_Time) VALUES ('15:30:00', '16:30:00')"
                        };
                        
                        for (String insert : insertSql) {
                            try {
                                stmt.executeUpdate(insert);
                                System.out.println("Inserted: " + insert);
                            } catch (SQLException e) {
                                System.out.println("Error inserting slot: " + e.getMessage());
                            }
                        }
                        
                        // Verify inserts
                        System.out.println("\nVerifying inserted slots:");
                        rs = stmt.executeQuery(sql);
                        while (rs.next()) {
                            int id = rs.getInt("Slot_Id");
                            String startTime = rs.getTime("Start_Time").toString();
                            String endTime = rs.getTime("End_Time").toString();
                            System.out.println(id + " | " + startTime + " | " + endTime);
                        }
                    }
                }
                
                // Check Doctor_schedule table
                try (Statement stmt = conn.createStatement()) {
                    System.out.println("\nChecking Doctor_schedule table...");
                    String sql = "SELECT TOP 5 * FROM Doctor_schedule";
                    
                    try {
                        ResultSet rs = stmt.executeQuery(sql);
                        System.out.println("Doctor_schedule table exists.");
                        
                        boolean hasData = false;
                        while (rs.next()) {
                            hasData = true;
                            System.out.println("Found schedule entry: Doctor ID=" + rs.getInt("Doctor_Id") + 
                                              ", Slot ID=" + rs.getInt("Slot_Id") + 
                                              ", Date=" + rs.getDate("Schedule_WorkDate") + 
                                              ", Status=" + rs.getString("Schedule_Status"));
                        }
                        
                        if (!hasData) {
                            System.out.println("No data found in Doctor_schedule table.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error with Doctor_schedule table: " + e.getMessage());
                    }
                }
                
            } else {
                System.out.println("Failed to establish database connection!");
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 