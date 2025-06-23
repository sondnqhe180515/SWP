/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Appointments;

/**
 *
 * @author admin
 */
public class AppointmentDAO extends DBContext {

    public List<Appointments> getPendingAppointments() {
        List<Appointments> list = new ArrayList<>();
        String sql = "select * from Appointments";
        try (
                PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Appointments ap = new Appointments();
                ap.setAppointmentID(rs.getInt("AppointmentID"));
                ap.setCustomerID(rs.getInt("CustomerID"));
                ap.setDoctorID(rs.getInt("DoctorID"));
                ap.setServiceID(rs.getInt("ServiceID"));
                ap.setAppointmentDate(rs.getTimestamp("AppointmentDate"));
                ap.setStatus(rs.getString("Status"));
                ap.setNote(rs.getString("Note"));
                ap.setCreatedAt(rs.getTimestamp("CreatedAt"));

                list.add(ap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    public void updateStatus(int appointmentID, String status) {
        String sql = "update Appointments set Status = ? "
                + "where AppointmentID = ?";
        try (
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, appointmentID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

      public void insertAppointment(Appointments ap) throws Exception {
        String sql = "INSERT INTO Appointments (customerID, doctorID, serviceID, appointmentDate, status, note, createdAt) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ap.getCustomerID());
            ps.setInt(2, ap.getDoctorID());
            ps.setInt(3, ap.getServiceID());
            ps.setDate(4, new java.sql.Date(ap.getAppointmentDate().getTime()));
            ps.setString(5, ap.getStatus());
            ps.setString(6, ap.getNote());
            ps.setTimestamp(7, new java.sql.Timestamp(ap.getCreatedAt().getTime()));

            ps.executeUpdate();
        }
    }

    public List<Appointments> getAppointmentsByDoctorID(int doctorID) {
        List<Appointments> list = new ArrayList<>();
        String sql = "select * from Appointments where DoctorID = ? ORDER BY AppointmentDate DESC";
        try (
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointments ap = new Appointments();
                ap.setAppointmentID(rs.getInt("AppointmentID"));
                ap.setCustomerID(rs.getInt("CustomerID"));
                ap.setDoctorID(rs.getInt("DoctorID"));
                ap.setServiceID(rs.getInt("ServiceID"));
                ap.setAppointmentDate(rs.getTimestamp("AppointmentDate"));
                ap.setStatus(rs.getString("Status"));
                ap.setNote(rs.getString("Note"));
                ap.setCreatedAt(rs.getTimestamp("CreatedAt"));

                list.add(ap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
