package controller;

import dao.BlogDAO;
import dao.CategoryBlogDAO;
import dao.CommentDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Blog;
import model.CategoryBlog;
import model.Comment;

@WebServlet(name = "BlogDetailServlet", urlPatterns = {"/blog-detail"})
public class BlogDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int blogId = Integer.parseInt(request.getParameter("id"));
            
            BlogDAO blogDAO = new BlogDAO();
            CategoryBlogDAO categoryDAO = new CategoryBlogDAO();
            CommentDAO commentDAO = new CommentDAO();
            
            // Get blog details
            Blog blog = blogDAO.getBlogById(blogId);
            if (blog == null) {
                response.sendRedirect("error.jsp");
                return;
            }
            
            // Get related blogs
            List<Blog> relatedBlogs = blogDAO.getRelatedBlogs(blog.getCategoryBlogId(), blogId, 3);
            
            // Get recent blogs (overall)
            List<Blog> recentBlogs = blogDAO.getRecentBlogs(3);
            
            // Get recent blogs by category
            List<Blog> recentBlogsByCategory = blogDAO.getRecentBlogsByCategoryId(blog.getCategoryBlogId(), 3);
            
            // Get all categories
            List<CategoryBlog> categories = categoryDAO.getAllCategories();
            
            // Get comments for this blog
            List<Comment> comments = commentDAO.getCommentsByBlogId(blogId);
            
            request.setAttribute("blog", blog);
            request.setAttribute("relatedBlogs", relatedBlogs);
            request.setAttribute("recentBlogs", recentBlogs);
            request.setAttribute("recentBlogsByCategory", recentBlogsByCategory);
            request.setAttribute("categories", categories);
            request.setAttribute("comments", comments);
            
            request.getRequestDispatcher("blog-detail.jsp").forward(request, response);
            
        } catch (NumberFormatException | SQLException e) {
            System.out.println("Error in BlogDetailServlet: " + e.getMessage());
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get form data
            String content = request.getParameter("content");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            int blogId = Integer.parseInt(request.getParameter("blogId"));
            int userId = Integer.parseInt(request.getParameter("userId"));
            
            // Create new comment
            Comment comment = new Comment();
            comment.setCommentContent(content);
            comment.setBlogId(blogId);
            comment.setCommentCreatedBy(userId);
            
            // Save comment
            CommentDAO commentDAO = new CommentDAO();
            commentDAO.addComment(comment);
            
            // Redirect back to blog detail page
            response.sendRedirect("blog-detail?id=" + blogId);
            
        } catch (NumberFormatException | SQLException e) {
            System.out.println("Error in BlogDetailServlet doPost: " + e.getMessage());
            response.sendRedirect("error.jsp");
        }
    }
} 