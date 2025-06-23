/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AppointmentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import model.Appointments;

/**
 *
 * @author admin
 */
public class AddAppointmentController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("addAppointment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int customerID = Integer.parseInt(request.getParameter("customerID"));
            int doctorID = Integer.parseInt(request.getParameter("doctorID"));
            int serviceID = Integer.parseInt(request.getParameter("serviceID"));
            String dateStr = request.getParameter("appointmentDate");
            String note = request.getParameter("note");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date appointmentDate = sdf.parse(dateStr);

            Appointments ap = new Appointments();
            ap.setCustomerID(customerID);
            ap.setDoctorID(doctorID);
            ap.setServiceID(serviceID);
            ap.setAppointmentDate(appointmentDate);
            ap.setNote(note);
            ap.setStatus("Đang chờ");
            ap.setCreatedAt(new Date());

            AppointmentDAO dao = new AppointmentDAO();
            dao.insertAppointment(ap);

            response.sendRedirect("manageAppointment");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            request.getRequestDispatcher("addAppointment.jsp").forward(request, response);
        }
    }
}