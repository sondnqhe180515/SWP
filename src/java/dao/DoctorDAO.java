package dao;

import database.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object for Doctor entity
 */
public class DoctorDAO extends DBContext {
    
    /**
     * Get all active doctors with their specialization
     * @return List of maps containing doctor information
     * @throws SQLException If a database error occurs
     */
    public List<Map<String, Object>> getAllActiveDoctors() throws SQLException {
        List<Map<String, Object>> doctors = new ArrayList<>();
        
        String sql = "SELECT d.Doctor_Id, a.Account_Fullname, a.Account_Image, " +
                     "s.Specialization_Id, s.Specialization_Name " +
                     "FROM Doctor d " +
                     "JOIN Account a ON d.Doctor_Id = a.Account_Id " +
                     "JOIN Specialization s ON d.Specialization_Id = s.Specialization_Id " +
                     "WHERE a.Account_Status = 'Active' AND d.Doctor_Status = 'Active'";
        
        try {
            System.out.println("DoctorDAO: Executing getAllActiveDoctors query");
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> doctor = new HashMap<>();
                doctor.put("doctorId", rs.getInt("Doctor_Id"));
                doctor.put("fullName", rs.getString("Account_Fullname"));
                doctor.put("avatar", rs.getString("Account_Image"));
                doctor.put("specializationId", rs.getInt("Specialization_Id"));
                doctor.put("specialization", rs.getString("Specialization_Name"));
                doctors.add(doctor);
            }
            
            System.out.println("DoctorDAO: Found " + doctors.size() + " active doctors");
            
            // If no doctors found, check if tables exist
            if (doctors.isEmpty()) {
                System.out.println("DoctorDAO: No doctors found, checking if tables exist");
                
                // Check Account table
                try {
                    String checkAccountSql = "SELECT TOP 5 * FROM Account";
                    PreparedStatement checkStmt = conn.prepareStatement(checkAccountSql);
                    ResultSet checkRs = checkStmt.executeQuery();
                    
                    System.out.println("DoctorDAO: Checking Account table:");
                    boolean hasData = false;
                    while (checkRs.next()) {
                        hasData = true;
                        System.out.println("  - ID: " + checkRs.getInt("Account_Id") + 
                                         ", Name: " + checkRs.getString("Account_Fullname") + 
                                         ", Status: " + checkRs.getString("Account_Status"));
                    }
                    
                    if (!hasData) {
                        System.out.println("DoctorDAO: Account table exists but is empty");
                    }
                } catch (SQLException e) {
                    System.out.println("DoctorDAO: Error checking Account table: " + e.getMessage());
                }
                
                // Check Doctor table
                try {
                    String checkDoctorSql = "SELECT TOP 5 * FROM Doctor";
                    PreparedStatement checkStmt = conn.prepareStatement(checkDoctorSql);
                    ResultSet checkRs = checkStmt.executeQuery();
                    
                    System.out.println("DoctorDAO: Checking Doctor table:");
                    boolean hasData = false;
                    while (checkRs.next()) {
                        hasData = true;
                        System.out.println("  - Doctor ID: " + checkRs.getInt("Doctor_Id") + 
                                         ", Specialization ID: " + checkRs.getInt("Specialization_Id") + 
                                         ", Status: " + checkRs.getString("Doctor_Status"));
                    }
                    
                    if (!hasData) {
                        System.out.println("DoctorDAO: Doctor table exists but is empty");
                    }
                } catch (SQLException e) {
                    System.out.println("DoctorDAO: Error checking Doctor table: " + e.getMessage());
                }
                
                // Check Specialization table
                try {
                    String checkSpecSql = "SELECT TOP 5 * FROM Specialization";
                    PreparedStatement checkSpecStmt = conn.prepareStatement(checkSpecSql);
                    ResultSet checkSpecRs = checkSpecStmt.executeQuery();
                    
                    System.out.println("DoctorDAO: Checking Specialization table:");
                    boolean hasData = false;
                    while (checkSpecRs.next()) {
                        hasData = true;
                        System.out.println("  - ID: " + checkSpecRs.getInt("Specialization_Id") + 
                                         ", Name: " + checkSpecRs.getString("Specialization_Name") + 
                                         ", Status: " + checkSpecRs.getString("Specialization_Status"));
                    }
                    
                    if (!hasData) {
                        System.out.println("DoctorDAO: Specialization table exists but is empty");
                    }
                } catch (SQLException e) {
                    System.out.println("DoctorDAO: Error checking Specialization table: " + e.getMessage());
                }
            }
            
            return doctors;
        } catch (SQLException e) {
            System.out.println("DoctorDAO: Error in getAllActiveDoctors: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Get doctor by ID with specialization
     * @param doctorId The ID of the doctor to retrieve
     * @return Map containing doctor information or null if not found
     * @throws SQLException If a database error occurs
     */
    public Map<String, Object> getDoctorById(int doctorId) throws SQLException {
        String sql = "SELECT d.Doctor_Id, a.Account_Fullname, a.Account_Image, " +
                     "s.Specialization_Id, s.Specialization_Name " +
                     "FROM Doctor d " +
                     "JOIN Account a ON d.Doctor_Id = a.Account_Id " +
                     "JOIN Specialization s ON d.Specialization_Id = s.Specialization_Id " +
                     "WHERE d.Doctor_Id = ? AND a.Account_Status = 'Active' AND d.Doctor_Status = 'Active'";
        
        try {
            System.out.println("DoctorDAO: Getting doctor with ID: " + doctorId);
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Map<String, Object> doctor = new HashMap<>();
                doctor.put("doctorId", rs.getInt("Doctor_Id"));
                doctor.put("fullName", rs.getString("Account_Fullname"));
                doctor.put("avatar", rs.getString("Account_Image"));
                doctor.put("specializationId", rs.getInt("Specialization_Id"));
                doctor.put("specialization", rs.getString("Specialization_Name"));
                System.out.println("DoctorDAO: Found doctor: " + doctor.get("fullName"));
                return doctor;
            }
            
            System.out.println("DoctorDAO: No doctor found with ID: " + doctorId);
            return null;
        } catch (SQLException e) {
            System.out.println("DoctorDAO: Error in getDoctorById: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Get doctors by specialization ID
     * @param specializationId The specialization ID
     * @return List of maps containing doctor information
     * @throws SQLException If a database error occurs
     */
    public List<Map<String, Object>> getDoctorsBySpecialization(int specializationId) throws SQLException {
        List<Map<String, Object>> doctors = new ArrayList<>();
        
        String sql = "SELECT d.Doctor_Id, a.Account_Fullname, a.Account_Image, " +
                     "s.Specialization_Id, s.Specialization_Name " +
                     "FROM Doctor d " +
                     "JOIN Account a ON d.Doctor_Id = a.Account_Id " +
                     "JOIN Specialization s ON d.Specialization_Id = s.Specialization_Id " +
                     "WHERE d.Specialization_Id = ? AND a.Account_Status = 'Active' AND d.Doctor_Status = 'Active'";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            System.out.println("DoctorDAO: Getting doctors for specialization ID: " + specializationId);
            conn = getConnection();
            
            if (conn == null) {
                System.out.println("DoctorDAO: Connection is null!");
                throw new SQLException("Could not establish database connection");
            }
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, specializationId);
            System.out.println("DoctorDAO: Executing query: " + sql.replace("?", String.valueOf(specializationId)));
            
            rs = stmt.executeQuery();
            System.out.println("DoctorDAO: Query executed successfully");
            
            int count = 0;
            while (rs.next()) {
                count++;
                Map<String, Object> doctor = new HashMap<>();
                int doctorId = rs.getInt("Doctor_Id");
                String fullName = rs.getString("Account_Fullname");
                
                doctor.put("doctorId", doctorId);
                doctor.put("fullName", fullName);
                doctor.put("avatar", rs.getString("Account_Image"));
                doctor.put("specializationId", rs.getInt("Specialization_Id"));
                doctor.put("specialization", rs.getString("Specialization_Name"));
                doctors.add(doctor);
                
                System.out.println("DoctorDAO: Found doctor: ID=" + doctorId + ", Name=" + fullName);
            }
            
            System.out.println("DoctorDAO: Found " + count + " doctors for specialization ID: " + specializationId);
            
            // If no doctors found, check if specialization exists
            if (doctors.isEmpty()) {
                String checkSpecSql = "SELECT Specialization_Id, Specialization_Name, Specialization_Status FROM Specialization WHERE Specialization_Id = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSpecSql);
                checkStmt.setInt(1, specializationId);
                ResultSet checkRs = checkStmt.executeQuery();
                
                if (checkRs.next()) {
                    String name = checkRs.getString("Specialization_Name");
                    String status = checkRs.getString("Specialization_Status");
                    System.out.println("DoctorDAO: Specialization exists: ID=" + specializationId + 
                                     ", Name=" + name + ", Status=" + status);
                } else {
                    System.out.println("DoctorDAO: Specialization with ID " + specializationId + " does not exist");
                }
                
                // Check if there are any doctors at all
                String checkDoctorsSql = "SELECT COUNT(*) AS total FROM Doctor";
                checkStmt = conn.prepareStatement(checkDoctorsSql);
                checkRs = checkStmt.executeQuery();
                
                if (checkRs.next()) {
                    int total = checkRs.getInt("total");
                    System.out.println("DoctorDAO: Total doctors in database: " + total);
                }
                
                // Check if there are any doctors with this specialization (regardless of status)
                String checkSpecDoctorsSql = "SELECT COUNT(*) AS total FROM Doctor WHERE Specialization_Id = ?";
                checkStmt = conn.prepareStatement(checkSpecDoctorsSql);
                checkStmt.setInt(1, specializationId);
                checkRs = checkStmt.executeQuery();
                
                if (checkRs.next()) {
                    int total = checkRs.getInt("total");
                    System.out.println("DoctorDAO: Total doctors with specialization ID " + specializationId + ": " + total);
                }
            }
            
            return doctors;
        } catch (SQLException e) {
            System.out.println("DoctorDAO: Error in getDoctorsBySpecialization: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                // Don't close connection here as it's managed by DBContext
            } catch (SQLException e) {
                System.out.println("DoctorDAO: Error closing resources: " + e.getMessage());
            }
        }
    }
    
    /**
     * Check if a doctor is available at a specific date and slot
     * @param doctorId The ID of the doctor
     * @param date The date in SQL Date format
     * @param slotId The ID of the time slot
     * @return true if the doctor is available, false otherwise
     * @throws SQLException If a database error occurs
     */
    public boolean isDoctorAvailable(int doctorId, java.sql.Date date, int slotId) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM Doctor_schedule " +
                     "WHERE Doctor_Id = ? AND Schedule_WorkDate = ? AND Slot_Id = ? AND Schedule_Status = 'Booked'";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, doctorId);
            stmt.setDate(2, date);
            stmt.setInt(3, slotId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count == 0; // Doctor is available if no bookings found
                }
            }
        }
        
        return true; // Default to available if query fails
    }
} 