package controller;

import dao.BlogDAO;
import dao.DoctorDAO;
import dao.SpecializationDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Blog;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home", "/HomeServlet", "/index.jsp"})
public class HomeServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            System.out.println("HomeServlet: Starting to load data");
            
            // Get specializations
            SpecializationDAO specializationDAO = new SpecializationDAO();
            List<Map<String, Object>> specializations = specializationDAO.getAllActiveSpecializations();
            request.setAttribute("specializations", specializations);
            System.out.println("HomeServlet: Loaded " + (specializations != null ? specializations.size() : 0) + " specializations");
            
            // Get doctors
            DoctorDAO doctorDAO = new DoctorDAO();
            List<Map<String, Object>> doctors = doctorDAO.getAllActiveDoctors();
            request.setAttribute("doctors", doctors);
            System.out.println("HomeServlet: Loaded " + (doctors != null ? doctors.size() : 0) + " doctors");
            
            // Get recent blogs
            BlogDAO blogDAO = new BlogDAO();
            List<Blog> recentBlogs = blogDAO.getRecentBlogs(3); // Get 3 most recent blogs
            request.setAttribute("recentBlogs", recentBlogs);
            System.out.println("HomeServlet: Loaded " + (recentBlogs != null ? recentBlogs.size() : 0) + " recent blogs");
            
            // Forward to home page
            request.getRequestDispatcher("home.jsp").forward(request, response);
            System.out.println("HomeServlet: Successfully forwarded to home.jsp");
            
        } catch (SQLException e) {
            System.out.println("Error in HomeServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi tải dữ liệu trang chủ: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Unexpected error in HomeServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi không xác định: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
