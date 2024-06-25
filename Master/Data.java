class Data {
    private String patientUsername;
    private String diagnosis;
    private String timestamp;

    public Data(String patientUsername, String diagnosis, String timestamp) {
        this.patientUsername = patientUsername;
        this.diagnosis = diagnosis;
        this.timestamp = timestamp;
    }

    public String getPatientUsername() {
        return patientUsername;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
