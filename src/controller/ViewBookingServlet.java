package controller;

import dao.AppointmentDAO;
import model.Appointment;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class ViewBookingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int pageSize = 10; // hoặc số bạn muốn
        AppointmentDAO dao = new AppointmentDAO();

        List<Appointment> fullList = dao.getAllAppointments(); // giữ nguyên nghiệp vụ
        List<Appointment> paginatedList = dao.getAppointmentsByPage(page, pageSize, fullList);
        int totalAppointments = fullList.size();
        int totalPages = (int) Math.ceil((double) totalAppointments / pageSize);

        request.setAttribute("appointmentList", paginatedList);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        request.getRequestDispatcher("viewBooking.jsp").forward(request, response);
    }
}
