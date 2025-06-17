package controller;

import dao.AppointmentDAO;
import model.Appointment;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CreateBookingServlet extends HttpServlet {

    private boolean isValidAppointmentTime(Date appointmentDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(appointmentDate);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int totalMinutes = hour * 60 + minute;
        return (totalMinutes >= 420 && totalMinutes <= 690) || (totalMinutes >= 840 && totalMinutes <= 1020);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int customerId = Integer.parseInt(request.getParameter("customerId"));
            int doctorId = Integer.parseInt(request.getParameter("doctorId"));
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            String note = request.getParameter("note");
            String dateStr = request.getParameter("appointmentDate");

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date appointmentDate = inputFormat.parse(dateStr);
            Date now = new Date();

            if (appointmentDate.before(now)) {
                request.setAttribute("error", "Không thể đặt lịch trong quá khứ.");
                request.getRequestDispatcher("createBooking.jsp").forward(request, response);
                return;
            }

            if (!isValidAppointmentTime(appointmentDate)) {
                request.setAttribute("error", "Vui lòng chọn trong các khung giờ:\n07:00–11:30 hoặc từ 14:00–17:00");
                request.getRequestDispatcher("createBooking.jsp").forward(request, response);
                return;
            }

            AppointmentDAO dao = new AppointmentDAO();

            List<Appointment> sameTimeAppointments = dao.getAppointmentsByDoctorAndTime(doctorId, appointmentDate);
            boolean hasOngoing = sameTimeAppointments.stream()
                    .anyMatch(a -> a.getStatus().equalsIgnoreCase("Đang xử lý"));

            if (hasOngoing) {
                List<Appointment> overlaps = dao.getAppointmentsOfDoctorInDay(doctorId, appointmentDate);
                StringBuilder message = new StringBuilder("Các lịch đặt khám của bác sĩ trong ngày hôm nay, vui lòng chọn khung giờ khác (ít nhất 30 phút):\n");
                SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");                     
                for (Appointment ap : overlaps) {
                    message.append("- ").append(displayFormat.format(ap.getAppointmentDate())).append("\n");
                } 
                request.setAttribute("error", message.toString());
                request.getRequestDispatcher("createBooking.jsp").forward(request, response);
                return;
            }

            Appointment app = new Appointment();
            app.setCustomerId(customerId);
            app.setDoctorId(doctorId);
            app.setServiceId(serviceId);
            app.setAppointmentDate(appointmentDate);
            app.setStatus("Đang xử lý");
            app.setNote(note);
            dao.createAppointment(app);
            response.sendRedirect("successBooking.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("Lỗi khi đặt lịch: " + e.getMessage());
        }
    }
}
