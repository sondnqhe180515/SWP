/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.User;

/**
 *
 * @author FPT SHOP
 */

public class ViewEmployeeList extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String roleFilter = request.getParameter("role"); // staff, doctor, or null

        UserDAO userDAO = new UserDAO();
        List<User> employees;

        if (roleFilter == null || roleFilter.equals("all")) {
            employees = userDAO.getAllEmployees(); // cả staff và doctor
        } else {
            int roleId = roleFilter.equals("staff") ? 2 : 3;
            employees = userDAO.getUsersByRole(roleId);
        }

        request.setAttribute("employees", employees);
        request.setAttribute("selectedRole", roleFilter);
        request.getRequestDispatcher("employee-list.jsp").forward(request, response);
    }
}
