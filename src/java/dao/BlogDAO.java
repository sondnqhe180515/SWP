package dao;

import database.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Blog;

public class BlogDAO extends DBContext {
    
    private void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("Error closing ResultSet: " + e.getMessage());
            }
        }
    }

    private void closePreparedStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                System.out.println("Error closing PreparedStatement: " + e.getMessage());
            }
        }
    }

    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing Connection: " + e.getMessage());
            }
        }
    }

    public List<Blog> getAllBlogs() throws SQLException {
        List<Blog> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT b.*, c.CategoryBlog_Name FROM Blog b "
                    + "INNER JOIN Category_Blog c ON b.CategoryBlog_Id = c.CategoryBlog_Id "
                    + "ORDER BY b.Blog_CreatedDate DESC";
            conn = getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Blog blog = new Blog(
                    rs.getInt("Blog_Id"),
                    rs.getString("Blog_Title"),
                    rs.getString("Blog_Description"),
                    rs.getString("Blog_Image"),
                    rs.getDate("Blog_CreatedDate"),
                    rs.getInt("CategoryBlog_Id"),
                    rs.getInt("Blog_CreatedBy"),
                    rs.getString("CategoryBlog_Name")
                );
                blog.setBlogContent(rs.getString("Blog_Description"));
                blog.setBlogStatus(rs.getString("Blog_Status"));
                list.add(blog);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(conn);
        }
        return list;
    }
    
    public Blog getBlogById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT b.*, c.CategoryBlog_Name FROM Blog b "
                    + "INNER JOIN Category_Blog c ON b.CategoryBlog_Id = c.CategoryBlog_Id "
                    + "WHERE b.Blog_Id = ?";
            conn = getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Blog blog = new Blog(
                    rs.getInt("Blog_Id"),
                    rs.getString("Blog_Title"),
                    rs.getString("Blog_Description"),
                    rs.getString("Blog_Image"),
                    rs.getDate("Blog_CreatedDate"),
                    rs.getInt("CategoryBlog_Id"),
                    rs.getInt("Blog_CreatedBy"),
                    rs.getString("CategoryBlog_Name")
                );
                blog.setBlogContent(rs.getString("Blog_Description"));
                blog.setBlogStatus(rs.getString("Blog_Status"));
                return blog;
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(conn);
        }
        return null;
    }
    
    public List<Blog> getBlogsByCategory(int categoryId) throws SQLException {
        List<Blog> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT b.*, c.CategoryBlog_Name FROM Blog b "
                    + "INNER JOIN Category_Blog c ON b.CategoryBlog_Id = c.CategoryBlog_Id "
                    + "WHERE b.CategoryBlog_Id = ? "
                    + "ORDER BY b.Blog_CreatedDate DESC";
            conn = getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, categoryId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Blog blog = new Blog(
                    rs.getInt("Blog_Id"),
                    rs.getString("Blog_Title"),
                    rs.getString("Blog_Description"),
                    rs.getString("Blog_Image"),
                    rs.getDate("Blog_CreatedDate"),
                    rs.getInt("CategoryBlog_Id"),
                    rs.getInt("Blog_CreatedBy"),
                    rs.getString("CategoryBlog_Name")
                );
                blog.setBlogContent(rs.getString("Blog_Description"));
                blog.setBlogStatus(rs.getString("Blog_Status"));
                list.add(blog);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(conn);
        }
        return list;
    }
    
    public List<Blog> getRecentBlogs(int limit) throws SQLException {
        List<Blog> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT TOP (?) b.*, c.CategoryBlog_Name FROM Blog b "
                    + "INNER JOIN Category_Blog c ON b.CategoryBlog_Id = c.CategoryBlog_Id "
                    + "ORDER BY b.Blog_CreatedDate DESC";
            conn = getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, limit);
            rs = ps.executeQuery();
            while (rs.next()) {
                Blog blog = new Blog(
                    rs.getInt("Blog_Id"),
                    rs.getString("Blog_Title"),
                    rs.getString("Blog_Description"),
                    rs.getString("Blog_Image"),
                    rs.getDate("Blog_CreatedDate"),
                    rs.getInt("CategoryBlog_Id"),
                    rs.getInt("Blog_CreatedBy"),
                    rs.getString("CategoryBlog_Name")
                );
                blog.setBlogContent(rs.getString("Blog_Description"));
                blog.setBlogStatus(rs.getString("Blog_Status"));
                list.add(blog);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(conn);
        }
        return list;
    }
    
    public List<Blog> getRecentBlogsByCategoryId(int categoryId, int limit) throws SQLException {
        List<Blog> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT TOP (?) b.*, c.CategoryBlog_Name FROM Blog b "
                    + "INNER JOIN Category_Blog c ON b.CategoryBlog_Id = c.CategoryBlog_Id "
                    + "WHERE b.CategoryBlog_Id = ? "
                    + "ORDER BY b.Blog_CreatedDate DESC";
            conn = getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, limit);
            ps.setInt(2, categoryId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Blog blog = new Blog(
                    rs.getInt("Blog_Id"),
                    rs.getString("Blog_Title"),
                    rs.getString("Blog_Description"),
                    rs.getString("Blog_Image"),
                    rs.getDate("Blog_CreatedDate"),
                    rs.getInt("CategoryBlog_Id"),
                    rs.getInt("Blog_CreatedBy"),
                    rs.getString("CategoryBlog_Name")
                );
                blog.setBlogContent(rs.getString("Blog_Description"));
                blog.setBlogStatus(rs.getString("Blog_Status"));
                list.add(blog);
            }
        } catch (Exception e) {
            System.out.println("getRecentBlogsByCategoryId: " + e);
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(conn);
        }
        return list;
    }
    
    public List<Blog> searchBlogs(String keyword) throws SQLException {
        List<Blog> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT b.*, c.CategoryBlog_Name FROM Blog b "
                    + "INNER JOIN Category_Blog c ON b.CategoryBlog_Id = c.CategoryBlog_Id "
                    + "WHERE b.Blog_Title LIKE ? OR b.Blog_Description LIKE ? "
                    + "ORDER BY b.Blog_CreatedDate DESC";
            conn = getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Blog blog = new Blog(
                    rs.getInt("Blog_Id"),
                    rs.getString("Blog_Title"),
                    rs.getString("Blog_Description"),
                    rs.getString("Blog_Image"),
                    rs.getDate("Blog_CreatedDate"),
                    rs.getInt("CategoryBlog_Id"),
                    rs.getInt("Blog_CreatedBy"),
                    rs.getString("CategoryBlog_Name")
                );
                blog.setBlogContent(rs.getString("Blog_Description"));
                blog.setBlogStatus(rs.getString("Blog_Status"));
                list.add(blog);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(conn);
        }
        return list;
    }

    public void addBlog(Blog blog) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            String query = "INSERT INTO Blog (Blog_Title, Blog_Description, Blog_Image, "
                    + "Blog_Status, Blog_CreatedDate, Blog_CreatedBy, CategoryBlog_Id) "
                    + "VALUES (?, ?, ?, ?, GETDATE(), ?, ?)";
            conn = getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, blog.getBlogTitle());
            ps.setString(2, blog.getBlogContent());
            ps.setString(3, blog.getBlogImage());
            ps.setString(4, blog.getBlogStatus());
            ps.setInt(5, blog.getBlogCreatedBy());
            ps.setInt(6, blog.getCategoryBlogId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("addBlog: " + e);
        } finally {
            closePreparedStatement(ps);
            closeConnection(conn);
        }
    }

    public void updateBlog(Blog blog) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            String query = "UPDATE Blog SET Blog_Title = ?, Blog_Description = ?, "
                    + "Blog_Image = ?, Blog_Status = ?, CategoryBlog_Id = ? WHERE Blog_Id = ?";
            conn = getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, blog.getBlogTitle());
            ps.setString(2, blog.getBlogContent());
            ps.setString(3, blog.getBlogImage());
            ps.setString(4, blog.getBlogStatus());
            ps.setInt(5, blog.getCategoryBlogId());
            ps.setInt(6, blog.getBlogId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("updateBlog: " + e);
        } finally {
            closePreparedStatement(ps);
            closeConnection(conn);
        }
    }

    public void deleteBlog(int blogId) throws SQLException {
        // First delete all comments associated with this blog
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // Delete comments first
            String deleteCommentsSql = "DELETE FROM Comment WHERE Blog_Id = ?";
            conn = getConnection();
            ps = conn.prepareStatement(deleteCommentsSql);
            ps.setInt(1, blogId);
            ps.executeUpdate();
            closePreparedStatement(ps);
            
            // Then delete the blog
            String deleteBlogSql = "DELETE FROM Blog WHERE Blog_Id = ?";
            ps = conn.prepareStatement(deleteBlogSql);
            ps.setInt(1, blogId);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("deleteBlog: " + e);
        } finally {
            closePreparedStatement(ps);
            closeConnection(conn);
        }
    }

    public List<Blog> getRelatedBlogs(int categoryBlogId, int excludeBlogId, int limit) throws SQLException {
        List<Blog> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT TOP (?) b.*, c.CategoryBlog_Name FROM Blog b "
                    + "INNER JOIN Category_Blog c ON b.CategoryBlog_Id = c.CategoryBlog_Id "
                    + "WHERE b.CategoryBlog_Id = ? AND b.Blog_Id <> ? "
                    + "ORDER BY b.Blog_CreatedDate DESC";
            conn = getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, limit);
            ps.setInt(2, categoryBlogId);
            ps.setInt(3, excludeBlogId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Blog blog = new Blog(
                    rs.getInt("Blog_Id"),
                    rs.getString("Blog_Title"),
                    rs.getString("Blog_Description"),
                    rs.getString("Blog_Image"),
                    rs.getDate("Blog_CreatedDate"),
                    rs.getInt("CategoryBlog_Id"),
                    rs.getInt("Blog_CreatedBy"),
                    rs.getString("CategoryBlog_Name")
                );
                blog.setBlogContent(rs.getString("Blog_Description"));
                blog.setBlogStatus(rs.getString("Blog_Status"));
                list.add(blog);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(conn);
        }
        return list;
    }

    public int getTotalBlogs() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT COUNT(*) FROM Blog";
            conn = getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("getTotalBlogs: " + e);
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(conn);
        }
        return 0;
    }
} 