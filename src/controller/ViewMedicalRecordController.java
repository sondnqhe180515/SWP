package controller;

import dao.MedicalRecordDAO;
import model.MedicalRecords;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/viewMedicalRecords")
public class ViewMedicalRecordController extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchName = req.getParameter("searchName");
        MedicalRecordDAO dao = new MedicalRecordDAO();
        List<MedicalRecords> records = dao.getAllMedicalRecords(searchName);
        req.setAttribute("records", records);
        req.setAttribute("searchName", searchName); // để giữ lại giá trị ô tìm kiếm
        req.getRequestDispatcher("viewMedicalRecord.jsp").forward(req, resp);
    }
}
