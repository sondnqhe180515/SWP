package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test class to verify doctors by specialization query
 */
public class TestDoctorsBySpecialization {
    
    public static void main(String[] args) {
        System.out.println("Starting test for doctors by specialization query");
        
        try {
            // Get database connection
            DBContext dbContext = new DBContext();
            Connection conn = dbContext.getConnection();
            
            if (conn == null) {
                System.out.println("Failed to establish database connection");
                return;
            }
            
            System.out.println("Database connection established successfully");
            
            // Test with specialization ID 1 (assuming it exists)
            testSpecialization(conn, 1);
            
            // Test with specialization ID 2 (assuming it exists)
            testSpecialization(conn, 2);
            
            // Test with an invalid specialization ID
            testSpecialization(conn, 999);
            
            System.out.println("Test completed successfully");
            
        } catch (Exception e) {
            System.out.println("Test failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testSpecialization(Connection conn, int specializationId) {
        System.out.println("\n----- Testing specialization ID: " + specializationId + " -----");
        
        String sql = "SELECT d.Doctor_Id, a.Account_Fullname, a.Account_Image, " +
                     "s.Specialization_Id, s.Specialization_Name " +
                     "FROM Doctor d " +
                     "JOIN Account a ON d.Doctor_Id = a.Account_Id " +
                     "JOIN Specialization s ON d.Specialization_Id = s.Specialization_Id " +
                     "WHERE d.Specialization_Id = ? AND a.Account_Status = 'Active' AND d.Doctor_Status = 'Active'";
        
        List<Map<String, Object>> doctors = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, specializationId);
            System.out.println("Executing query: " + sql.replace("?", String.valueOf(specializationId)));
            
            try (ResultSet rs = stmt.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    count++;
                    Map<String, Object> doctor = new HashMap<>();
                    int doctorId = rs.getInt("Doctor_Id");
                    String fullName = rs.getString("Account_Fullname");
                    String avatar = rs.getString("Account_Image");
                    int specId = rs.getInt("Specialization_Id");
                    String specName = rs.getString("Specialization_Name");
                    
                    doctor.put("doctorId", doctorId);
                    doctor.put("fullName", fullName);
                    doctor.put("avatar", avatar);
                    doctor.put("specializationId", specId);
                    doctor.put("specialization", specName);
                    doctors.add(doctor);
                    
                    System.out.println("Found doctor: ID=" + doctorId + 
                                     ", Name=" + fullName + 
                                     ", Specialization=" + specName + 
                                     ", Avatar=" + avatar);
                }
                
                System.out.println("Found " + count + " doctors for specialization ID: " + specializationId);
            }
            
            // If no doctors found, check if specialization exists
            if (doctors.isEmpty()) {
                String checkSpecSql = "SELECT Specialization_Id, Specialization_Name, Specialization_Status FROM Specialization WHERE Specialization_Id = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkSpecSql)) {
                    checkStmt.setInt(1, specializationId);
                    try (ResultSet checkRs = checkStmt.executeQuery()) {
                        if (checkRs.next()) {
                            String name = checkRs.getString("Specialization_Name");
                            String status = checkRs.getString("Specialization_Status");
                            System.out.println("Specialization exists: ID=" + specializationId + 
                                             ", Name=" + name + ", Status=" + status);
                        } else {
                            System.out.println("Specialization with ID " + specializationId + " does not exist");
                        }
                    }
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error testing specialization ID " + specializationId + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
} 