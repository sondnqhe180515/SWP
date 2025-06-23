/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.MedicalRecord;
import Model.MedicalRecordDetail;
import Model.Prescription;

import java.sql.*;
import java.util.List;
/**
 *
 * @author An
 */
public class MedicalRecordDAO {
       private Connection conn;

    public MedicalRecordDAO(Connection conn) {
        this.conn = conn;
    }

    public int insertMedicalRecord(MedicalRecord record) throws SQLException {
        String sql = "INSERT INTO MedicalRecords (user_id, doctor_id, visit_date, diagnosis, instructions) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, record.getUserId());
            stmt.setInt(2, record.getDoctorId());
            stmt.setDate(3, new java.sql.Date(record.getVisitDate().getTime()));
            stmt.setString(4, record.getDiagnosis());
            stmt.setString(5, record.getInstructions());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); 
                }
            }
        }
        return -1;
    }

    public void insertMedicalRecordDetails(int recordId, List<MedicalRecordDetail> details) throws SQLException {
        String sql = "INSERT INTO MedicalRecord_Details (record_id, med_name, dose, frequency, days, note) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (MedicalRecordDetail d : details) {
                stmt.setInt(1, recordId);
                stmt.setString(2, d.getMedName());
                stmt.setString(3, d.getDose());
                stmt.setString(4, d.getFrequency());
                stmt.setInt(5, d.getDays());
                stmt.setString(6, d.getNote());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    public void createPrescription(Prescription p) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
