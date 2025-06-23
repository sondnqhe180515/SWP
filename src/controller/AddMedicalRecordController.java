package controller;

import dao.MedicalRecordDAO;
import model.MedicalRecords;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/addMedicalRecord")
public class AddMedicalRecordController extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            int appointmentID = Integer.parseInt(req.getParameter("appointmentID"));
            String diagnosis = req.getParameter("diagnosis");
            String treatment = req.getParameter("treatment");
            String description = req.getParameter("description");
            String reExamDateStr = req.getParameter("reExamDate");

            MedicalRecords rec = new MedicalRecords();
            rec.setAppointmentID(appointmentID);
            rec.setDiagnosis(diagnosis);
            rec.setTreatment(treatment);
            rec.setDescription(description);
            if (reExamDateStr != null && !reExamDateStr.isEmpty()) {
                rec.setReExamDate(Date.valueOf(reExamDateStr));
            }
            new MedicalRecordDAO().insertMedicalRecord(rec);

            res.sendRedirect("success.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            res.sendRedirect("error.jsp");
        }
    }
}
