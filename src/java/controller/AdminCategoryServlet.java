package controller;

import dao.CategoryBlogDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.CategoryBlog;

/**
 * Servlet for managing blog categories in the admin panel
 */
@WebServlet(name = "AdminCategoryServlet", urlPatterns = {
    "/admin/categories", 
    "/admin/categories/add", 
    "/admin/categories/edit",
    "/admin/categories/delete", 
    "/admin/categories/status"
})
public class AdminCategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("AdminCategoryServlet: doGet called");
        String path = request.getServletPath();
        System.out.println("AdminCategoryServlet: Path = " + path);
        
        try {
            // Check if user is logged in and is admin
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("account");
            
            System.out.println("AdminCategoryServlet: Account = " + (account != null ? account.getUsername() : "null"));
            
            if (account == null || !account.isAdmin()) {
                System.out.println("AdminCategoryServlet: User not logged in or not admin, redirecting to login");
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }
            
            CategoryBlogDAO categoryDAO = new CategoryBlogDAO();
            
            if (path.equals("/admin/categories")) {
                System.out.println("AdminCategoryServlet: Listing all categories");
                // List all categories
                List<CategoryBlog> categories = categoryDAO.getAllCategoriesWithStatus();
                System.out.println("AdminCategoryServlet: Categories fetched, count = " + (categories != null ? categories.size() : "null"));
                
                request.setAttribute("categories", categories);
                request.setAttribute("totalCategories", categoryDAO.countCategories());
                request.setAttribute("activeCategories", categoryDAO.countActiveCategories());
                request.setAttribute("inactiveCategories", categoryDAO.countCategories() - categoryDAO.countActiveCategories());
                
                System.out.println("AdminCategoryServlet: Forwarding to categories.jsp");
                request.getRequestDispatcher("/admin/categories.jsp").forward(request, response);
                
            } else if (path.equals("/admin/categories/add")) {
                // Show add form (handled via modal in categories.jsp)
                response.sendRedirect(request.getContextPath() + "/admin/categories");
                
            } else if (path.equals("/admin/categories/edit")) {
                // Get category for editing (handled via AJAX)
                int id = Integer.parseInt(request.getParameter("id"));
                CategoryBlog category = categoryDAO.getCategoryById(id);
                
                if (category != null) {
                    request.setAttribute("category", category);
                    request.getRequestDispatcher("/admin/category-form.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/categories?error=not_found");
                }
                
            } else if (path.equals("/admin/categories/delete")) {
                // Handle delete in doPost
                response.sendRedirect(request.getContextPath() + "/admin/categories");
                
            } else if (path.equals("/admin/categories/status")) {
                // Handle status change in doPost
                response.sendRedirect(request.getContextPath() + "/admin/categories");
            }
            
        } catch (Exception e) {
            System.out.println("AdminCategoryServlet: Error in doGet - " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error processing request: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("AdminCategoryServlet: doPost called");
        request.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();
        System.out.println("AdminCategoryServlet: Path = " + path);
        
        try {
            // Check if user is logged in and is admin
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("account");
            
            if (account == null || !account.isAdmin()) {
                System.out.println("AdminCategoryServlet: User not logged in or not admin, redirecting to login");
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }
            
            CategoryBlogDAO categoryDAO = new CategoryBlogDAO();
            
            if (path.equals("/admin/categories/add")) {
                System.out.println("AdminCategoryServlet: Adding new category");
                // Add new category
                String name = request.getParameter("name");
                String status = request.getParameter("status");
                
                // Validate inputs
                if (name == null || name.trim().isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/admin/categories?error=name_required");
                    return;
                }
                
                // Set default values if needed
                if (status == null || status.trim().isEmpty()) {
                    status = "Active";
                }
                
                // Đảm bảo status chỉ là 'Active' hoặc 'Deactive' theo ràng buộc CHECK constraint
                if (!"Active".equals(status)) {
                    status = "Deactive";
                }
                
                // Add category
                int newId = categoryDAO.addCategory(name, status);
                
                if (newId > 0) {
                    response.sendRedirect(request.getContextPath() + "/admin/categories?success=added");
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/categories?error=add_failed");
                }
                
            } else if (path.equals("/admin/categories/edit")) {
                System.out.println("AdminCategoryServlet: Updating category");
                // Update existing category
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                String status = request.getParameter("status");
                
                // Validate inputs
                if (name == null || name.trim().isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/admin/categories?error=name_required");
                    return;
                }
                
                // Update category
                boolean updated = categoryDAO.updateCategory(id, name, status);
                
                if (updated) {
                    response.sendRedirect(request.getContextPath() + "/admin/categories?success=updated");
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/categories?error=update_failed");
                }
                
            } else if (path.equals("/admin/categories/delete")) {
                System.out.println("AdminCategoryServlet: Deleting category");
                // Delete category
                int id = Integer.parseInt(request.getParameter("id"));
                
                boolean deleted = categoryDAO.deleteCategory(id);
                
                if (deleted) {
                    response.sendRedirect(request.getContextPath() + "/admin/categories?success=deleted");
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/categories?error=delete_failed&message=Category+is+in+use");
                }
                
            } else if (path.equals("/admin/categories/status")) {
                System.out.println("AdminCategoryServlet: Changing category status");
                // Change category status
                int id = Integer.parseInt(request.getParameter("id"));
                String status = request.getParameter("status");
                
                boolean updated = categoryDAO.changeCategoryStatus(id, status);
                
                if (updated) {
                    response.sendRedirect(request.getContextPath() + "/admin/categories?success=status_changed");
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/categories?error=status_change_failed");
                }
            }
            
        } catch (Exception e) {
            System.out.println("AdminCategoryServlet: Error in doPost - " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error processing request: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
} 