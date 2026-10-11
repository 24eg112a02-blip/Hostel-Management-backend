package com.smarthostel.controller;

import com.smarthostel.dto.BulkAttendanceRequest;
import com.smarthostel.entity.AttendanceRecord;
import com.smarthostel.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @GetMapping("/student/{studentId}")
    public List<AttendanceRecord> getAttendanceByStudent(@PathVariable Long studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }

    @GetMapping("/date/{date}")
    public List<AttendanceRecord> getAttendanceByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return attendanceRepository.findByDate(date);
    }

    @PostMapping("/mark")
    public ResponseEntity<?> markAttendance(@RequestBody AttendanceRecord record) {
        if (record.getStudentId() == null || record.getDate() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "studentId and date are required"));
        }

        Optional<AttendanceRecord> existing = attendanceRepository.findByStudentIdAndDate(
                record.getStudentId(),
                record.getDate()
        );

        if (existing.isPresent()) {
            AttendanceRecord toUpdate = existing.get();
            toUpdate.setStatus(record.getStatus());
            toUpdate.setRemarks(record.getRemarks());
            toUpdate.setMarkedBy(record.getMarkedBy());
            AttendanceRecord saved = attendanceRepository.save(toUpdate);
            return ResponseEntity.ok(saved);
        }

        AttendanceRecord saved = attendanceRepository.save(record);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PostMapping("/bulk-mark")
    public ResponseEntity<?> bulkMarkAttendance(@RequestBody BulkAttendanceRequest request) {
        if (request.getDate() == null || request.getRecords() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Date and records list are required"));
        }

        LocalDate date = request.getDate();
        String markedBy = request.getMarkedBy() != null ? request.getMarkedBy() : "Warden Staff";

        for (BulkAttendanceRequest.StudentStatus item : request.getRecords()) {
            Optional<AttendanceRecord> existing = attendanceRepository.findByStudentIdAndDate(item.getStudentId(), date);
            if (existing.isPresent()) {
                AttendanceRecord toUpdate = existing.get();
                toUpdate.setStatus(item.getStatus());
                toUpdate.setRemarks(item.getRemarks());
                toUpdate.setMarkedBy(markedBy);
                attendanceRepository.save(toUpdate);
            } else {
                AttendanceRecord newRecord = new AttendanceRecord();
                newRecord.setStudentId(item.getStudentId());
                newRecord.setDate(date);
                newRecord.setStatus(item.getStatus());
                newRecord.setRemarks(item.getRemarks());
                newRecord.setMarkedBy(markedBy);
                attendanceRepository.save(newRecord);
            }
        }

        return ResponseEntity.ok(Map.of("message", "Bulk roll call saved successfully", "count", request.getRecords().size()));
    }
}
