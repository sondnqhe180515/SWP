/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Role;
import model.User;
import dal.DBContext;

/**
 *
 * @author dungb
 */
public class UserDAO extends DBContext {
    Connection con;
    PreparedStatement pstm;
    ResultSet rs;

    public UserDAO() {
        con = super.conn;
    }
    
    /**
     * Authenticate user with email and password
     * @param email User's email
     * @param password User's raw password (will be hashed)
     * @return User object if authentication successful, null otherwise
     */
    public User login(String email, String password) {
        try {
            String sql = "SELECT u.*, r.RoleName FROM Users u "
                    + "JOIN Roles r ON u.RoleID = r.RoleID "
                    + "WHERE u.Email = ? AND u.PasswordHash = ? AND u.Status = 1";
            
            pstm = con.prepareStatement(sql);
            pstm.setString(1, email);
            pstm.setString(2, password); // In production, use password hashing!
            
            rs = pstm.executeQuery();
            
            if (rs.next()) {
                return createUserFromResultSet(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return null;
    }
    
    /**
     * Get user by email only (for remember me functionality)
     * @param email User's email
     * @return User object if found and active, null otherwise
     */
    public User getUserByEmail(String email) {
        try {
            String sql = "SELECT u.*, r.RoleName FROM Users u "
                    + "JOIN Roles r ON u.RoleID = r.RoleID "
                    + "WHERE u.Email = ? AND u.Status = 1";
            
            pstm = con.prepareStatement(sql);
            pstm.setString(1, email);
            
            rs = pstm.executeQuery();
            
            if (rs.next()) {
                return createUserFromResultSet(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return null;
    }
    
    /**
     * Update user password
     * @param email User's email
     * @param newPassword New password (should be hashed)
     * @return true if update successful, false otherwise
     */
    public boolean updatePassword(String email, String newPassword) {
        try {
            String sql = "UPDATE Users SET PasswordHash = ? WHERE Email = ? AND Status = 1";
            
            pstm = con.prepareStatement(sql);
            pstm.setString(1, newPassword);
            pstm.setString(2, email);
            
            int rowsAffected = pstm.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return false;
    }
    
    /**
     * Check if email exists and is active
     * @param email User's email
     * @return true if email exists and user is active, false otherwise
     */
    public boolean isEmailExists(String email) {
        try {
            String sql = "SELECT COUNT(*) FROM Users WHERE Email = ? AND Status = 1";
            
            pstm = con.prepareStatement(sql);
            pstm.setString(1, email);
            
            rs = pstm.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return false;
    }
    
    /**
     * Helper method to create User object from ResultSet
     */
    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserID(rs.getInt("UserID"));
        user.setFullName(rs.getString("FullName"));
        user.setEmail(rs.getString("Email"));
        user.setPasswordHash(rs.getString("PasswordHash"));
        user.setPhoneNumber(rs.getString("PhoneNumber"));
        user.setAddress(rs.getString("Address"));
        user.setRoleID(rs.getInt("RoleID"));
        user.setStatus(rs.getBoolean("Status"));
        user.setCreatedAt(rs.getTimestamp("CreatedAt"));
        
        // Set the role object
        Role role = new Role();
        role.setRoleID(rs.getInt("RoleID"));
        role.setRoleName(rs.getString("RoleName"));
        user.setRole(role);
        
        return user;
    }
    
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (pstm != null) pstm.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
       