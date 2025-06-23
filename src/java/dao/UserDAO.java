package dao;

import dal.DBContext;
import model.Role;
import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object for User-related database operations.
 */
public class UserDAO extends DBContext {

    private Connection connection;
    private PreparedStatement pstm;
    private ResultSet rs;

    public UserDAO() {
        this.connection = super.connection;
    }

    /**
     * Insert a new user into the database.
     *
     * @param user The User object to insert
     * @throws SQLException If a database error occurs
     */
    public void insertUser(User user) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connection is null. Please check DBContext configuration or database availability.");
        }
        String sql = "INSERT INTO Users (FullName, Email, PasswordHash, PhoneNumber, Address, RoleID, Status, DateOfBirth, Gender) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordHash()); // In production, ensure password is hashed!
            ps.setString(4, user.getPhoneNumber());
            if (user.getAddress() != null && !user.getAddress().isEmpty()) {
                ps.setString(5, user.getAddress());
            } else {
                ps.setNull(5, java.sql.Types.NVARCHAR);
            }
            ps.setInt(6, user.getRoleId());
            ps.setBoolean(7, user.isStatus());
            if (user.getDateOfBirth() != null) {
                ps.setDate(8, new java.sql.Date(user.getDateOfBirth().getTime()));
            } else {
                ps.setNull(8, java.sql.Types.DATE);
            }
            ps.setString(9, user.getGender());
            ps.executeUpdate();
        }
    }

    /**
     * Authenticate user with email and password.
     *
     * @param email User's email
     * @param password User's raw password (will be hashed)
     * @return User object if authentication successful, null otherwise
     */
    public User login(String email, String password) {
        try {
            String sql = "SELECT u.*, r.RoleName FROM Users u "
                    + "JOIN Roles r ON u.RoleID = r.RoleID "
                    + "WHERE u.Email = ? AND u.PasswordHash = ?";
            pstm = connection.prepareStatement(sql);
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
     * Get user by email only (for remember me functionality).
     *
     * @param email User's email
     * @return User object if found and active, null otherwise
     */
    public User getUserByEmail(String email) {
        try {
            String sql = "SELECT u.*, r.RoleName FROM Users u "
                    + "JOIN Roles r ON u.RoleID = r.RoleID "
                    + "WHERE u.Email = ? AND u.Status = 1";
            pstm = connection.prepareStatement(sql);
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
     * Get user by ID.
     *
     * @param userId User's ID
     * @return User object if found, null otherwise
     */
    public User getUserById(int userId) {
        try {
            String sql = "SELECT u.*, r.RoleName FROM Users u LEFT JOIN Roles r ON u.RoleID = r.RoleID WHERE u.UserID = ?";
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, userId);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return createUserFromResultSet(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, "Error fetching user by ID " + userId, ex);
        } finally {
            closeResources();
        }
        return null;
    }

    /**
     * Get user by ID (String version for compatibility).
     *
     * @param userId User's ID as a String
     * @return User object if found, null otherwise
     */
    public User getUserById(String userId) {
        try {
            int id = Integer.parseInt(userId); // Convert String to int
            return getUserById(id); // Delegate to the int version
        } catch (NumberFormatException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, "Invalid UserID format: " + userId, e);
            return null;
        }
    }

    /**
     * Update user profile.
     *
     * @param user User object with updated information
     * @return true if update successful, false otherwise
     * @throws SQLException If a database error occurs
     */
    public boolean updateProfile(User user) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connection is null. Please check DBContext configuration or database availability.");
        }
        String sql = "UPDATE Users SET FullName = ?, Email = ?, PasswordHash = ?, PhoneNumber = ?, Address = ?, DateOfBirth = ?, Gender = ? WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordHash()); // In production, ensure password is hashed!
            ps.setString(4, user.getPhoneNumber());
            if (user.getAddress() != null && !user.getAddress().isEmpty()) {
                ps.setString(5, user.getAddress());
            } else {
                ps.setNull(5, java.sql.Types.NVARCHAR);
            }
            if (user.getDateOfBirth() != null) {
                ps.setDate(6, new java.sql.Date(user.getDateOfBirth().getTime()));
            } else {
                ps.setNull(6, java.sql.Types.DATE);
            }
            ps.setString(7, user.getGender());
            ps.setInt(8, user.getUserId());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * Update user password.
     *
     * @param email User's email
     * @param newPassword New password (should be hashed)
     * @return true if update successful, false otherwise
     */
    public boolean updatePassword(String email, String newPassword) {
        try {
            String sql = "UPDATE Users SET PasswordHash = ? WHERE Email = ? AND Status = 1";
            pstm = connection.prepareStatement(sql);
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
     * Check if email exists and is active.
     *
     * @param email User's email
     * @return true if email exists and user is active, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean isEmailExists(String email) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connection is null. Please check DBContext configuration or database availability.");
        }
        try {
            String sql = "SELECT COUNT(*) FROM Users WHERE Email = ? AND Status = 1";
            pstm = connection.prepareStatement(sql);
            pstm.setString(1, email);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } finally {
            closeResources();
        }
    }

    /**
     * Check if user is a customer (RoleID = 1).
     *
     * @param userId User's ID
     * @return true if user is a customer, false otherwise
     */
    public boolean isCustomer(int userId) {
        try {
            String sql = "SELECT RoleID FROM Users WHERE UserID = ?";
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, userId);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt("RoleID") == 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return false;
    }

    /**
     * Get users by page with optional role filter and sorting.
     *
     * @param page Page number
     * @param pageSize Number of users per page
     * @param roleFilter Role name to filter by (optional)
     * @param sortBy Sorting criteria (e.g., "name" or "UserID")
     * @return List of User objects
     */
    public List<User> getUsersByPage(int page, int pageSize, String roleFilter, String sortBy) {
        List<User> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM (")
                .append("    SELECT ROW_NUMBER() OVER (ORDER BY ");
        if ("name".equalsIgnoreCase(sortBy)) {
            sql.append("SUBSTRING(u.FullName, LEN(u.FullName) - CHARINDEX(' ', REVERSE(u.FullName)) + 2, LEN(u.FullName))");
        } else {
            sql.append("u.UserID");
        }
        sql.append(") AS RowNum, ")
                .append("u.UserID, u.FullName, u.Email, u.PhoneNumber, u.Address, r.RoleName ")
                .append("FROM Users u ")
                .append("JOIN Roles r ON u.RoleID = r.RoleID ");
        if (roleFilter != null && !roleFilter.isEmpty()) {
            sql.append("WHERE r.RoleName = ? ");
        }
        sql.append(") AS temp WHERE RowNum BETWEEN ? AND ?");

        int start = (page - 1) * pageSize + 1;
        int end = page * pageSize;
        try {
            pstm = connection.prepareStatement(sql.toString());
            int paramIndex = 1;
            if (roleFilter != null && !roleFilter.isEmpty()) {
                pstm.setString(paramIndex++, roleFilter);
            }
            pstm.setInt(paramIndex++, start);
            pstm.setInt(paramIndex, end);
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(new User(
                        rs.getInt("UserID"),
                        rs.getString("FullName"),
                        rs.getString("Email"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Address"),
                        rs.getString("RoleName")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return list;
    }

    /**
     * Get total number of users with optional role filter.
     *
     * @param roleFilter Role name to filter by (optional)
     * @return Total number of users
     */
    public int getTotalUsers(String roleFilter) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Users u JOIN Roles r ON u.RoleID = r.RoleID");
        if (roleFilter != null && !roleFilter.isEmpty()) {
            sql.append(" WHERE r.RoleName = ?");
        }
        try {
            pstm = connection.prepareStatement(sql.toString());
            if (roleFilter != null && !roleFilter.isEmpty()) {
                pstm.setString(1, roleFilter);
            }
            rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return 0;
    }

    /**
     * Get all users from the database.
     *
     * @return List of all User objects
     */
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT u.UserID, u.FullName, u.Email, u.PasswordHash, u.PhoneNumber, u.Address, u.RoleID, u.Status, u.Gender, r.RoleName "
                + "FROM Users u LEFT JOIN Roles r ON u.RoleID = r.RoleID";
        try {
            if (connection == null) {
                throw new SQLException("Database connection is null. Please check DBContext configuration or database availability.");
            }
            pstm = connection.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                User user = new User(
                        rs.getInt("UserID"),
                        rs.getString("FullName"),
                        rs.getString("PasswordHash"),
                        rs.getString("Email"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Address"),
                        rs.getInt("RoleID"),
                        rs.getBoolean("Status"),
                        null, // createdAt (not retrieved)
                        null, // dateOfBirth (not retrieved)
                        rs.getString("Gender")
                );
                user.setRoleName(rs.getString("RoleName"));
                list.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, "Error fetching all users", ex);
        } finally {
            closeResources();
        }
        return list;
    }

    /**
     * Helper method to create User object from ResultSet.
     */
    // 1. Lấy danh sách nhân viên hoặc bác sĩ dựa theo role (1 = staff, 2 = doctor)
    public List<User> getUsersByRole(int roleId) {
        List<User> users = new ArrayList<>();
        try {
            String sql = "SELECT u.*, r.RoleName FROM Users u "
                    + "JOIN Roles r ON u.RoleID = r.RoleID "
                    + "WHERE u.RoleID = ?";
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, roleId);
            rs = pstm.executeQuery();
            while (rs.next()) {
                users.add(createUserFromResultSet(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return users;
    }

    // 2. Lấy toàn bộ nhân viên (staff và doctor) => roleId từ 1 đến 2
    public List<User> getAllEmployees() {
        List<User> users = new ArrayList<>();
        try {
            String sql = "SELECT u.*, r.RoleName FROM Users u "
                    + "JOIN Roles r ON u.RoleID = r.RoleID "
                    + "WHERE u.RoleID IN (1, 2)";
            pstm = connection.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                users.add(createUserFromResultSet(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return users;
    }

    // 3. Cập nhật trạng thái tài khoản (Active / Deactive)
    public boolean updateUserStatus(int userId, boolean isActive) {
        try {
            String sql = "UPDATE Users SET Status = ? WHERE UserID = ?";
            pstm = connection.prepareStatement(sql);
            pstm.setBoolean(1, isActive);
            pstm.setInt(2, userId);
            int rowsAffected = pstm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return false;
    }

    // Đếm tổng số user
    public int countAllUsers() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM Users";
        try {
            pstm = connection.prepareStatement(sql);
            rs = pstm.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return count;
    }

// Đếm theo RoleId (ví dụ: 1 - Customer, 2 - Staff, 3 - Doctor)
    public int countUsersByRole(int roleId) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM Users WHERE RoleID = ?";
        try {
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, roleId);
            rs = pstm.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return count;
    }

    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("UserID"));
        user.setFullName(rs.getString("FullName"));
        user.setEmail(rs.getString("Email"));
        user.setPasswordHash(rs.getString("PasswordHash"));
        user.setPhoneNumber(rs.getString("PhoneNumber"));
        user.setAddress(rs.getString("Address"));
        user.setRoleId(rs.getInt("RoleID"));
        user.setStatus(rs.getBoolean("Status"));
        user.setCreatedAt(rs.getTimestamp("CreatedAt"));
        user.setDateOfBirth(rs.getDate("DateOfBirth"));
        user.setGender(rs.getString("Gender"));
        user.setRoleName(rs.getString("RoleName"));

        // Set the role object
        Role role = new Role();
        role.setRoleID(rs.getInt("RoleID"));
        role.setRoleName(rs.getString("RoleName"));
        user.setRole(role);

        return user;
    }

    /**
     * Close database resources.
     */
    private void closeResources() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstm != null) {
                pstm.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
