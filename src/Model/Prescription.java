package Model;

public class Prescription {
    private int prescriptionID;
    private int recordID;
    private String medicineName;
    private String dose;
    private String freq;
    private String days;
    private String note;

    // Constructor, Getter, Setter
    public Prescription(){
    };

    public Prescription(int prescriptionID, int recordID, String medicineName, String dose, String freq, String days, String note) {
        this.prescriptionID = prescriptionID;
        this.recordID = recordID;
        this.medicineName = medicineName;
        this.dose = dose;
        this.freq = freq;
        this.days = days;
        this.note = note;
    }

    // ✅ Getter và Setter
    public int getPrescriptionID() {
        return prescriptionID;
    }

    public void setPrescriptionID(int prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    
}