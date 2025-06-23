package dao;

import dal.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.MedicalRecords;

public class MedicalRecordDAO extends DBContext {

    public void insertMedicalRecord(MedicalRecords r) throws SQLException {
        String sql = "INSERT INTO MedicalRecords (AppointmentID, Diagnosis, Treatment, Description, ReExamDate, CreatedAt) VALUES (?, ?, ?, ?, ?, GETDATE())";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, r.getAppointmentID());
            ps.setString(2, r.getDiagnosis());
            ps.setString(3, r.getTreatment());
            ps.setString(4, r.getDescription());
            if (r.getReExamDate() != null) {
                ps.setDate(5, new java.sql.Date(r.getReExamDate().getTime()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            ps.executeUpdate();
        }
    }

    public void updateReExamNote(int recordId, String reExamNote) {
        String sql = "UPDATE MedicalRecords SET ReExamNote = ? WHERE RecordID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, reExamNote);
            ps.setInt(2, recordId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MedicalRecords> getAllMedicalRecords(String searchName) {
        List<MedicalRecords> list = new ArrayList<>();
        String sql = "SELECT m.*, u.FullName AS CustomerName "
                + "FROM MedicalRecords m "
                + "JOIN Appointments a ON m.AppointmentID = a.AppointmentID "
                + "JOIN Users u ON a.CustomerID = u.UserID ";

        if (searchName != null && !searchName.trim().isEmpty()) {
            sql += "WHERE u.FullName LIKE ? ";
        }

        sql += "ORDER BY m.RecordID DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (searchName != null && !searchName.trim().isEmpty()) {
                ps.setString(1, "%" + searchName + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MedicalRecords record = new MedicalRecords();
                record.setRecordID(rs.getInt("RecordID"));
                record.setAppointmentID(rs.getInt("AppointmentID"));
                record.setDiagnosis(rs.getString("Diagnosis"));
                record.setTreatment(rs.getString("Treatment"));
                record.setDescription(rs.getString("Description"));
                record.setCustomerName(rs.getString("CustomerName"));
                record.setCreatedAt(rs.getTimestamp("CreatedAt"));
                record.setReExamDate(rs.getTimestamp("ReExamDate"));
                record.setReExamNote(rs.getString("ReExamNote"));
                list.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteMedicalRecord(int recordId) {
        String sql = "DELETE FROM MedicalRecords WHERE RecordID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recordId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
