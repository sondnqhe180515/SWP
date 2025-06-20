package dao;

import database.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.CategoryBlog;

public class CategoryBlogDAO extends DBContext {
    
    public List<CategoryBlog> getAllCategories() {
        List<CategoryBlog> list = new ArrayList<>();
        String sql = "SELECT * FROM Category_Blog WHERE CategoryBlog_Status = 'Active'";
        
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            
            while(rs.next()) {
                CategoryBlog category = new CategoryBlog();
                category.setCategoryBlogId(rs.getInt("CategoryBlog_Id"));
                category.setCategoryBlogName(rs.getString("CategoryBlog_Name"));
                category.setCategoryBlogStatus(rs.getString("CategoryBlog_Status"));
                list.add(category);
            }
        } catch (SQLException e) {
            System.out.println("getAllCategories: " + e.getMessage());
        }
        return list;
    }
    
    public List<CategoryBlog> getAllCategoriesWithStatus() {
        List<CategoryBlog> list = new ArrayList<>();
        String sql = "SELECT * FROM Category_Blog";
        
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            
            while(rs.next()) {
                CategoryBlog category = new CategoryBlog();
                category.setCategoryBlogId(rs.getInt("CategoryBlog_Id"));
                category.setCategoryBlogName(rs.getString("CategoryBlog_Name"));
                category.setCategoryBlogStatus(rs.getString("CategoryBlog_Status"));
                list.add(category);
            }
        } catch (SQLException e) {
            System.out.println("getAllCategoriesWithStatus: " + e.getMessage());
        }
        return list;
    }
    
    public CategoryBlog getCategoryById(int categoryId) {
        String sql = "SELECT * FROM Category_Blog WHERE CategoryBlog_Id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            
            st.setInt(1, categoryId);
            try (ResultSet rs = st.executeQuery()) {
                if(rs.next()) {
                    CategoryBlog category = new CategoryBlog();
                    category.setCategoryBlogId(rs.getInt("CategoryBlog_Id"));
                    category.setCategoryBlogName(rs.getString("CategoryBlog_Name"));
                    category.setCategoryBlogStatus(rs.getString("CategoryBlog_Status"));
                    return category;
                }
            }
        } catch (SQLException e) {
            System.out.println("getCategoryById: " + e.getMessage());
        }
        return null;
    }
    
    public int addCategory(String name, String status) {
        String sql = "INSERT INTO Category_Blog (CategoryBlog_Name, CategoryBlog_Status) VALUES (?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            st.setString(1, name);
            st.setString(2, status);
            
            int affectedRows = st.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating category failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating category failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.out.println("addCategory: " + e.getMessage());
        }
        return -1;
    }
    
    public boolean updateCategory(int id, String name, String status) {
        String sql = "UPDATE Category_Blog SET CategoryBlog_Name = ?, CategoryBlog_Status = ? WHERE CategoryBlog_Id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            
            st.setString(1, name);
            st.setString(2, status);
            st.setInt(3, id);
            
            int affectedRows = st.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("updateCategory: " + e.getMessage());
        }
        return false;
    }
    
    public boolean deleteCategory(int id) {
        // Check if category is used by any blog
        String checkSql = "SELECT COUNT(*) FROM Blog WHERE CategoryBlog_Id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement checkSt = conn.prepareStatement(checkSql)) {
            
            checkSt.setInt(1, id);
            try (ResultSet rs = checkSt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // Category is in use, cannot delete
                    return false;
                }
            }
            
            // If not in use, proceed with deletion
            String deleteSql = "DELETE FROM Category_Blog WHERE CategoryBlog_Id = ?";
            
            try (PreparedStatement deleteSt = conn.prepareStatement(deleteSql)) {
                deleteSt.setInt(1, id);
                
                int affectedRows = deleteSt.executeUpdate();
                return affectedRows > 0;
            }
        } catch (SQLException e) {
            System.out.println("deleteCategory: " + e.getMessage());
        }
        return false;
    }
    
    public boolean changeCategoryStatus(int id, String status) {
        // Đảm bảo status chỉ là 'Active' hoặc 'Deactive' theo ràng buộc CHECK constraint
        if (!"Active".equals(status) && !"Deactive".equals(status)) {
            status = "Deactive"; // Mặc định là Deactive nếu không phải giá trị hợp lệ
        }
        
        String sql = "UPDATE Category_Blog SET CategoryBlog_Status = ? WHERE CategoryBlog_Id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            
            st.setString(1, status);
            st.setInt(2, id);
            
            int affectedRows = st.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("changeCategoryStatus: " + e.getMessage());
        }
        return false;
    }
    
    public int countCategories() {
        String sql = "SELECT COUNT(*) FROM Category_Blog";
        
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("countCategories: " + e.getMessage());
        }
        return 0;
    }
    
    public int countActiveCategories() {
        String sql = "SELECT COUNT(*) FROM Category_Blog WHERE CategoryBlog_Status = 'Active'";
        
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("countActiveCategories: " + e.getMessage());
        }
        return 0;
    }
} 