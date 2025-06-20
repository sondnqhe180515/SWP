package controller;

import dao.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is already logged in
        HttpSession session = request.getSession();
        if (session.getAttribute("account") != null) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        // Forward to login page
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            // Validate input
            if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                request.setAttribute("errorMessage", "Vui lòng nhập tên đăng nhập và mật khẩu");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            
            // Attempt to login
            AccountDAO accountDAO = new AccountDAO();
            Account account = accountDAO.login(username, password);
            
            if (account == null) {
                request.setAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            
            // Check if account is active
            if (!"Active".equalsIgnoreCase(account.getStatus())) {
                request.setAttribute("errorMessage", "Tài khoản của bạn đã bị vô hiệu hóa");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            
            // Store account in session
            HttpSession session = request.getSession();
            session.setAttribute("account", account);
            
            // Redirect based on role
            if (account.isAdmin()) {
                response.sendRedirect("admin/dashboard");
            } else {
                response.sendRedirect("index.jsp");
            }
            
        } catch (Exception e) {
            System.out.println("Error in LoginServlet: " + e.getMessage());
            request.setAttribute("errorMessage", "Đã xảy ra lỗi trong quá trình đăng nhập");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
} 