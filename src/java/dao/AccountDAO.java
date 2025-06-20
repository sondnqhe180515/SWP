package dao;

import database.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Account;

public class AccountDAO {
    
    public Account login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM Account WHERE Account_Username = ? AND Account_Password = ?";
        
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account();
                    account.setAccountId(rs.getInt("Account_Id"));
                    account.setUsername(rs.getString("Account_Username"));
                    account.setPassword(rs.getString("Account_Password"));
                    account.setEmail(rs.getString("Account_Email"));
                    account.setFullname(rs.getString("Account_Fullname"));
                    account.setPhone(rs.getString("Account_Phone"));
                    account.setAddress(rs.getString("Account_Address"));
                    account.setGender(rs.getInt("Account_Gender"));
                    account.setBirthDate(rs.getDate("Account_Date"));
                    account.setImage(rs.getString("Account_Image"));
                    account.setRole(rs.getString("Role"));
                    account.setCreatedDate(rs.getDate("Account_CreatedDate"));
                    account.setStatus(rs.getString("Account_Status"));
                    return account;
                }
            }
        }
        
        return null;
    }
    
    public Account getAccountById(int accountId) throws SQLException {
        String sql = "SELECT * FROM Account WHERE Account_Id = ?";
        
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, accountId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account();
                    account.setAccountId(rs.getInt("Account_Id"));
                    account.setUsername(rs.getString("Account_Username"));
                    account.setPassword(rs.getString("Account_Password"));
                    account.setEmail(rs.getString("Account_Email"));
                    account.setFullname(rs.getString("Account_Fullname"));
                    account.setPhone(rs.getString("Account_Phone"));
                    account.setAddress(rs.getString("Account_Address"));
                    account.setGender(rs.getInt("Account_Gender"));
                    account.setBirthDate(rs.getDate("Account_Date"));
                    account.setImage(rs.getString("Account_Image"));
                    account.setRole(rs.getString("Role"));
                    account.setCreatedDate(rs.getDate("Account_CreatedDate"));
                    account.setStatus(rs.getString("Account_Status"));
                    return account;
                }
            }
        }
        
        return null;
    }
} 