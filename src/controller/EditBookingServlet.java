package controller;

import dao.AppointmentDAO;
import dao.ServiceDAO;
import dao.UserDAO;
import model.Appointment;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EditBookingServlet extends HttpServlet {

    private boolean isValidAppointmentTime(Date appointmentDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(appointmentDate);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int totalMinutes = hour * 60 + minute;
        return (totalMinutes >= 420 && totalMinutes <= 690) || (totalMinutes >= 840 && totalMinutes <= 1020);
    }

    private boolean hasThreeSameChars(String note) {
        for (int i = 0; i < note.length() - 2; i++) {
            if (note.charAt(i) == note.charAt(i + 1) && note.charAt(i) == note.charAt(i + 2)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        AppointmentDAO appDao = new AppointmentDAO();
        Appointment app = appDao.getAppointmentById(id);
        request.setAttribute("appointment", app);
        request.setAttribute("customers", new UserDAO().getUsersByDefaultOrder("Khách hàng"));
        request.setAttribute("doctors", new UserDAO().getUsersByDefaultOrder("Bác sĩ"));
        request.setAttribute("services", new ServiceDAO().getAllServices());
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
            String status = request.getParameter("status");
            String note = request.getParameter("note") != null ? request.getParameter("note").trim() : "";
            String dateStr = request.getParameter("appointmentDate");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date appointmentDate = sdf.parse(dateStr);
            Date now = new Date();

            Appointment app = new Appointment();
            app.setAppointmentId(id);
            app.setCustomerId(customerId);
            app.setDoctorId(doctorId);
            app.setServiceId(serviceId);
            app.setAppointmentDate(appointmentDate);
            app.setStatus(status);
            app.setNote(note);

            request.setAttribute("appointment", app);
            request.setAttribute("customers", new UserDAO().getUsersByDefaultOrder("Khách hàng"));
            request.setAttribute("doctors", new UserDAO().getUsersByDefaultOrder("Bác sĩ"));
            request.setAttribute("services", new ServiceDAO().getAllServices());

            if (appointmentDate.before(now)) {
                request.setAttribute("error", "Không thể chọn thời điểm trong quá khứ.");
                request.getRequestDispatcher("editBooking.jsp").forward(request, response);
                return;
            }

            if (!isValidAppointmentTime(appointmentDate)) {
                request.setAttribute("error", "Vui lòng chọn khung giờ hợp lệ: 07:00–11:30 hoặc 14:00–17:00");
                request.getRequestDispatcher("editBooking.jsp").forward(request, response);
                return;
            }

            if (!note.isEmpty()) {
                if (note.length() > 255) {
                    request.setAttribute("error", "Ghi chú không được vượt quá 255 ký tự.");
                } else if (!Character.isLetterOrDigit(note.charAt(0))) {
                    request.setAttribute("error", "Ghi chú không được bắt đầu bằng ký tự đặc biệt.");
                } else if (note.matches(".*<[^>]+>.*")) {
                    request.setAttribute("error", "Không được nhập thẻ HTML vào ghi chú.");
                } else if (!Character.isLetter(note.charAt(0))) {
                    request.setAttribute("error", "Ghi chú phải bắt đầu bằng chữ cái.");
                } else if (hasThreeSameChars(note)) {
                    request.setAttribute("error", "Không được nhập 3 ký tự giống nhau liên tiếp trong ghi chú.");
                }
                if (request.getAttribute("error") != null) {
                    request.getRequestDispatcher("editBooking.jsp").forward(request, response);
                    return;
                }
            }

            AppointmentDAO dao = new AppointmentDAO();
            List<Appointment> sameTimeAppointments = dao.getAppointmentsByDoctorAndTime(doctorId, appointmentDate);
            boolean hasOngoing = sameTimeAppointments.stream()
                    .anyMatch(a -> a.getStatus().equalsIgnoreCase("Đang xử lý") && a.getAppointmentId() != id);

            if (hasOngoing) {
                List<Appointment> overlaps = dao.getAppointmentsOfDoctorInDay(doctorId, appointmentDate);
                StringBuilder message = new StringBuilder("Bác sĩ đã có lịch trùng trong ngày:\n");
                SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                for (Appointment ap : overlaps) {
                    if (ap.getAppointmentId() != id) {
                        message.append("- ").append(displayFormat.format(ap.getAppointmentDate())).append("\n");
                    }
                }
                request.setAttribute("error", message.toString());
                request.getRequestDispatcher("editBooking.jsp").forward(request, response);
                return;
            }

            dao.updateAppointment(app);
            response.sendRedirect("ViewBookingServlet");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi cập nhật lịch khám: " + e.getMessage());
        }
    }
}
