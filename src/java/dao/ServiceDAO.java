package dao;

import database.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Service;

/**
 * Data Access Object for Service entity
 */
public class ServiceDAO extends DBContext {
    
    /**
     * Get all services
     * @return List of Service objects
     * @throws SQLException If a database error occurs
     */
    public List<Service> getAllServices() throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT s.Service_Id, s.Service_Title, s.Service_Detail, s.Service_Price, " +
                     "s.Specialization_Id, sp.Specialization_Name " +
                     "FROM Service s " +
                     "JOIN Specialization sp ON s.Specialization_Id = sp.Specialization_Id " +
                     "WHERE s.Service_Status = 'Active'";
        
        try {
            System.out.println("ServiceDAO: Executing getAllServices query");
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Service service = new Service();
                service.setId(rs.getInt("Service_Id"));
                service.setName(rs.getString("Service_Title"));
                service.setDescription(rs.getString("Service_Detail"));
                service.setPrice(rs.getDouble("Service_Price"));
                // Store specialization info in the description
                String description = service.getDescription();
                if (description == null) {
                    description = "";
                }
                description += " (Specialization: " + rs.getString("Specialization_Name") + ")";
                service.setDescription(description);
                service.setStatus("Active");
                services.add(service);
            }
            
            System.out.println("ServiceDAO: Found " + services.size() + " services");
            return services;
        } catch (SQLException e) {
            System.out.println("ServiceDAO: Error in getAllServices: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Get a service by its ID
     * @param serviceId The ID of the service to retrieve
     * @return Service object or null if not found
     * @throws SQLException If a database error occurs
     */
    public Service getServiceById(int serviceId) throws SQLException {
        String sql = "SELECT s.Service_Id, s.Service_Title, s.Service_Detail, s.Service_Price, " +
                     "s.Specialization_Id, sp.Specialization_Name " +
                     "FROM Service s " +
                     "JOIN Specialization sp ON s.Specialization_Id = sp.Specialization_Id " +
                     "WHERE s.Service_Id = ? AND s.Service_Status = 'Active'";
        
        try {
            System.out.println("ServiceDAO: Getting service with ID: " + serviceId);
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, serviceId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Service service = new Service();
                service.setId(rs.getInt("Service_Id"));
                service.setName(rs.getString("Service_Title"));
                service.setDescription(rs.getString("Service_Detail"));
                service.setPrice(rs.getDouble("Service_Price"));
                // Store specialization info in the description
                String description = service.getDescription();
                if (description == null) {
                    description = "";
                }
                description += " (Specialization: " + rs.getString("Specialization_Name") + ")";
                service.setDescription(description);
                service.setStatus("Active");
                System.out.println("ServiceDAO: Found service: " + service.getName());
                return service;
            }
            
            System.out.println("ServiceDAO: No service found with ID: " + serviceId);
            return null;
        } catch (SQLException e) {
            System.out.println("ServiceDAO: Error in getServiceById: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Get services by specialization ID
     * @param specializationId The specialization ID
     * @return List of Service objects
     * @throws SQLException If a database error occurs
     */
    public List<Service> getServicesBySpecialization(int specializationId) throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT s.Service_Id, s.Service_Title, s.Service_Detail, s.Service_Price, " +
                     "s.Specialization_Id, sp.Specialization_Name " +
                     "FROM Service s " +
                     "JOIN Specialization sp ON s.Specialization_Id = sp.Specialization_Id " +
                     "WHERE s.Specialization_Id = ? AND s.Service_Status = 'Active'";
        
        try {
            System.out.println("ServiceDAO: Getting services for specialization ID: " + specializationId);
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, specializationId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Service service = new Service();
                service.setId(rs.getInt("Service_Id"));
                service.setName(rs.getString("Service_Title"));
                service.setDescription(rs.getString("Service_Detail"));
                service.setPrice(rs.getDouble("Service_Price"));
                // Store specialization info in the description
                String description = service.getDescription();
                if (description == null) {
                    description = "";
                }
                description += " (Specialization: " + rs.getString("Specialization_Name") + ")";
                service.setDescription(description);
                service.setStatus("Active");
                services.add(service);
            }
            
            System.out.println("ServiceDAO: Found " + services.size() + " services for specialization ID: " + specializationId);
            return services;
        } catch (SQLException e) {
            System.out.println("ServiceDAO: Error in getServicesBySpecialization: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Get services for dropdown menu
     * @return Map of service IDs to service titles
     * @throws SQLException If a database error occurs
     */
    public Map<Integer, String> getServicesForDropdown() throws SQLException {
        Map<Integer, String> services = new HashMap<>();
        String sql = "SELECT Service_Id, Service_Title FROM Service WHERE Service_Status = 'Active'";
        
        try {
            System.out.println("ServiceDAO: Getting services for dropdown");
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("Service_Id");
                String title = rs.getString("Service_Title");
                services.put(id, title);
            }
            
            System.out.println("ServiceDAO: Found " + services.size() + " services for dropdown");
            
            // If no services found, check if Service table exists
            if (services.isEmpty()) {
                System.out.println("ServiceDAO: No services found, checking if Service table exists");
                try {
                    String checkTableSql = "SELECT TOP 1 * FROM Service";
                    PreparedStatement checkStmt = conn.prepareStatement(checkTableSql);
                    ResultSet checkRs = checkStmt.executeQuery();
                    
                    if (checkRs.next()) {
                        System.out.println("ServiceDAO: Service table exists but no active services found");
                    } else {
                        System.out.println("ServiceDAO: Service table exists but is empty");
                    }
                } catch (SQLException e) {
                    System.out.println("ServiceDAO: Error checking Service table: " + e.getMessage());
                }
                
                // Check if Specialization table exists
                try {
                    String checkSpecSql = "SELECT TOP 5 * FROM Specialization";
                    PreparedStatement checkSpecStmt = conn.prepareStatement(checkSpecSql);
                    ResultSet checkSpecRs = checkSpecStmt.executeQuery();
                    
                    System.out.println("ServiceDAO: Checking Specialization table:");
                    boolean hasData = false;
                    while (checkSpecRs.next()) {
                        hasData = true;
                        System.out.println("  - ID: " + checkSpecRs.getInt("Specialization_Id") + 
                                         ", Name: " + checkSpecRs.getString("Specialization_Name") + 
                                         ", Status: " + checkSpecRs.getString("Specialization_Status"));
                    }
                    
                    if (!hasData) {
                        System.out.println("ServiceDAO: Specialization table exists but is empty");
                    }
                } catch (SQLException e) {
                    System.out.println("ServiceDAO: Error checking Specialization table: " + e.getMessage());
                }
            }
            
            return services;
        } catch (SQLException e) {
            System.out.println("ServiceDAO: Error in getServicesForDropdown: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
} 