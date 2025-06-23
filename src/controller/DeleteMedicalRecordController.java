package controller;

import dao.MedicalRecordDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/DeleteMedicalRecord")
public class DeleteMedicalRecordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String recordIdStr = request.getParameter("recordId");
        try {
            int recordId = Integer.parseInt(recordIdStr);
            MedicalRecordDAO dao = new MedicalRecordDAO();
            dao.deleteMedicalRecord(recordId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        response.sendRedirect("viewMedicalRecords");
    }
}
