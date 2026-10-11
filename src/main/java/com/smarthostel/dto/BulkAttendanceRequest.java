package com.smarthostel.dto;

import java.time.LocalDate;
import java.util.List;

public class BulkAttendanceRequest {
    private LocalDate date;
    private String markedBy;
    private List<StudentStatus> records;

    public static class StudentStatus {
        private Long studentId;
        private String status;
        private String remarks;

        public StudentStatus() {}

        public Long getStudentId() { return studentId; }
        public void setStudentId(Long studentId) { this.studentId = studentId; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getRemarks() { return remarks; }
        public void setRemarks(String remarks) { this.remarks = remarks; }
    }

    public BulkAttendanceRequest() {}

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getMarkedBy() { return markedBy; }
    public void setMarkedBy(String markedBy) { this.markedBy = markedBy; }

    public List<StudentStatus> getRecords() { return records; }
    public void setRecords(List<StudentStatus> records) { this.records = records; }
}
