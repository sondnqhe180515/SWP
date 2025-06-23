/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author An
 */
public class MedicalRecord {
     private int recordId;
    private int userId;     // bệnh nhân
    private int doctorId;   // bác sĩ
    private Date visitDate;
    private String diagnosis;
    private String instructions;
    private List<MedicalRecordDetail> details;

    public MedicalRecord(int recordId, int userId, int doctorId, Date visitDate, String diagnosis, String instructions, List<MedicalRecordDetail> details) {
        this.recordId = recordId;
        this.userId = userId;
        this.doctorId = doctorId;
        this.visitDate = visitDate;
        this.diagnosis = diagnosis;
        this.instructions = instructions;
        this.details = details;
    }

    public MedicalRecord() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
    // Getters và Setters
    public int getRecordId() {
        return recordId;
    }
    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getDoctorId() {
        return doctorId;
    }
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
    public Date getVisitDate() {
        return visitDate;
    }
    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }
    public String getDiagnosis() {
        return diagnosis;
    }
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    public String getInstructions() {
        return instructions;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    public List<MedicalRecordDetail> getDetails() {
        return details;
    }
    public void setDetails(List<MedicalRecordDetail> details) {
        this.details = details;
    }
}
