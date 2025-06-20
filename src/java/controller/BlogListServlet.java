package controller;

import dao.BlogDAO;
import dao.CategoryBlogDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Blog;
import model.CategoryBlog;

@WebServlet(name = "BlogListServlet", urlPatterns = {"/blogs", "/BlogListServlet"})
public class BlogListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            BlogDAO blogDAO = new BlogDAO();
            CategoryBlogDAO categoryDAO = new CategoryBlogDAO();
            
            // Get category filter if any
            String categoryId = request.getParameter("category");
            List<Blog> blogs;
            
            if(categoryId != null && !categoryId.isEmpty()) {
                blogs = blogDAO.getBlogsByCategory(Integer.parseInt(categoryId));
            } else {
                blogs = blogDAO.getAllBlogs();
            }
            
            // Get search keyword if any
            String keyword = request.getParameter("search");
            if(keyword != null && !keyword.isEmpty()) {
                blogs = blogDAO.searchBlogs(keyword);
            }
            
            // Get all categories for sidebar
            List<CategoryBlog> categories = categoryDAO.getAllCategories();
            
            // Get recent blogs for sidebar
            List<Blog> recentBlogs = blogDAO.getRecentBlogs(3);
            
            System.out.println("So blog: " + (blogs == null ? "null" : blogs.size()));
            if (blogs != null && !blogs.isEmpty()) {
                Blog first = blogs.get(0);
                System.out.println("First blog: " + first.getBlogTitle() + ", " + first.getBlogDescription() + ", " + first.getBlogImage() + ", " + first.getBlogCreatedDate() + ", " + first.getCategoryBlogName());
            }
            
            request.setAttribute("blogs", blogs);
            request.setAttribute("categories", categories);
            request.setAttribute("recentBlogs", recentBlogs);
            request.setAttribute("selectedCategory", categoryId);
            request.setAttribute("searchKeyword", keyword);
            
            request.getRequestDispatcher("blogs_view.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.out.println("BlogListServlet: " + e.getMessage());
            request.setAttribute("errorMessage", "Lỗi khi tải danh sách bài viết: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
} 