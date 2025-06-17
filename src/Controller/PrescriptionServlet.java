package Controller;

import DAO.PrescriptionDAO;
import Model.Prescription;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.util.List;

public class PrescriptionServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String prescriptionID_raw = request.getParameter("prescriptionID");
        String recordID_raw = request.getParameter("recordID");
        String medicineName = request.getParameter("medicineName");
        String quantity_raw = request.getParameter("quantity");
        String instructions = request.getParameter("instructions");

        try {
            int prescriptionID = (prescriptionID_raw == null || prescriptionID_raw.isEmpty()) ? 0 : Integer.parseInt(prescriptionID_raw);
            int recordID = (recordID_raw == null || recordID_raw.isEmpty()) ? 0 : Integer.parseInt(recordID_raw);
            int quantity = (quantity_raw == null || quantity_raw.isEmpty()) ? 0 : Integer.parseInt(quantity_raw);

            Prescription p = new Prescription(prescriptionID, recordID, medicineName, quantity, instructions);

            PrescriptionDAO dao = new PrescriptionDAO();
            if (prescriptionID == 0) {
                dao.addPrescription(p);
            } else {
                dao.updatePrescription(p);
            }

            response.sendRedirect("prescriptionServlet");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        PrescriptionDAO dao = new PrescriptionDAO();

        switch (action) {
            case "edit":
                String id_raw = request.getParameter("id");
                try {
                    int id = Integer.parseInt(id_raw);
                    Prescription p = dao.getPrescriptionByID(id);
                    request.setAttribute("p", p);
                    request.getRequestDispatcher("prescriptionForm.jsp").forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "delete":
                String did_raw = request.getParameter("id");
                try {
                    int did = Integer.parseInt(did_raw);
                    dao.deletePrescription(did);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                response.sendRedirect("prescriptionServlet");
                break;

            case "list":
            default:
                List<Prescription> list = dao.getAllPrescriptions();
                request.setAttribute("list", list);
                request.getRequestDispatcher("prescriptionList.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Prescription CRUD Servlet";
    }
}
