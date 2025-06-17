package controller;

import dao.AppointmentDAO;
import dao.UserDAO;
import dao.ServiceDAO;
import model.Appointment;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class EditBookingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        AppointmentDAO appDao = new AppointmentDAO();
        Appointment app = appDao.getAppointmentById(id);
        UserDAO userDao = new UserDAO();
        ServiceDAO serviceDao = new ServiceDAO();

        request.setAttribute("appointment", app);
        request.setAttribute("customers", userDao.getUsersByDefaultOrder("Khách hàng"));
        request.setAttribute("doctors", userDao.getUsersByDefaultOrder("Bác sĩ"));
        request.setAttribute("services", serviceDao.getAllServices());

        request.getRequestDispatcher("editBooking.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int customerId = Integer.parseInt(request.getParameter("customerId"));
            int doctorId = Integer.parseInt(request.getParameter("doctorId"));
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            String note = request.getParameter("note");
            String status = request.getParameter("status");
            String dateStr = request.getParameter("appointmentDate");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date appointmentDate = sdf.parse(dateStr);

            Appointment app = new Appointment();
            app.setAppointmentId(id);
            app.setCustomerId(customerId);
            app.setDoctorId(doctorId);
            app.setServiceId(serviceId);
            app.setAppointmentDate(appointmentDate);
            app.setStatus(status);
            app.setNote(note);

            new AppointmentDAO().updateAppointment(app);
            response.sendRedirect("ViewBookingServlet");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi cập nhật lịch khám: " + e.getMessage());
        }
    }
}
