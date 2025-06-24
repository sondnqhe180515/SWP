package controller;

import dao.AppointmentDAO;
import dao.UserDAO;
import dao.ServiceDAO;
import model.Appointment;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.net.URLEncoder;

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
        UserDAO userDao = new UserDAO();
        ServiceDAO serviceDao = new ServiceDAO();
        request.setAttribute("appointment", app);
        request.setAttribute("customers", userDao.getUsersByDefaultOrder("Khách hàng"));
        request.setAttribute("doctors", userDao.getUsersByDefaultOrder("Bác sĩ"));
        request.setAttribute("services", serviceDao.getAllServices());

        // Giữ lại các filter
        request.setAttribute("status", request.getParameter("status") != null ? request.getParameter("status") : "");
        request.setAttribute("date", request.getParameter("date") != null ? request.getParameter("date") : "");
        request.setAttribute("sortBy", request.getParameter("sortBy") != null ? request.getParameter("sortBy") : "");
        request.setAttribute("keyword", request.getParameter("keyword") != null ? request.getParameter("keyword") : "");
        request.setAttribute("page", request.getParameter("page") != null ? request.getParameter("page") : "1");

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

            if (!isValidAppointmentTime(appointmentDate)) {
                request.setAttribute("error", "Vui lòng chọn khung giờ hợp lệ: 07:00–11:30 hoặc 14:00–17:00");
                request.setAttribute("appointment", app);
                doGet(request, response);
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
                    request.setAttribute("appointment", app);
                    doGet(request, response);
                    return;
                }
            }

            new AppointmentDAO().updateAppointment(app);

            // Lấy filter từ request để redirect đúng trang danh sách với filter
            String statusFilter = request.getParameter("statusFilter") != null ? request.getParameter("statusFilter") : "";
            String dateFilter = request.getParameter("date") != null ? request.getParameter("date") : "";
            String sortBy = request.getParameter("sortBy") != null ? request.getParameter("sortBy") : "";
            String keyword = request.getParameter("keyword") != null ? request.getParameter("keyword") : "";
            String page = request.getParameter("page") != null ? request.getParameter("page") : "1";

            String redirectURL = "ViewBookingServlet"
                    + "?status=" + URLEncoder.encode(statusFilter, "UTF-8")
                    + "&date=" + URLEncoder.encode(dateFilter, "UTF-8")
                    + "&sortBy=" + URLEncoder.encode(sortBy, "UTF-8")
                    + "&keyword=" + URLEncoder.encode(keyword, "UTF-8")
                    + "&page=" + page;

            response.sendRedirect(redirectURL);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi cập nhật lịch khám: " + e.getMessage());
        }
    }
}
