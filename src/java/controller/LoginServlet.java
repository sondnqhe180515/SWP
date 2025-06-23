package controller;

import dao.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import utils.AuthUtil;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check if user is already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            String redirectPath = AuthUtil.getRedirectPathForRole(user.getRole().getRoleName());
            response.sendRedirect(request.getContextPath() + "/" + redirectPath);
            return;
        }
        
        // Check for remember me cookies
        String rememberedEmail = "";
        String rememberedPassword = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("rememberedEmail".equals(cookie.getName())) {
                    rememberedEmail = cookie.getValue();
                } else if ("rememberedPassword".equals(cookie.getName())) {
                    rememberedPassword = cookie.getValue();
                }
            }
        }
        
        request.setAttribute("rememberedEmail", rememberedEmail);
        request.setAttribute("rememberedPassword", rememberedPassword);
        
        // Display login page
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");
        
        // Validate inputs
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("errorMessage", "Email and password are required");
            request.setAttribute("rememberedEmail", email);
            request.setAttribute("rememberedPassword", password);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        
        // Authenticate user
        UserDAO userDAO = new UserDAO();
        User user = userDAO.login(email, password);
        
        if (user == null) {
            request.setAttribute("errorMessage", "Invalid email or password");
            request.setAttribute("rememberedEmail", email);
            request.setAttribute("rememberedPassword", password);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        
        // Check if account is active
        if (!user.isStatus()) {
            request.setAttribute("errorMessage", "Your account has been suspended. Please contact administrator.");
            request.setAttribute("rememberedEmail", email);
            request.setAttribute("rememberedPassword", password);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        
        // Handle remember me functionality
        if ("on".equals(rememberMe)) {
            Cookie emailCookie = new Cookie("rememberedEmail", email);
            emailCookie.setMaxAge(30 * 24 * 60 * 60); // 30 days
            emailCookie.setPath("/");
            
            Cookie passwordCookie = new Cookie("rememberedPassword", password);
            passwordCookie.setMaxAge(30 * 24 * 60 * 60); // 30 days
            passwordCookie.setPath("/");
            
            response.addCookie(emailCookie);
            response.addCookie(passwordCookie);
        } else {
            clearRememberMeCookies(response);
        }
        
        // Authentication successful, store user in session
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        
        // Redirect based on user role
        String roleName = user.getRole().getRoleName();
        String redirectPath = AuthUtil.getRedirectPathForRole(roleName);
        
        session.setAttribute("loginSuccess", "Welcome back, " + user.getFullName() + "!");
        response.sendRedirect(request.getContextPath() + "/" + redirectPath);
    }
    
    private void clearRememberMeCookies(HttpServletResponse response) {
        Cookie emailCookie = new Cookie("rememberedEmail", "");
        emailCookie.setMaxAge(0);
        emailCookie.setPath("/");
        
        Cookie passwordCookie = new Cookie("rememberedPassword", "");
        passwordCookie.setMaxAge(0);
        passwordCookie.setPath("/");
        
        response.addCookie(emailCookie);
        response.addCookie(passwordCookie);
    }
}
