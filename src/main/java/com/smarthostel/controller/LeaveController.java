package com.smarthostel.controller;

import com.smarthostel.entity.LeaveApplication;
import com.smarthostel.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/leaves")
@CrossOrigin(origins = "*")
public class LeaveController {

    @Autowired
    private LeaveRepository leaveRepository;

    @GetMapping
    public List<LeaveApplication> getAllLeaves() {
        return leaveRepository.findAll();
    }

    @GetMapping("/student/{studentId}")
    public List<LeaveApplication> getLeavesByStudent(@PathVariable Long studentId) {
        return leaveRepository.findByStudentId(studentId);
    }

    @PostMapping("/apply")
    public ResponseEntity<?> applyLeave(@RequestBody LeaveApplication leave) {
        if (leave.getStartDate() == null || leave.getEndDate() == null || leave.getReason() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Start date, end date, and reason are required."));
        }
        leave.setAppliedDate(LocalDate.now());
        leave.setStatus("PENDING");
        LeaveApplication saved = leaveRepository.save(leave);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateLeaveStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload
    ) {
        return leaveRepository.findById(id).map(leave -> {
            String status = payload.get("status");
            String remarks = payload.get("adminRemarks");
            String reviewer = payload.get("reviewedBy");

            if (status != null) leave.setStatus(status);
            if (remarks != null) leave.setAdminRemarks(remarks);
            if (reviewer != null) leave.setReviewedBy(reviewer);

            LeaveApplication updated = leaveRepository.save(leave);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }
}
