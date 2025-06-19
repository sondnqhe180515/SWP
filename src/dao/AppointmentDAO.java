package dao;

import dal.DBContext;
import model.Appointment;
import java.sql.*;
import java.util.*;

public class AppointmentDAO extends DBContext {

    public void createAppointment(Appointment app) {
        String sql = "INSERT INTO Appointments (CustomerID, DoctorID, ServiceID, AppointmentDate, Status, Note) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, app.getCustomerId());
            ps.setInt(2, app.getDoctorId());
            ps.setInt(3, app.getServiceId());
            ps.setTimestamp(4, new Timestamp(app.getAppointmentDate().getTime()));
            ps.setString(5, app.getStatus());
            ps.setString(6, app.getNote());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Kiểm tra có lịch ĐANG XỬ LÝ nào trong khoảng thời gian ±30 phút
    public boolean isDoctorBusy(int doctorId, java.util.Date appointmentDate) {
        String sql = "SELECT COUNT(*) FROM Appointments "
                + "WHERE DoctorID = ? "
                + "AND CAST(AppointmentDate AS DATE) = CAST(? AS DATE) "
                + "AND ABS(DATEDIFF(MINUTE, AppointmentDate, ?)) < 30 "
                + "AND Status = N'Đang xử lý'";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            Timestamp ts = new Timestamp(appointmentDate.getTime());
            ps.setInt(1, doctorId);
            ps.setTimestamp(2, ts);
            ps.setTimestamp(3, ts);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //Trả về các lịch ĐANG XỬ LÝ của bác sĩ trong NGÀY
    public List<Appointment> getAppointmentsOfDoctorInDay(int doctorId, java.util.Date date) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM Appointments "
                + "WHERE DoctorID = ? "
                + "AND CAST(AppointmentDate AS DATE) = CAST(? AS DATE) "
                + "AND Status = N'Đang xử lý'";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ps.setTimestamp(2, new Timestamp(date.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment a = new Appointment();
                a.setAppointmentId(rs.getInt("AppointmentID"));
                a.setCustomerId(rs.getInt("CustomerID"));
                a.setDoctorId(rs.getInt("DoctorID"));
                a.setServiceId(rs.getInt("ServiceID"));
                a.setAppointmentDate(rs.getTimestamp("AppointmentDate"));
                a.setStatus(rs.getString("Status"));
                a.setNote(rs.getString("Note"));
                list.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy các lịch khám trùng ngày và giờ với bác sĩ
    public List<Appointment> getAppointmentsByDoctorAndTime(int doctorId, java.util.Date time) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM Appointments "
                + "WHERE DoctorID = ? "
                + "AND CAST(AppointmentDate AS DATE) = CAST(? AS DATE) "
                + "AND ABS(DATEDIFF(MINUTE, AppointmentDate, ?)) < 30"; // trùng thời gian
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            Timestamp ts = new Timestamp(time.getTime());
            ps.setInt(1, doctorId);
            ps.setTimestamp(2, ts);
            ps.setTimestamp(3, ts);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment a = new Appointment();
                a.setAppointmentId(rs.getInt("AppointmentID"));
                a.setCustomerId(rs.getInt("CustomerID"));
                a.setDoctorId(rs.getInt("DoctorID"));
                a.setServiceId(rs.getInt("ServiceID"));
                a.setAppointmentDate(rs.getTimestamp("AppointmentDate"));
                a.setStatus(rs.getString("Status"));
                a.setNote(rs.getString("Note"));
                list.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //Trả về true nếu ca trùng thời điểm là "Hoàn tất" hoặc không có
    public boolean isDoctorAvailable(int doctorId, java.util.Date appointmentDate) {
        String sql = "SELECT Status FROM Appointments "
                + "WHERE DoctorID = ? "
                + "AND CAST(AppointmentDate AS DATE) = CAST(? AS DATE) "
                + "AND ABS(DATEDIFF(MINUTE, AppointmentDate, ?)) < 30";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            Timestamp ts = new Timestamp(appointmentDate.getTime());
            ps.setInt(1, doctorId);
            ps.setTimestamp(2, ts);
            ps.setTimestamp(3, ts);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String status = rs.getString("Status");
                return status.equalsIgnoreCase("Hoàn tất");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM Appointments";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment a = new Appointment();
                a.setAppointmentId(rs.getInt("AppointmentID"));
                a.setCustomerId(rs.getInt("CustomerID"));
                a.setDoctorId(rs.getInt("DoctorID"));
                a.setServiceId(rs.getInt("ServiceID"));
                a.setAppointmentDate(rs.getTimestamp("AppointmentDate"));
                a.setStatus(rs.getString("Status"));
                a.setNote(rs.getString("Note"));
                list.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Appointment> getAppointmentsByPage(int page, int pageSize, List<Appointment> appointmentList) {
        List<Appointment> paginatedList = new ArrayList<>();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, appointmentList.size());
        if (start < appointmentList.size()) {
            for (int i = start; i < end; i++) {
                paginatedList.add(appointmentList.get(i));
            }
        }
        return paginatedList;
    }

    public Appointment getAppointmentById(int id) {
        String sql = "SELECT * FROM Appointments WHERE AppointmentID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Appointment a = new Appointment();
                a.setAppointmentId(rs.getInt("AppointmentID"));
                a.setCustomerId(rs.getInt("CustomerID"));
                a.setDoctorId(rs.getInt("DoctorID"));
                a.setServiceId(rs.getInt("ServiceID"));
                a.setAppointmentDate(rs.getTimestamp("AppointmentDate"));
                a.setStatus(rs.getString("Status"));
                a.setNote(rs.getString("Note"));
                return a;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateAppointment(Appointment app) {
        String sql = "UPDATE Appointments SET CustomerID=?, DoctorID=?, ServiceID=?, AppointmentDate=?, Status=?, Note=? WHERE AppointmentID=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, app.getCustomerId());
            ps.setInt(2, app.getDoctorId());
            ps.setInt(3, app.getServiceId());
            ps.setTimestamp(4, new Timestamp(app.getAppointmentDate().getTime()));
            ps.setString(5, app.getStatus());
            ps.setString(6, app.getNote());
            ps.setInt(7, app.getAppointmentId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
