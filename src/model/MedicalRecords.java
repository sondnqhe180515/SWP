package model;

import java.util.Date;

public class MedicalRecords {
    private int recordID;
    private int appointmentID;
    private String diagnosis;
    private String treatment;
    private String description;
    private Date reExamDate;
    private String reExamNote;
    private Date createdAt;
    private String customerName;

    public MedicalRecords() {
    }

    public MedicalRecords(int recordID, int appointmentID, String diagnosis, String treatment, String description, Date reExamDate, String reExamNote, Date createdAt, String customerName) {
        this.recordID = recordID;
        this.appointmentID = appointmentID;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.description = description;
        this.reExamDate = reExamDate;
        this.reExamNote = reExamNote;
        this.createdAt = createdAt;
        this.customerName = customerName;
    }

    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReExamDate() {
        return reExamDate;
    }

    public void setReExamDate(Date reExamDate) {
        this.reExamDate = reExamDate;
    }

    public String getReExamNote() {
        return reExamNote;
    }

    public void setReExamNote(String reExamNote) {
        this.reExamNote = reExamNote;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    
}
