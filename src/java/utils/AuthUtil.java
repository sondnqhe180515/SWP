package utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.User;

public class AuthUtil {
    
    /**
     * Check if the current user has the required role
     * @param request HttpServletRequest
     * @param requiredRole Role name required for access
     * @return true if authorized, false otherwise
     */
    public static boolean isAuthorized(HttpServletRequest request, String requiredRole) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return false;
        }
        
        return user.getRole().getRoleName().equals(requiredRole);
    }
    
    /**
     * Get the current logged-in user
     * @param request HttpServletRequest
     * @return User object or null if not logged in
     */
    public static User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        
        return (User) session.getAttribute("user");
    }
    
    /**
     * Check if user is logged in
     * @param request HttpServletRequest
     * @return true if logged in, false otherwise
     */
    public static boolean isLoggedIn(HttpServletRequest request) {
        return getCurrentUser(request) != null;
    }
    
    /**
     * Returns the appropriate redirect path based on user role
     * @param roleName Name of the user role
     * @return Path to redirect the user to
     */
    public static String getRedirectPathForRole(String roleName) {
        if (roleName == null) {
            return "login";
        }
        
        switch(roleName.trim()) {
            case "Quản trị viên":
                return "admin/admin.jsp";
            case "Bác sĩ":
                return "doctor/dashboard.jsp";
            case "Nhân viên":
                return "staff/dashboard.jsp";
            case "Khách hàng":
                return "customer/dashboard.jsp";
            default:
                return "login";
        }
    }
    
    
}
