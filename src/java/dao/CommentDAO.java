package dao;

import database.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Comment;

public class CommentDAO {
    
    public List<Comment> getCommentsByBlogId(int blogId) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM Comment WHERE Blog_Id = ? AND Comment_Status = 'Approved' ORDER BY Comment_CreatedDate DESC";
        
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, blogId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Comment comment = new Comment();
                    comment.setCommentId(rs.getInt("Comment_Id"));
                    comment.setBlogId(rs.getInt("Blog_Id"));
                    comment.setCommentContent(rs.getString("Comment_Content"));
                    comment.setCommentStatus(rs.getString("Comment_Status"));
                    comment.setCommentCreatedDate(rs.getDate("Comment_CreatedDate"));
                    comment.setCommentCreatedBy(rs.getInt("Comment_CreatedBy"));
                    comments.add(comment);
                }
            }
        }
        
        return comments;
    }
    
    public void addComment(Comment comment) throws SQLException {
        String sql = "INSERT INTO Comment (Blog_Id, Comment_Content, Comment_Status, Comment_CreatedDate, Comment_CreatedBy) VALUES (?, ?, 'Pending', GETDATE(), ?)";
        
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, comment.getBlogId());
            ps.setString(2, comment.getCommentContent());
            ps.setInt(3, comment.getCommentCreatedBy());
            
            ps.executeUpdate();
        }
    }
    
    public int getTotalComments() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Comment";
        
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        
        return 0;
    }
    
    public int getPendingComments() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Comment WHERE Comment_Status = 'Pending'";
        
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        
        return 0;
    }
    
    public List<Comment> getAllComments() throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT c.*, b.Blog_Title FROM Comment c JOIN Blog b ON c.Blog_Id = b.Blog_Id ORDER BY c.Comment_CreatedDate DESC";
        
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentId(rs.getInt("Comment_Id"));
                comment.setBlogId(rs.getInt("Blog_Id"));
                comment.setCommentContent(rs.getString("Comment_Content"));
                comment.setCommentStatus(rs.getString("Comment_Status"));
                comment.setCommentCreatedDate(rs.getDate("Comment_CreatedDate"));
                comment.setCommentCreatedBy(rs.getInt("Comment_CreatedBy"));
                comment.setBlogTitle(rs.getString("Blog_Title")); // Additional field for admin view
                comments.add(comment);
            }
        }
        
        return comments;
    }
    
    public void updateCommentStatus(int commentId, String status) throws SQLException {
        String sql = "UPDATE Comment SET Comment_Status = ? WHERE Comment_Id = ?";
        
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, status);
            ps.setInt(2, commentId);
            
            ps.executeUpdate();
        }
    }
    
    public void deleteComment(int commentId) throws SQLException {
        String sql = "DELETE FROM Comment WHERE Comment_Id = ?";
        
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, commentId);
            
            ps.executeUpdate();
        }
    }
} 