package controller;

import dao.FeedbackDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/deleteFeedback")
public class DeleteFeedBackController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            FeedbackDAO dao = new FeedbackDAO();
            dao.deleteFeedback(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Quay lại trang hiển thị phản hồi
        response.sendRedirect("viewFeedback");
    }
}
