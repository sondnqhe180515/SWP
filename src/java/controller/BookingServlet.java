package controller;

import database.DBContext;
import dao.SlotDAO;
import dao.DoctorDAO;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Slot;

@WebServlet(name = "BookingServlet", urlPatterns = {"/BookingServlet"})
public class BookingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            // Get form data
        String bookingFor = request.getParameter("for");
        String fullname = request.getParameter("fullname");
        String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
        String date = request.getParameter("date"); // yyyy-MM-dd
        String time = request.getParameter("time"); // HH:mm
            String specializationId = request.getParameter("specialization"); // Changed from service to specialization
            String doctorIdStr = request.getParameter("doctorId");
            String note = request.getParameter("note");

            // Get connection from DBContext
            DBContext dbContext = new DBContext();
            Connection conn = dbContext.getConnection();
            
            // Begin transaction
            conn.setAutoCommit(false);
            
            try {
                // 1. Insert into Reservation table
                String reservationSql = "INSERT INTO Reservation (Reservation_Fullname, Reservation_Email, " +
                                       "Reservation_Phone, Reservation_Address, Reservation_Date, " +
                                       "Reservation_Status, Reservation_CreatedDate, Reservation_CreatedBy, " +
                                       "Reservation_Note) VALUES (?, ?, ?, ?, ?, ?, GETDATE(), ?, ?)";
                
                PreparedStatement reservationStmt = conn.prepareStatement(reservationSql, Statement.RETURN_GENERATED_KEYS);
                reservationStmt.setString(1, fullname);
                reservationStmt.setString(2, email != null && !email.isEmpty() ? email : null);
                reservationStmt.setString(3, phone);
                reservationStmt.setString(4, address != null && !address.isEmpty() ? address : null);
                
                // Parse date string to SQL Date
                Date sqlDate = Date.valueOf(date);
                reservationStmt.setDate(5, sqlDate);
                
                reservationStmt.setString(6, "Pending"); // Default status
                reservationStmt.setInt(7, 1); // Default user ID (should be replaced with actual user ID if logged in)
                
                // Combine specialization and note
                String combinedNote = "Chuyen khoa ID: " + specializationId;
                if (note != null && !note.isEmpty()) {
                    combinedNote += "\n" + note;
                }
                reservationStmt.setString(8, combinedNote);
                
                int rowsInserted = reservationStmt.executeUpdate();
                
                // Get the generated reservation ID
                int reservationId = 0;
                ResultSet generatedKeys = reservationStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    reservationId = generatedKeys.getInt(1);
                }
                
                // Find an available slot based on the requested time
                SlotDAO slotDAO = new SlotDAO();
                
                // Ensure time is in the correct format (HH:mm:ss)
                if (!time.contains(":")) {
                    time += ":00";
                } else if (time.split(":").length < 3) {
                    time += ":00";
                }
                
                Time requestedTime = Time.valueOf(time);
                Slot slot = slotDAO.findClosestSlot(requestedTime);
                
                int slotId = 1; // Default slot
                if (slot != null) {
                    slotId = slot.getSlotId();
                }
                
                // Determine doctor ID
                int doctorId = 0; // Default to no doctor
                if (doctorIdStr != null && !doctorIdStr.isEmpty()) {
                    doctorId = Integer.parseInt(doctorIdStr);
                } else {
                    // If no doctor specified, find one based on specialization
                    DoctorDAO doctorDAO = new DoctorDAO();
                    List<Map<String, Object>> doctors = doctorDAO.getDoctorsBySpecialization(Integer.parseInt(specializationId));
                    if (!doctors.isEmpty()) {
                        // Get the first available doctor
                        Map<String, Object> doctor = doctors.get(0);
                        doctorId = (int) doctor.get("doctorId");
                    }
                }
                
                // 2. Handle relationship if booking for a relative
                if ("relative".equals(bookingFor)) {
        String relativeName = request.getParameter("relativeName");
        String relativePhone = request.getParameter("relativePhone");
                    String relativeEmail = request.getParameter("relativeEmail");
        String relativeGender = request.getParameter("relativeGender");

                    // Convert gender from string to tinyint
                    byte genderValue = 0; // Default
                    if ("Nam".equals(relativeGender)) {
                        genderValue = 1;
                    } else if ("Nữ".equals(relativeGender)) {
                        genderValue = 2;
                    }
                    
                    // Insert into Relationship table
                    String relationshipSql = "INSERT INTO Relationship (Relationship_Fullname, Relationship_Phone, " +
                                           "Relationship_Email, Relationship_Gender, Relationship_CreatedBy) " +
                                           "VALUES (?, ?, ?, ?, ?)";
                    
                    PreparedStatement relationshipStmt = conn.prepareStatement(relationshipSql, Statement.RETURN_GENERATED_KEYS);
                    relationshipStmt.setString(1, relativeName);
                    relationshipStmt.setString(2, relativePhone);
                    relationshipStmt.setString(3, relativeEmail != null && !relativeEmail.isEmpty() ? relativeEmail : "");
                    relationshipStmt.setByte(4, genderValue);
                    relationshipStmt.setInt(5, 1); // Default user ID
                    
                    relationshipStmt.executeUpdate();
                    
                    // Get the generated relationship ID
                    int relationshipId = 0;
                    ResultSet relGeneratedKeys = relationshipStmt.getGeneratedKeys();
                    if (relGeneratedKeys.next()) {
                        relationshipId = relGeneratedKeys.getInt(1);
                    }
                    
                    // Insert into Reservation_Details with relationship
                    String detailsSql = "INSERT INTO Reservation_Details (Reservation_Id, Relationship_Id, " +
                                      "Doctor_Id, Slot_Id, Schedule_WorkDate) VALUES (?, ?, ?, ?, ?)";
                    
                    PreparedStatement detailsStmt = conn.prepareStatement(detailsSql);
                    detailsStmt.setInt(1, reservationId);
                    detailsStmt.setInt(2, relationshipId);
                    detailsStmt.setInt(3, doctorId);
                    detailsStmt.setInt(4, slotId);
                    detailsStmt.setDate(5, sqlDate);
                    
                    detailsStmt.executeUpdate();
                    
            } else {
                    // Booking for self
                    // Get current user ID if logged in
                    HttpSession session = request.getSession();
                    Integer accountId = (Integer) session.getAttribute("accountId");
                    if (accountId == null) {
                        accountId = 1; // Default account ID if not logged in
                    }
                    
                    // Insert into Reservation_Details for self
                    String detailsSql = "INSERT INTO Reservation_Details (Reservation_Id, Account_Id, " +
                                      "Doctor_Id, Slot_Id, Schedule_WorkDate) VALUES (?, ?, ?, ?, ?)";
                    
                    PreparedStatement detailsStmt = conn.prepareStatement(detailsSql);
                    detailsStmt.setInt(1, reservationId);
                    detailsStmt.setInt(2, accountId);
                    detailsStmt.setInt(3, doctorId);
                    detailsStmt.setInt(4, slotId);
                    detailsStmt.setDate(5, sqlDate);
                    
                    detailsStmt.executeUpdate();
                }
                
                // Update doctor schedule
                if (doctorId > 0) {
                    String scheduleSql = "INSERT INTO Doctor_schedule (Slot_Id, Doctor_Id, Schedule_WorkDate, Schedule_Status) " +
                                       "VALUES (?, ?, ?, 'Booked')";
                    
                    PreparedStatement scheduleStmt = conn.prepareStatement(scheduleSql);
                    scheduleStmt.setInt(1, slotId);
                    scheduleStmt.setInt(2, doctorId);
                    scheduleStmt.setDate(3, sqlDate);
                    
                    scheduleStmt.executeUpdate();
                }
                
                // Commit the transaction
                conn.commit();
                
                // Set success message
                request.getSession().setAttribute("successMessage", "Dat lich thanh cong! Ma dat lich cua ban la: " + reservationId);
                
                // Redirect to success page
                response.sendRedirect("success.jsp");
                
            } catch (SQLException e) {
                // Rollback in case of error
                conn.rollback();
                throw e;
            } finally {
                // Reset auto-commit to default
                conn.setAutoCommit(true);
                conn.close();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Loi khi dat lich: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
