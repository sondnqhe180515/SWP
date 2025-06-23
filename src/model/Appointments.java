package model;

import java.util.Date;

public class Appointments {
    private int appointmentID;
    private int customerID;
    private int doctorID;
    private int serviceID;
    private Date appointmentDate;
    private String status;
    private String note;
    private Date createdAt;

    public Appointments() {
    }

    public Appointments(int appointmentID, int customerID, int doctorID, int serviceID, Date appointmentDate, String status, String note, Date createdAt) {
        this.appointmentID = appointmentID;
        this.customerID = customerID;
        this.doctorID = doctorID;
        this.serviceID = serviceID;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.note = note;
        this.createdAt = createdAt;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
