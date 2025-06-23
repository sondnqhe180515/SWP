package controller;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;

public class AdminDashboard extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        UserDAO dao = new UserDAO();
        int totalUsers = dao.countAllUsers();
        int staffCount = dao.countUsersByRole(2);     // 2 = staff
        int customerCount = dao.countUsersByRole(1);  // 1 = customer
        int doctorCount = dao.countUsersByRole(3);

        request.setAttribute("totalUsers", totalUsers);
        request.setAttribute("staffCount", staffCount);
        request.setAttribute("customerCount", customerCount);
        request.setAttribute("doctorCount", doctorCount);

        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
    }
}
