package controller;

import dao.MedicalRecordDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/reexamNote") 
public class UpdateReExamNoteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int recordId = Integer.parseInt(request.getParameter("recordId"));
            request.setAttribute("recordId", recordId);
            request.getRequestDispatcher("reexamNote.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid record ID");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int recordId = Integer.parseInt(request.getParameter("recordId"));
            String reExamNote = request.getParameter("reExamNote");

            MedicalRecordDAO dao = new MedicalRecordDAO();
            dao.updateReExamNote(recordId, reExamNote);

            // Sau khi lưu xong, quay về trang danh sách
            response.sendRedirect("viewMedicalRecords");

        } catch (Exception e) {
            request.setAttribute("error", "Đã xảy ra lỗi khi cập nhật: " + e.getMessage());
            request.getRequestDispatcher("reexamNote.jsp").forward(request, response);
        }
    }
}
