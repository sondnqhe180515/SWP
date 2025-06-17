package dao;

import dal.DBContext;
import model.Service;
import java.sql.*;
import java.util.*;

public class ServiceDAO extends DBContext {

    public List<Service> getAllServices() {
        List<Service> list = new ArrayList<>();
        String sql = "SELECT ServiceID, ServiceName, Description, Price, Status FROM Services WHERE Status = 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Service s = new Service();
                s.setServiceId(rs.getInt("ServiceID"));
                s.setServiceName(rs.getString("ServiceName"));
                s.setDescription(rs.getString("Description"));
                s.setPrice(rs.getBigDecimal("Price"));
                s.setStatus(rs.getBoolean("Status"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Service getServiceById(int id) {
        String sql = "SELECT * FROM Services WHERE ServiceID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Service(
                        rs.getInt("ServiceID"),
                        rs.getString("ServiceName"),
                        rs.getString("Description"),
                        rs.getBigDecimal("Price"),
                        rs.getBoolean("Status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertService(Service s) {
        String sql = "INSERT INTO Services (ServiceName, Description, Price, Status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, s.getServiceName());
            ps.setString(2, s.getDescription());
            ps.setBigDecimal(3, s.getPrice());
            ps.setBoolean(4, s.isStatus());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
