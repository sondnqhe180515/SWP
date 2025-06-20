package controller;

import database.DBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Test servlet to verify database connectivity
 */
@WebServlet(name = "TestDBConnectionServlet", urlPatterns = {"/test-db"})
public class TestDBConnectionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Database Connection Test</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Database Connection Test</h1>");
            
            try {
                // Test database connection
                DBContext dbContext = new DBContext();
                Connection conn = dbContext.getConnection();
                
                if (conn != null && !conn.isClosed()) {
                    out.println("<p style='color:green'>Database connection successful!</p>");
                    
                    // Test a simple query
                    try (Statement stmt = conn.createStatement()) {
                        // Try to get specializations (used for services)
                        ResultSet rs = stmt.executeQuery("SELECT TOP 5 * FROM Specialization");
                        
                        out.println("<h2>Sample data from Specialization table:</h2>");
                        out.println("<table border='1'>");
                        out.println("<tr><th>ID</th><th>Name</th><th>Status</th></tr>");
                        
                        boolean hasData = false;
                        while (rs.next()) {
                            hasData = true;
                            out.println("<tr>");
                            out.println("<td>" + rs.getInt("Specialization_Id") + "</td>");
                            out.println("<td>" + rs.getString("Specialization_Name") + "</td>");
                            out.println("<td>" + rs.getString("Specialization_Status") + "</td>");
                            out.println("</tr>");
                        }
                        
                        out.println("</table>");
                        
                        if (!hasData) {
                            out.println("<p style='color:orange'>No data found in Specialization table. This is needed for services.</p>");
                        }
                        
                        // Try to get doctors
                        rs = stmt.executeQuery("SELECT TOP 5 d.Doctor_Id, a.Account_Fullname, s.Specialization_Name " +
                                              "FROM Doctor d " +
                                              "JOIN Account a ON d.Doctor_Id = a.Account_Id " +
                                              "JOIN Specialization s ON d.Specialization_Id = s.Specialization_Id");
                        
                        out.println("<h2>Sample data from Doctor table:</h2>");
                        out.println("<table border='1'>");
                        out.println("<tr><th>ID</th><th>Name</th><th>Specialization</th></tr>");
                        
                        hasData = false;
                        while (rs.next()) {
                            hasData = true;
                            out.println("<tr>");
                            out.println("<td>" + rs.getInt("Doctor_Id") + "</td>");
                            out.println("<td>" + rs.getString("Account_Fullname") + "</td>");
                            out.println("<td>" + rs.getString("Specialization_Name") + "</td>");
                            out.println("</tr>");
                        }
                        
                        out.println("</table>");
                        
                        if (!hasData) {
                            out.println("<p style='color:orange'>No data found in Doctor table. This is needed for doctor selection.</p>");
                        }
                    }
                } else {
                    out.println("<p style='color:red'>Failed to establish database connection!</p>");
                }
            } catch (SQLException e) {
                out.println("<p style='color:red'>Database error: " + e.getMessage() + "</p>");
                out.println("<pre>");
                e.printStackTrace(out);
                out.println("</pre>");
            } catch (Exception e) {
                out.println("<p style='color:red'>Unexpected error: " + e.getMessage() + "</p>");
                out.println("<pre>");
                e.printStackTrace(out);
                out.println("</pre>");
            }
            
            out.println("<p><a href='index.jsp'>Back to home</a></p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
