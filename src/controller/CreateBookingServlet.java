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

    private boolean hasThreeSameChars(String note) {
        for (int i = 0; i < note.length() - 2; i++) {
            if (note.charAt(i) == note.charAt(i + 1) && note.charAt(i) == note.charAt(i + 2)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int customerId = Integer.parseInt(request.getParameter("customerId"));
            int doctorId = Integer.parseInt(request.getParameter("doctorId"));
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            String note = request.getParameter("note") != null ? request.getParameter("note").trim() : "";
            String dateStr = request.getParameter("appointmentDate");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date appointmentDate = sdf.parse(dateStr);
            Date now = new Date();

            // Giữ lại dữ liệu nếu có lỗi
            request.setAttribute("customerId", customerId);
            request.setAttribute("doctorId", doctorId);
            request.setAttribute("serviceId", serviceId);
            request.setAttribute("note", note);

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
                    request.getRequestDispatcher("createBooking.jsp").forward(request, response);
                    return;
                }
            }

            AppointmentDAO dao = new AppointmentDAO();
            List<Appointment> sameTimeAppointments = dao.getAppointmentsByDoctorAndTime(doctorId, appointmentDate);
            boolean hasOngoing = sameTimeAppointments.stream()
                    .anyMatch(a -> a.getStatus().equalsIgnoreCase("Đang xử lý"));

//            boolean hasOngoing = false;
//            for (Appointment a : sameTimeAppointments) {
//                if (a.getStatus().equalsIgnoreCase("Đang xử lý")) {
//                    hasOngoing = true;
//                    break;
//                }
//            }
            if (hasOngoing) {
                List<Appointment> overlaps = dao.getAppointmentsOfDoctorInDay(doctorId, appointmentDate);
                overlaps.sort(Comparator.comparing(Appointment::getAppointmentDate)); // sắp xếp tăng dần
                request.setAttribute("appointmentsOfDoctorInDay", overlaps);

                StringBuilder message = new StringBuilder("Các ca khám của bác sĩ trong ngày:\n");
                SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                for (Appointment ap : overlaps) {
                    message.append("- ").append(displayFormat.format(ap.getAppointmentDate())).append("\n");
                }

                request.setAttribute("error", message.toString());
                request.getRequestDispatcher("createBooking.jsp").forward(request, response);
                return;
            }

            // Tạo lịch hẹn
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
