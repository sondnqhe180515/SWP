/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.MedicalRecordDAO;
import Model.MedicalRecord;
import Model.MedicalRecordDetail;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author An
 */
@WebServlet(name = "CreateMedicalRecordServlet", urlPatterns = {"/CreateMedicalRecordServlet"})
public class CreateMedicalRecordServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CreateMedicalRecordServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateMedicalRecordServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conn = new dal.DBContext().getConnection()) {
            MedicalRecordDAO dao = new MedicalRecordDAO(conn);

            // Lấy dữ liệu từ form
            int userId = Integer.parseInt(request.getParameter("patientId"));
            int doctorId = getUserIdByName(conn, request.getParameter("doctorName"));
            Date visitDate = java.sql.Date.valueOf(request.getParameter("visitDate"));
            String diagnosis = request.getParameter("diagnosis");
            String instructions = request.getParameter("instructions");

            // Thuốc
            String[] medNames = request.getParameterValues("medName[]");
            String[] doses = request.getParameterValues("dose[]");
            String[] freqs = request.getParameterValues("freq[]");
            String[] daysArr = request.getParameterValues("days[]");
            String[] notes = request.getParameterValues("note[]");

            List<MedicalRecordDetail> details = new ArrayList<>();
            for (int i = 0; i < medNames.length; i++) {
                MedicalRecordDetail d = new MedicalRecordDetail();
                d.setMedName(medNames[i]);
                d.setDose(doses[i]);
                d.setFrequency(freqs[i]);
                d.setDays(Integer.parseInt(daysArr[i]));
                d.setNote(notes[i]);
                details.add(d);
            }

            // Lưu DB
            MedicalRecord record = new MedicalRecord();
            record.setUserId(userId);
            record.setDoctorId(doctorId);
            record.setVisitDate(visitDate);
            record.setDiagnosis(diagnosis);
            record.setInstructions(instructions);
            record.setDetails(details);

            int recordId = dao.insertMedicalRecord(record);
            dao.insertMedicalRecordDetails(recordId, details);

            
            response.sendRedirect("prescription.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("prescription.jsp");
        }
    }

    private int getUserIdByName(Connection conn, String name) throws Exception {
        String sql = "SELECT user_id FROM Users WHERE fullName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("user_id");
                }
            }
        }
        throw new Exception("User not found: " + name);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
