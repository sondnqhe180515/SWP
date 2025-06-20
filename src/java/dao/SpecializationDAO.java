package dao;

import database.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object for Specialization entity
 */
public class SpecializationDAO extends DBContext {
    
    /**
     * Get all active specializations
     * @return List of specialization information as maps
     * @throws SQLException If a database error occurs
     */
    public List<Map<String, Object>> getAllActiveSpecializations() throws SQLException {
        List<Map<String, Object>> specializations = new ArrayList<>();
        
        String sql = "SELECT Specialization_Id, Specialization_Name, Specialization_Image " +
                     "FROM Specialization " +
                     "WHERE Specialization_Status = 'Active' " +
                     "ORDER BY Specialization_Name";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Map<String, Object> specialization = new HashMap<>();
                specialization.put("id", rs.getInt("Specialization_Id"));
                specialization.put("name", rs.getString("Specialization_Name"));
                specialization.put("image", rs.getString("Specialization_Image"));
                specializations.add(specialization);
            }
        }
        
        return specializations;
    }
    
    /**
     * Get all specializations regardless of status
     * @return List of specialization information as maps
     * @throws SQLException If a database error occurs
     */
    public List<Map<String, Object>> getAllSpecializations() throws SQLException {
        List<Map<String, Object>> specializations = new ArrayList<>();
        
        String sql = "SELECT Specialization_Id, Specialization_Name, Specialization_Image, Specialization_Status " +
                     "FROM Specialization " +
                     "ORDER BY Specialization_Name";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Map<String, Object> specialization = new HashMap<>();
                specialization.put("id", rs.getInt("Specialization_Id"));
                specialization.put("name", rs.getString("Specialization_Name"));
                specialization.put("image", rs.getString("Specialization_Image"));
                specialization.put("status", rs.getString("Specialization_Status"));
                specializations.add(specialization);
            }
        }
        
        return specializations;
    }
    
    /**
     * Get specialization by ID
     * @param specializationId The ID of the specialization to retrieve
     * @return Specialization information as a map or null if not found
     * @throws SQLException If a database error occurs
     */
    public Map<String, Object> getSpecializationById(int specializationId) throws SQLException {
        String sql = "SELECT Specialization_Id, Specialization_Name, Specialization_Image, Specialization_Status " +
                     "FROM Specialization WHERE Specialization_Id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, specializationId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Map<String, Object> specialization = new HashMap<>();
                    specialization.put("id", rs.getInt("Specialization_Id"));
                    specialization.put("name", rs.getString("Specialization_Name"));
                    specialization.put("image", rs.getString("Specialization_Image"));
                    specialization.put("status", rs.getString("Specialization_Status"));
                    specialization.put("description", ""); // Placeholder for description
                    return specialization;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Get specializations for dropdown menu
     * @return Map of specialization IDs to names
     * @throws SQLException If a database error occurs
     */
    public Map<Integer, String> getSpecializationsForDropdown() throws SQLException {
        Map<Integer, String> specializations = new HashMap<>();
        
        String sql = "SELECT Specialization_Id, Specialization_Name " +
                     "FROM Specialization " +
                     "WHERE Specialization_Status = 'Active' " +
                     "ORDER BY Specialization_Name";
        
        System.out.println("SpecializationDAO: Getting specializations for dropdown");
        
        Connection conn = null;
        try {
            conn = getConnection();
            if (conn == null) {
                System.out.println("SpecializationDAO: ERROR - Connection is null");
                return specializations;
            }
            
            System.out.println("SpecializationDAO: Connection established successfully");
            
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                
                System.out.println("SpecializationDAO: Executing query: " + sql);
                
                int count = 0;
                while (rs.next()) {
                    count++;
                    int id = rs.getInt("Specialization_Id");
                    String name = rs.getString("Specialization_Name");
                    specializations.put(id, name);
                    System.out.println("SpecializationDAO: Found specialization - ID: " + id + ", Name: " + name);
                }
                
                System.out.println("SpecializationDAO: Query returned " + count + " rows");
                System.out.println("SpecializationDAO: Found " + specializations.size() + " specializations");
            }
        } catch (SQLException e) {
            System.out.println("SpecializationDAO: Error in getSpecializationsForDropdown: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("SpecializationDAO: Connection closed");
                } catch (SQLException e) {
                    System.out.println("SpecializationDAO: Error closing connection: " + e.getMessage());
                }
            }
        }
        
        return specializations;
    }
    
    /**
     * Add a new specialization
     * @param name The name of the specialization
     * @param description The description of the specialization (not stored in database)
     * @param image The image URL of the specialization
     * @param status The status of the specialization
     * @param createdBy The ID of the user who created the specialization (not stored in database)
     * @return The ID of the newly created specialization
     * @throws SQLException If a database error occurs
     */
    public int addSpecialization(String name, String description, String image, String status, int createdBy) throws SQLException {
        String sql = "INSERT INTO Specialization (Specialization_Name, Specialization_Image, Specialization_Status) " +
                     "VALUES (?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, name);
            stmt.setString(2, image);
            stmt.setString(3, status);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating specialization failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating specialization failed, no ID obtained.");
                }
            }
        }
    }
    
    /**
     * Update an existing specialization
     * @param id The ID of the specialization to update
     * @param name The updated name
     * @param description The updated description (not stored in database)
     * @param image The updated image URL
     * @param status The updated status
     * @param updatedBy The ID of the user who updated the specialization (not stored in database)
     * @return true if the update was successful, false otherwise
     * @throws SQLException If a database error occurs
     */
    public boolean updateSpecialization(int id, String name, String description, String image, String status, int updatedBy) throws SQLException {
        String sql = "UPDATE Specialization SET Specialization_Name = ?, " +
                     "Specialization_Image = ?, Specialization_Status = ? " +
                     "WHERE Specialization_Id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, name);
            stmt.setString(2, image);
            stmt.setString(3, status);
            stmt.setInt(4, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    /**
     * Delete a specialization
     * @param id The ID of the specialization to delete
     * @return true if the deletion was successful, false otherwise
     * @throws SQLException If a database error occurs
     */
    public boolean deleteSpecialization(int id) throws SQLException {
        // Check if specialization is used by any doctor
        String checkSql = "SELECT COUNT(*) FROM Doctor WHERE Specialization_Id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            
            checkStmt.setInt(1, id);
            
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // Specialization is in use, cannot delete
                    return false;
                }
            }
            
            // If not in use, proceed with deletion
            String deleteSql = "DELETE FROM Specialization WHERE Specialization_Id = ?";
            
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.setInt(1, id);
                int affectedRows = deleteStmt.executeUpdate();
                return affectedRows > 0;
            }
        }
    }
    
    /**
     * Change the status of a specialization
     * @param id The ID of the specialization
     * @param status The new status ('Active' or 'Deactive')
     * @param updatedBy The ID of the user who updated the status (not stored in database)
     * @return true if the update was successful, false otherwise
     * @throws SQLException If a database error occurs
     */
    public boolean changeSpecializationStatus(int id, String status, int updatedBy) throws SQLException {
        String sql = "UPDATE Specialization SET Specialization_Status = ? WHERE Specialization_Id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            stmt.setInt(2, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    /**
     * Count total number of specializations
     * @return The total number of specializations
     * @throws SQLException If a database error occurs
     */
    public int countSpecializations() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Specialization";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        
        return 0;
    }
} 