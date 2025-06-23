package controller;

import dao.FeedbackDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Feedbacks;
import model.User;

@WebServlet("/viewFeedback")
public class ViewFeedbackController extends HttpServlet {    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null || user.getRole() == null || !"Bác sĩ".equals(user.getRole().getRoleName())) {
            response.sendRedirect("login.jsp");
            return;
        }

        int doctorId = user.getUserID();

        FeedbackDAO dao = new FeedbackDAO();
        List<Feedbacks> feedbackList = dao.getFeedbacksForDoctor(doctorId);

        request.setAttribute("feedbackList", feedbackList);
        request.getRequestDispatcher("viewFeedback.jsp").forward(request, response);
    }
}
