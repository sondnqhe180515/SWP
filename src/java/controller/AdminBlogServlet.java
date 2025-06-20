package controller;

import dao.BlogDAO;
import dao.CategoryBlogDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Blog;
import model.CategoryBlog;

@WebServlet(name = "AdminBlogServlet", urlPatterns = {"/admin/blogs"})
public class AdminBlogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Check if user is logged in and is admin
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("account");
            
            if (account == null || !account.isAdmin()) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            
            String action = request.getParameter("action");
            
            // API to get blog by ID
            if ("getBlog".equals(action)) {
                int blogId = Integer.parseInt(request.getParameter("blogId"));
                BlogDAO blogDAO = new BlogDAO();
                Blog blog = blogDAO.getBlogById(blogId);
                
                if (blog != null) {
                    // Set response as JSON
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    
                    // Manually create JSON
                    String jsonBlog = "{"
                        + "\"blogId\":" + blog.getBlogId() + ","
                        + "\"blogTitle\":\"" + escapeJsonString(blog.getBlogTitle()) + "\","
                        + "\"blogDescription\":\"" + escapeJsonString(blog.getBlogDescription()) + "\","
                        + "\"blogImage\":\"" + escapeJsonString(blog.getBlogImage()) + "\","
                        + "\"categoryBlogId\":" + blog.getCategoryBlogId() + ","
                        + "\"blogStatus\":\"" + escapeJsonString(blog.getBlogStatus()) + "\""
                        + "}";
                    
                    // Send response
                    PrintWriter out = response.getWriter();
                    out.print(jsonBlog);
                    out.flush();
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            }
            
            // Normal page load
            // Get all blogs
            BlogDAO blogDAO = new BlogDAO();
            List<Blog> blogs = blogDAO.getAllBlogs();
            
            // Get all categories for the form
            CategoryBlogDAO categoryDAO = new CategoryBlogDAO();
            List<CategoryBlog> categories = categoryDAO.getAllCategories();
            
            // Set attributes
            request.setAttribute("blogs", blogs);
            request.setAttribute("categories", categories);
            
            // Forward to blog management page
            request.getRequestDispatcher("/admin/blogs.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.out.println("Error in AdminBlogServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    // Helper method to escape JSON strings
    private String escapeJsonString(String input) {
        if (input == null) {
            return "";
        }
        
        return input.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Check if user is logged in and is admin
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("account");
            
            if (account == null || !account.isAdmin()) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            
            // Get form data
            String action = request.getParameter("action");
            
            if ("add".equals(action)) {
                // Add new blog
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                String image = request.getParameter("image");
                String status = request.getParameter("status");
                int categoryId = Integer.parseInt(request.getParameter("categoryId"));
                
                Blog blog = new Blog();
                blog.setBlogTitle(title);
                blog.setBlogDescription(description);
                blog.setBlogContent(description); // Use description as content
                blog.setBlogImage(image);
                blog.setCategoryBlogId(categoryId);
                blog.setBlogStatus(status);
                blog.setBlogCreatedBy(account.getAccountId());
                
                BlogDAO blogDAO = new BlogDAO();
                blogDAO.addBlog(blog);
                
                response.sendRedirect(request.getContextPath() + "/admin/blogs?success=add");
                
            } else if ("edit".equals(action)) {
                // Edit existing blog
                int blogId = Integer.parseInt(request.getParameter("blogId"));
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                String image = request.getParameter("image");
                int categoryId = Integer.parseInt(request.getParameter("categoryId"));
                String status = request.getParameter("status");
                
                Blog blog = new Blog();
                blog.setBlogId(blogId);
                blog.setBlogTitle(title);
                blog.setBlogDescription(description);
                blog.setBlogContent(description); // Use description as content
                blog.setBlogImage(image);
                blog.setCategoryBlogId(categoryId);
                blog.setBlogStatus(status);
                
                BlogDAO blogDAO = new BlogDAO();
                blogDAO.updateBlog(blog);
                
                response.sendRedirect(request.getContextPath() + "/admin/blogs?success=edit");
                
            } else if ("delete".equals(action)) {
                // Delete blog
                int blogId = Integer.parseInt(request.getParameter("blogId"));
                
                BlogDAO blogDAO = new BlogDAO();
                blogDAO.deleteBlog(blogId);
                
                response.sendRedirect(request.getContextPath() + "/admin/blogs?success=delete");
            }
            
        } catch (Exception e) {
            System.out.println("Error in AdminBlogServlet doPost: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/blogs?error=true");
        }
    }
} 