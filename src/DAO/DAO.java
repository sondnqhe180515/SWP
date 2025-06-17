package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import dal.DBContext;
import Model.Account;

/**
 *
 * @author An
 */
public class DAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

//     Phương thức mới để lấy danh sách người dùng theo vai trò cụ thể
    public List<Account> getAllAcc() {
        List<Account> list = new ArrayList<>();
        // Câu truy vấn SQL để JOIN bảng Users và Roles, lọc theo RoleName
        String query = "select * from users";
        try {
            conn = new DBContext().getConnection(); // Mở kết nối với SQL Server
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                // Khởi tạo đối tượng Account từ ResultSet
                list.add(new Account(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4), 
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getBoolean(8),
                        rs.getString(9)
                ));
            }
        } catch (Exception e) {
        }
        return list;
    }

        public Account getUserID(String UserID) {
        // Câu truy vấn SQL để JOIN bảng Users và Roles, lọc theo RoleName
        String query = "select * from users\n" + "where UserID = ?";
        try {
            conn = new DBContext().getConnection(); // Mở kết nối với SQL Server
            ps = conn.prepareStatement(query);
            ps.setString(1, UserID);
            rs = ps.executeQuery();

            while (rs.next()) {
                // Khởi tạo đối tượng Account từ ResultSet
                return new Account(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4), 
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getBoolean(8),
                        rs.getString(9)
                );
            }
        } catch (Exception e) {
        }
        return null;
    }

        
   
   // Thêm người dùng
 public void addUser(Account user) {
        String sql = "INSERT INTO Account (fullName, email, phoneNumber, address, roleId, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhoneNumber());
            stmt.setString(4, user.getAddress());
            stmt.setInt(5, user.getRoleId());
            stmt.setBoolean(6, user.isStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Log lỗi
        } catch (Exception e) {
            e.printStackTrace(); // Lỗi từ DBContext
        }
    }
//  // getAllAcc() 
//    public List<Account> getAllAcc() {
//        List<Account> list = new ArrayList<>();
//        String sql = "SELECT * FROM Account";
//        try (Connection conn = new DBContext().getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                Account acc = new Account();
//                acc.setUserId(rs.getInt("userId"));
//                acc.setFullName(rs.getString("fullName"));
//                acc.setEmail(rs.getString("email"));
//                acc.setPhoneNumber(rs.getString("phoneNumber"));
//                acc.setAddress(rs.getString("address"));
//                acc.setRoleId(rs.getInt("roleId"));
//                acc.setStatus(rs.getBoolean("status"));
//                list.add(acc);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }


public void updateUser(Account user) {
    String sql = "UPDATE Users SET fullName=?, email=?, phoneNumber=?, address=?, roleId=?, status=? WHERE userId=?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, user.getFullName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPhoneNumber());
        ps.setString(4, user.getAddress());
        ps.setInt(5, user.getRoleId());
        ps.setBoolean(6, user.isStatus());
        ps.setInt(7, user.getUserId());
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}


 // Xóa người dùng
    public void deleteUser(int userId) {
    String sql = "DELETE FROM Account WHERE userId=?";
    try (Connection conn = new DBContext().getConnection(); 
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, userId);
        stmt.executeUpdate();
    } catch (Exception e) { // Bắt luôn cả Exception từ DBContext
        e.printStackTrace();
    }
}
}