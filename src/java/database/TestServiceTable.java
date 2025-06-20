package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Test class to verify that the Service table exists and has data
 */
public class TestServiceTable {
    public static void main(String[] args) {
        try {
            System.out.println("Testing connection to Service table...");
            
            // Create database connection
            DBContext dbContext = new DBContext();
            Connection conn = dbContext.getConnection();
            
            if (conn != null) {
                System.out.println("Database connection successful!");
                
                // Check if Service table exists
                try (Statement stmt = conn.createStatement()) {
                    // Try to query the Service table
                    String sql = "SELECT Service_Id, Service_Title, Service_Detail, Service_Price, Specialization_Id FROM Service";
                    ResultSet rs = stmt.executeQuery(sql);
                    
                    System.out.println("\nService table data:");
                    System.out.println("----------------------------------");
                    System.out.println("ID | Title | Detail | Price | Specialization ID");
                    System.out.println("----------------------------------");
                    
                    boolean hasData = false;
                    while (rs.next()) {
                        hasData = true;
                        int id = rs.getInt("Service_Id");
                        String title = rs.getString("Service_Title");
                        String detail = rs.getString("Service_Detail");
                        double price = rs.getDouble("Service_Price");
                        int specId = rs.getInt("Specialization_Id");
                        System.out.println(id + " | " + title + " | " + detail + " | " + price + " | " + specId);
                    }
                    
                    if (!hasData) {
                        System.out.println("No data found in Service table. You need to insert services.");
                        System.out.println("\nChecking if Specialization table has data...");
                        
                        // Check Specialization table
                        String specSql = "SELECT Specialization_Id, Specialization_Name FROM Specialization";
                        ResultSet specRs = stmt.executeQuery(specSql);
                        
                        System.out.println("\nSpecialization table data:");
                        System.out.println("----------------------------------");
                        System.out.println("ID | Name");
                        System.out.println("----------------------------------");
                        
                        boolean hasSpecData = false;
                        while (specRs.next()) {
                            hasSpecData = true;
                            int specId = specRs.getInt("Specialization_Id");
                            String specName = specRs.getString("Specialization_Name");
                            System.out.println(specId + " | " + specName);
                        }
                        
                        if (!hasSpecData) {
                            System.out.println("No data found in Specialization table. You need to insert specializations first.");
                        } else {
                            System.out.println("\nExecuting INSERT statements to create default services...");
                            
                            // Get the first specialization ID
                            specRs = stmt.executeQuery("SELECT TOP 1 Specialization_Id FROM Specialization");
                            int firstSpecId = 1;
                            if (specRs.next()) {
                                firstSpecId = specRs.getInt("Specialization_Id");
                            }
                            
                            // Insert default services if none exist
                            String[] insertSql = {
                                "INSERT INTO Service (Service_Title, Service_Detail, Service_Price, Specialization_Id, Service_Status) VALUES ('Khám tổng quát', 'Khám tổng quát răng miệng', 200000, " + firstSpecId + ", 'Active')",
                                "INSERT INTO Service (Service_Title, Service_Detail, Service_Price, Specialization_Id, Service_Status) VALUES ('Cạo vôi răng', 'Làm sạch cao răng', 500000, " + firstSpecId + ", 'Active')",
                                "INSERT INTO Service (Service_Title, Service_Detail, Service_Price, Specialization_Id, Service_Status) VALUES ('Trám răng', 'Trám răng sâu', 300000, " + firstSpecId + ", 'Active')",
                                "INSERT INTO Service (Service_Title, Service_Detail, Service_Price, Specialization_Id, Service_Status) VALUES ('Nhổ răng', 'Nhổ răng sâu hoặc răng khôn', 400000, " + firstSpecId + ", 'Active')",
                                "INSERT INTO Service (Service_Title, Service_Detail, Service_Price, Specialization_Id, Service_Status) VALUES ('Tẩy trắng răng', 'Làm trắng răng', 1500000, " + firstSpecId + ", 'Active')"
                            };
                            
                            for (String insert : insertSql) {
                                try {
                                    stmt.executeUpdate(insert);
                                    System.out.println("Inserted: " + insert);
                                } catch (SQLException e) {
                                    System.out.println("Error inserting service: " + e.getMessage());
                                }
                            }
                            
                            // Verify inserts
                            System.out.println("\nVerifying inserted services:");
                            rs = stmt.executeQuery(sql);
                            while (rs.next()) {
                                int id = rs.getInt("Service_Id");
                                String title = rs.getString("Service_Title");
                                String detail = rs.getString("Service_Detail");
                                double price = rs.getDouble("Service_Price");
                                int specId = rs.getInt("Specialization_Id");
                                System.out.println(id + " | " + title + " | " + detail + " | " + price + " | " + specId);
                            }
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Error with Service table: " + e.getMessage());
                    
                    // Check if Service table exists
                    try {
                        String checkTableSql = "SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'Service'";
                        Statement checkStmt = conn.createStatement();
                        ResultSet checkRs = checkStmt.executeQuery(checkTableSql);
                        
                        if (checkRs.next()) {
                            System.out.println("Service table exists but there was an error querying it.");
                        } else {
                            System.out.println("Service table does not exist. Creating it...");
                            
                            // Create Service table
                            String createTableSql = "CREATE TABLE [dbo].[Service](\n" +
                                                   "    [Service_Id] [int] IDENTITY(1,1) NOT NULL,\n" +
                                                   "    [Service_Title] [nvarchar](255) NOT NULL,\n" +
                                                   "    [Service_Detail] [nvarchar](max) NULL,\n" +
                                                   "    [Service_Price] [decimal](18, 0) NOT NULL,\n" +
                                                   "    [Specialization_Id] [int] NOT NULL,\n" +
                                                   "    [Service_Status] [nvarchar](50) NOT NULL,\n" +
                                                   "    PRIMARY KEY CLUSTERED ([Service_Id] ASC)\n" +
                                                   ")";
                            
                            try {
                                Statement createStmt = conn.createStatement();
                                createStmt.executeUpdate(createTableSql);
                                System.out.println("Service table created successfully!");
                            } catch (SQLException createError) {
                                System.out.println("Error creating Service table: " + createError.getMessage());
                            }
                        }
                    } catch (SQLException checkError) {
                        System.out.println("Error checking if Service table exists: " + checkError.getMessage());
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