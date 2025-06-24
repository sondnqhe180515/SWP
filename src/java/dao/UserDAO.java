/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author admin
 */
public class UserDAO extends DBContext {

    private static final String CUSTOMER_ROLE = "Khách hàng";

    //xử lí đăng ký tài khoản
    public void insertUser(User user) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connection is null. Please check DBContext configuration or database availability.");
        }
        String sql = "INSERT INTO Users (FullName, Email, PasswordHash, PhoneNumber, Address, RoleID, Status, DateOfBirth, Gender) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordHash()); // theo class User
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

    //val check trùng email
    public boolean isEmailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Users WHERE Email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    // Kiểm tra vai trò của người dùng (Chỉ khách hàng RoleID = 1)
    public boolean isCustomer(int userId) {
        String sql = "SELECT RoleID FROM Users WHERE UserID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("RoleID") == 1; // RoleID = 1 là Khách hàng
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // Hiển thị thông tin cá nhân
    public User getUserById(int userId) {
        String sql = "SELECT u.*, r.RoleName FROM Users u LEFT JOIN Roles r ON u.RoleID = r.RoleID WHERE u.UserID = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, userId); // Set tham số trước khi execute
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("UserID"));
                    user.setFullName(rs.getString("FullName"));
                    user.setEmail(rs.getString("Email"));
                    user.setPhoneNumber(rs.getString("PhoneNumber"));
                    user.setAddress(rs.getString("Address"));
                    user.setRoleId(rs.getInt("RoleID"));
                    user.setStatus(rs.getBoolean("Status"));
                    user.setCreatedAt(rs.getDate("CreatedAt"));
                    user.setDateOfBirth(rs.getDate("DateOfBirth"));
                    user.setGender(rs.getString("Gender"));
                    user.setRoleName(rs.getString("RoleName"));
                    return user;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user by ID " + userId + ": " + e.getMessage());
        }
        return null;
    }

    // Phương thức lấy danh sách người dùng với phân trang
    public List<User> getUsersByPage(int page, int pageSize, String roleFilter, List<User> userList) {
        List<User> paginatedList = new ArrayList<>();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, userList.size());

        if (start < userList.size()) {
            for (int i = start; i < end; i++) {
                paginatedList.add(userList.get(i));
            }
        }
        return paginatedList;
    }

    // Phương thức lấy tất cả người dùng theo thứ tự trong DB (theo UserID)
    public List<User> getUsersByDefaultOrder(String roleFilter) {
        List<User> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u.UserID, u.FullName, u.Email, u.PhoneNumber, u.Address, r.RoleName ")
                .append("FROM Users u ")
                .append("JOIN Roles r ON u.RoleID = r.RoleID ");

        if (roleFilter != null && !roleFilter.isEmpty()) {
            sql.append("WHERE r.RoleName = ? ");
        }

        sql.append("ORDER BY u.UserID");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            if (roleFilter != null && !roleFilter.isEmpty()) {
                ps.setString(1, roleFilter);
            }
            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Phương thức sắp xếp người dùng theo tên (A-Z)
    public List<User> sortUsersByNameAZ(String roleFilter) {
        List<User> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u.UserID, u.FullName, u.Email, u.PhoneNumber, u.Address, r.RoleName, ")
                .append("CASE WHEN CHARINDEX(' ', REVERSE(u.FullName)) > 0 ")
                .append("THEN SUBSTRING(u.FullName, LEN(u.FullName) - CHARINDEX(' ', REVERSE(u.FullName)) + 2, LEN(u.FullName)) ")
                .append("ELSE u.FullName END AS FirstName ")
                .append("FROM Users u ")
                .append("JOIN Roles r ON u.RoleID = r.RoleID ");

        if (roleFilter != null && !roleFilter.isEmpty()) {
            sql.append("WHERE r.RoleName = ? ");
        }

        sql.append("ORDER BY FirstName ASC");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            if (roleFilter != null && !roleFilter.isEmpty()) {
                ps.setString(1, roleFilter);
            }
            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Phương thức tìm kiếm người dùng theo FullName hoặc Email
    public List<User> searchUsers(String keyword, String roleFilter, String sortBy) {
        List<User> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u.UserID, u.FullName, u.Email, u.PhoneNumber, u.Address, r.RoleName, ")
                .append("CASE WHEN CHARINDEX(' ', REVERSE(u.FullName)) > 0 ")
                .append("THEN SUBSTRING(u.FullName, LEN(u.FullName) - CHARINDEX(' ', REVERSE(u.FullName)) + 2, LEN(u.FullName)) ")
                .append("ELSE u.FullName END AS FirstName ")
                .append("FROM Users u ")
                .append("JOIN Roles r ON u.RoleID = r.RoleID ")
                .append("WHERE (u.FullName LIKE ? OR u.Email LIKE ?)");

        if (roleFilter != null && !roleFilter.isEmpty()) {
            sql.append(" AND r.RoleName = ?");
        }

        if ("name".equalsIgnoreCase(sortBy)) {
            sql.append(" ORDER BY FirstName ASC");
        } else {
            sql.append(" ORDER BY u.UserID");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            if (roleFilter != null && !roleFilter.isEmpty()) {
                ps.setString(3, roleFilter);
            }
            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (SQLException e) {
            System.out.println("Error searching users: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // Phương thức đếm tổng số người dùng theo tìm kiếm
    public int getTotalUsersWithSearch(String keyword, String roleFilter) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Users u JOIN Roles r ON u.RoleID = r.RoleID ");
        sql.append("WHERE (u.FullName LIKE ? OR u.Email LIKE ?)");
        if (roleFilter != null && !roleFilter.isEmpty()) {
            sql.append(" AND r.RoleName = ?");
        }
        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            if (roleFilter != null && !roleFilter.isEmpty()) {
                ps.setString(3, roleFilter);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error counting users with search: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    //Thống kê số lượng
    public int getTotalUsers(String roleFilter) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Users u JOIN Roles r ON u.RoleID = r.RoleID");
        if (roleFilter != null && !roleFilter.isEmpty()) {
            sql.append(" WHERE r.RoleName = ?");
        }
        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            if (roleFilter != null && !roleFilter.isEmpty()) {
                ps.setString(1, roleFilter);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Lấy danh sách khách hàng theo thứ tự mặc định (theo UserID)
    public List<User> getCustomersByDefaultOrder() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT u.UserID, u.FullName, u.Email, u.PhoneNumber, u.Address, r.RoleName, u.Gender, u.DateOfBirth "
                + "FROM Users u "
                + "JOIN Roles r ON u.RoleID = r.RoleID "
                + "WHERE r.RoleName = ? "
                + "ORDER BY u.UserID";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, CUSTOMER_ROLE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("UserID"));
                    user.setFullName(rs.getString("FullName"));
                    user.setEmail(rs.getString("Email"));
                    user.setPhoneNumber(rs.getString("PhoneNumber"));
                    user.setAddress(rs.getString("Address"));
                    user.setRoleName(rs.getString("RoleName"));
                    user.setGender(rs.getString("Gender"));
                    user.setDateOfBirth(rs.getDate("DateOfBirth"));
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching customers by default order: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // Sắp xếp khách hàng theo tên (A-Z)
    public List<User> sortCustomersByNameAZ() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT u.UserID, u.FullName, u.Email, u.PhoneNumber, u.Address, r.RoleName, u.Gender, u.DateOfBirth, "
                + "CASE WHEN CHARINDEX(' ', REVERSE(u.FullName)) > 0 "
                + "THEN SUBSTRING(u.FullName, LEN(u.FullName) - CHARINDEX(' ', REVERSE(u.FullName)) + 2, LEN(u.FullName)) "
                + "ELSE u.FullName END AS FirstName "
                + "FROM Users u "
                + "JOIN Roles r ON u.RoleID = r.RoleID "
                + "WHERE r.RoleName = ? "
                + "ORDER BY FirstName ASC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, CUSTOMER_ROLE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("UserID"));
                    user.setFullName(rs.getString("FullName"));
                    user.setEmail(rs.getString("Email"));
                    user.setPhoneNumber(rs.getString("PhoneNumber"));
                    user.setAddress(rs.getString("Address"));
                    user.setRoleName(rs.getString("RoleName"));
                    user.setGender(rs.getString("Gender"));
                    user.setDateOfBirth(rs.getDate("DateOfBirth"));
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error sorting customers by name: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // Tìm kiếm khách hàng theo FullName hoặc Email
    public List<User> searchCustomers(String keyword, String sortBy) {
        List<User> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u.UserID, u.FullName, u.Email, u.PhoneNumber, u.Address, r.RoleName, u.Gender, u.DateOfBirth, ")
                .append("CASE WHEN CHARINDEX(' ', REVERSE(u.FullName)) > 0 ")
                .append("THEN SUBSTRING(u.FullName, LEN(u.FullName) - CHARINDEX(' ', REVERSE(u.FullName)) + 2, LEN(u.FullName)) ")
                .append("ELSE u.FullName END AS FirstName ")
                .append("FROM Users u ")
                .append("JOIN Roles r ON u.RoleID = r.RoleID ")
                .append("WHERE (u.FullName LIKE ? OR u.Email LIKE ?) ")
                .append("AND r.RoleName = ? ");

        if ("name".equalsIgnoreCase(sortBy)) {
            sql.append("ORDER BY FirstName ASC");
        } else {
            sql.append("ORDER BY u.UserID");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, CUSTOMER_ROLE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("UserID"));
                    user.setFullName(rs.getString("FullName"));
                    user.setEmail(rs.getString("Email"));
                    user.setPhoneNumber(rs.getString("PhoneNumber"));
                    user.setAddress(rs.getString("Address"));
                    user.setRoleName(rs.getString("RoleName"));
                    user.setGender(rs.getString("Gender"));
                    user.setDateOfBirth(rs.getDate("DateOfBirth"));
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching customers: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
}
