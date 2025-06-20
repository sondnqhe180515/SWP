package controller;

import dao.DoctorDAO;
import dao.ServiceDAO;
import dao.SpecializationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Service;

/**
 * Servlet that prepares data for the booking form
 */
@WebServlet(name = "BookingFormServlet", urlPatterns = {"/booking-form", "/get-doctors-by-specialization"})
public class BookingFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check if this is a request for doctors by specialization
        String pathInfo = request.getServletPath();
        if ("/get-doctors-by-specialization".equals(pathInfo)) {
            getDoctorsBySpecialization(request, response);
            return;
        }
        
        // Otherwise, load the booking form data
        try {
            System.out.println("BookingFormServlet: Starting to load data for booking form");
            
            // Load specializations for the dropdown instead of services
            SpecializationDAO specializationDAO = new SpecializationDAO();
            try {
                System.out.println("BookingFormServlet: Calling getSpecializationsForDropdown()");
                Map<Integer, String> specializations = specializationDAO.getSpecializationsForDropdown();
                
                if (specializations == null) {
                    System.out.println("BookingFormServlet: WARNING - specializations is null");
                } else if (specializations.isEmpty()) {
                    System.out.println("BookingFormServlet: WARNING - specializations is empty");
                } else {
                    System.out.println("BookingFormServlet: Successfully loaded " + specializations.size() + " specializations");
                    // Log each specialization for debugging
                    for (Map.Entry<Integer, String> entry : specializations.entrySet()) {
                        System.out.println("BookingFormServlet: Specialization ID: " + entry.getKey() + ", Name: " + entry.getValue());
                    }
                }
                
                request.setAttribute("specializations", specializations);
            } catch (SQLException e) {
                System.out.println("BookingFormServlet: Error loading specializations: " + e.getMessage());
                e.printStackTrace();
                // Continue with execution to try loading doctors
            }
            
            // Load doctors for the dropdown
            DoctorDAO doctorDAO = new DoctorDAO();
            try {
                List<Map<String, Object>> doctors = doctorDAO.getAllActiveDoctors();
                request.setAttribute("doctors", doctors);
                System.out.println("BookingFormServlet: Successfully loaded " + doctors.size() + " doctors");
            } catch (SQLException e) {
                System.out.println("BookingFormServlet: Error loading doctors: " + e.getMessage());
                e.printStackTrace();
                // Continue with execution to forward to booking.jsp anyway
            }
            
            // Forward to the booking form
            System.out.println("BookingFormServlet: Forwarding to booking.jsp");
            System.out.println("BookingFormServlet: Final check - specializations attribute is " + 
                              (request.getAttribute("specializations") == null ? "NULL" : "NOT NULL"));
            
            request.getRequestDispatcher("booking.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.out.println("BookingFormServlet: Unexpected error: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading form data: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    
    /**
     * Handle AJAX requests for doctors by specialization
     */
    private void getDoctorsBySpecialization(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            try {
                // Get the specialization ID from the request
                String specializationIdStr = request.getParameter("specializationId");
                if (specializationIdStr == null || specializationIdStr.isEmpty()) {
                    throw new IllegalArgumentException("Specialization ID is required");
                }
                
                int specializationId = Integer.parseInt(specializationIdStr);
                System.out.println("BookingFormServlet: Getting doctors for specialization ID: " + specializationId);
                
                // Get doctors for this specialization
                DoctorDAO doctorDAO = new DoctorDAO();
                List<Map<String, Object>> doctors = doctorDAO.getDoctorsBySpecialization(specializationId);
                
                // Manually create JSON response
                StringBuilder jsonBuilder = new StringBuilder("[");
                for (int i = 0; i < doctors.size(); i++) {
                    Map<String, Object> doctor = doctors.get(i);
                    if (i > 0) {
                        jsonBuilder.append(",");
                    }
                    
                    jsonBuilder.append("{");
                    jsonBuilder.append("\"doctorId\":").append(doctor.get("doctorId")).append(",");
                    jsonBuilder.append("\"fullName\":\"").append(escapeJson(String.valueOf(doctor.get("fullName")))).append("\",");
                    
                    // Handle avatar which might be null
                    Object avatar = doctor.get("avatar");
                    if (avatar != null) {
                        jsonBuilder.append("\"avatar\":\"").append(escapeJson(String.valueOf(avatar))).append("\",");
                    } else {
                        jsonBuilder.append("\"avatar\":null,");
                    }
                    
                    jsonBuilder.append("\"specializationId\":").append(doctor.get("specializationId")).append(",");
                    jsonBuilder.append("\"specialization\":\"").append(escapeJson(String.valueOf(doctor.get("specialization")))).append("\"");
                    jsonBuilder.append("}");
                }
                jsonBuilder.append("]");
                
                out.print(jsonBuilder.toString());
                
                System.out.println("BookingFormServlet: Sent " + doctors.size() + " doctors for specialization ID: " + specializationId);
                
            } catch (NumberFormatException e) {
                System.out.println("BookingFormServlet: Invalid specialization ID format: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\": \"Invalid specialization ID format\"}");
            } catch (SQLException e) {
                System.out.println("BookingFormServlet: Database error: " + e.getMessage());
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"error\": \"Database error: " + escapeJson(e.getMessage()) + "\"}");
            } catch (Exception e) {
                System.out.println("BookingFormServlet: Unexpected error: " + e.getMessage());
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"error\": \"Unexpected error: " + escapeJson(e.getMessage()) + "\"}");
            }
        }
    }
    
    /**
     * Escape JSON string values
     */
    private String escapeJson(String input) {
        if (input == null) {
            return "";
        }
        
        StringBuilder escaped = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            switch (c) {
                case '\"':
                    escaped.append("\\\"");
                    break;
                case '\\':
                    escaped.append("\\\\");
                    break;
                case '\b':
                    escaped.append("\\b");
                    break;
                case '\f':
                    escaped.append("\\f");
                    break;
                case '\n':
                    escaped.append("\\n");
                    break;
                case '\r':
                    escaped.append("\\r");
                    break;
                case '\t':
                    escaped.append("\\t");
                    break;
                default:
                    escaped.append(c);
            }
        }
        return escaped.toString();
    }
} 