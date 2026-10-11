package com.smarthostel.controller;

import com.smarthostel.entity.Complaint;
import com.smarthostel.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/complaints")
@CrossOrigin(origins = "*")
public class ComplaintController {

    @Autowired
    private ComplaintRepository complaintRepository;

    @GetMapping
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    @GetMapping("/student/{studentId}")
    public List<Complaint> getComplaintsByStudent(@PathVariable Long studentId) {
        return complaintRepository.findByStudentId(studentId);
    }

    @PostMapping
    public ResponseEntity<?> createComplaint(@RequestBody Complaint complaint) {
        if (complaint.getTitle() == null || complaint.getDescription() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Title and description are required."));
        }
        complaint.setSubmissionDate(LocalDate.now());
        complaint.setStatus("SUBMITTED");
        Complaint saved = complaintRepository.save(complaint);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateComplaintStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload
    ) {
        return complaintRepository.findById(id).map(complaint -> {
            String status = payload.get("status");
            String remarks = payload.get("adminRemarks");

            if (status != null) {
                complaint.setStatus(status);
                if ("RESOLVED".equalsIgnoreCase(status)) {
                    complaint.setResolvedDate(LocalDate.now());
                }
            }
            if (remarks != null) {
                complaint.setAdminRemarks(remarks);
            }

            Complaint updated = complaintRepository.save(complaint);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComplaint(@PathVariable Long id) {
        return complaintRepository.findById(id).map(complaint -> {
            complaintRepository.delete(complaint);
            return ResponseEntity.ok(Map.of("message", "Complaint deleted successfully"));
        }).orElse(ResponseEntity.notFound().build());
    }
}
