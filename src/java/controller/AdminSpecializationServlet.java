package controller;

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
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Doctor;

/**
 * Servlet for managing specializations in the admin panel
 */
@WebServlet(name = "AdminSpecializationServlet", urlPatterns = {
    "/admin/specializations", 
    "/admin/specializations/add", 
    "/admin/specializations/edit",
    "/admin/specializations/view",
    "/admin/specializations/delete", 
    "/admin/specializations/status"
})
public class AdminSpecializationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String path = request.getServletPath();
        
        try {
            // Check if user is logged in and is admin
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("account");
            
            if (account == null || !account.isAdmin()) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }
            
            SpecializationDAO specializationDAO = new SpecializationDAO();
            
            if (path.equals("/admin/specializations")) {
                // List all specializations
                List<Map<String, Object>> specializations = specializationDAO.getAllSpecializations();
                request.setAttribute("specializations", specializations);
                request.setAttribute("totalSpecializations", specializationDAO.countSpecializations());
                
                // Count active and inactive specializations
                int activeCount = 0;
                int inactiveCount = 0;
                for (Map<String, Object> spec : specializations) {
                    if ("Active".equals(spec.get("status"))) {
                        activeCount++;
                    } else {
                        inactiveCount++;
                    }
                }
                request.setAttribute("activeSpecializations", activeCount);
                request.setAttribute("inactiveSpecializations", inactiveCount);
                
                request.getRequestDispatcher("/admin/specializations.jsp").forward(request, response);
                
            } else if (path.equals("/admin/specializations/add")) {
                // Show add form
                request.getRequestDispatcher("/admin/specialization-form.jsp").forward(request, response);
                
            } else if (path.equals("/admin/specializations/edit")) {
                // Show edit form with specialization data
                int id = Integer.parseInt(request.getParameter("id"));
                Map<String, Object> specialization = specializationDAO.getSpecializationById(id);
                
                if (specialization != null) {
                    request.setAttribute("specialization", specialization);
                    request.getRequestDispatcher("/admin/specialization-form.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/specializations?error=not_found");
                }
                
            } else if (path.equals("/admin/specializations/view")) {
                // Show specialization details
                int id = Integer.parseInt(request.getParameter("id"));
                Map<String, Object> specialization = specializationDAO.getSpecializationById(id);
                
                if (specialization != null) {
                    request.setAttribute("specialization", specialization);
                    
                    
                    request.getRequestDispatcher("/admin/specialization-detail.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/specializations?error=not_found");
                }
                
            } else if (path.equals("/admin/specializations/delete")) {
                // Handle delete in doPost
                response.sendRedirect(request.getContextPath() + "/admin/specializations");
                
            } else if (path.equals("/admin/specializations/status")) {
                // Handle status change in doPost
                response.sendRedirect(request.getContextPath() + "/admin/specializations");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error processing request: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();
        
        try {
            // Check if user is logged in and is admin
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("account");
            
            if (account == null || !account.isAdmin()) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }
            
            // Get user ID from session
            int userId = account.getAccountId();
            
            SpecializationDAO specializationDAO = new SpecializationDAO();
            
            if (path.equals("/admin/specializations/add")) {
                // Add new specialization
                String name = request.getParameter("name");
                String image = request.getParameter("image");
                String status = request.getParameter("status");
                
                // Validate inputs
                if (name == null || name.trim().isEmpty()) {
                    request.setAttribute("errorMessage", "Tên chuyên khoa không được để trống");
                    request.getRequestDispatcher("/admin/specialization-form.jsp").forward(request, response);
                    return;
                }
                
                // Set default values if needed
                if (status == null || status.trim().isEmpty()) {
                    status = "Active";
                }
                
                // Add specialization
                int newId = specializationDAO.addSpecialization(name, "", image, status, userId);
                response.sendRedirect(request.getContextPath() + "/admin/specializations?success=added&id=" + newId);
                
            } else if (path.equals("/admin/specializations/edit")) {
                // Update existing specialization
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                String image = request.getParameter("image");
                String status = request.getParameter("status");
                
                // Validate inputs
                if (name == null || name.trim().isEmpty()) {
                    request.setAttribute("errorMessage", "Tên chuyên khoa không được để trống");
                    request.setAttribute("specialization", specializationDAO.getSpecializationById(id));
                    request.getRequestDispatcher("/admin/specialization-form.jsp").forward(request, response);
                    return;
                }
                
                // Update specialization
                boolean updated = specializationDAO.updateSpecialization(id, name, "", image, status, userId);
                
                if (updated) {
                    response.sendRedirect(request.getContextPath() + "/admin/specializations?success=updated&id=" + id);
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/specializations?error=update_failed&id=" + id);
                }
                
            } else if (path.equals("/admin/specializations/delete")) {
                // Delete specialization
                int id = Integer.parseInt(request.getParameter("id"));
                
                boolean deleted = specializationDAO.deleteSpecialization(id);
                
                if (deleted) {
                    response.sendRedirect(request.getContextPath() + "/admin/specializations?success=deleted");
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/specializations?error=delete_failed&message=Specialization+is+in+use");
                }
                
            } else if (path.equals("/admin/specializations/status")) {
                // Change specialization status
                int id = Integer.parseInt(request.getParameter("id"));
                String status = request.getParameter("status");
                
                boolean updated = specializationDAO.changeSpecializationStatus(id, status, userId);
                
                if (updated) {
                    response.sendRedirect(request.getContextPath() + "/admin/specializations?success=status_changed&id=" + id);
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/specializations?error=status_change_failed&id=" + id);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error processing request: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
} 