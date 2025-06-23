
package controller;

import dao.AppointmentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.AbstractList;
import java.util.List;
import model.Appointments;

/**
 *
 * @author admin
 */
public class AppointmentController extends HttpServlet {

    AppointmentDAO dao = new AppointmentDAO();

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
        try {
            AppointmentDAO dao = new AppointmentDAO();
            List<Appointments> list = dao.getPendingAppointments();
            request.setAttribute("list", list);
            request.getRequestDispatcher("manageAppointment.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String idRaw = request.getParameter("appointmentID");

        if (idRaw != null && !idRaw.isEmpty()) {
            int id = Integer.parseInt(idRaw);
            AppointmentDAO dao = new AppointmentDAO();

            if ("confirm".equals(action)) {
                dao.updateStatus(id, "Xác nhận");
            } else if ("cancel".equals(action)) {
                dao.updateStatus(id, "Hủy");
            }
        }

    response.sendRedirect("manageAppointment");
    }
}
