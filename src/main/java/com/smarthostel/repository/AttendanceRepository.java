package com.smarthostel.repository;

import com.smarthostel.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceRecord, Long> {
    List<AttendanceRecord> findByStudentId(Long studentId);
    List<AttendanceRecord> findByDate(LocalDate date);
    Optional<AttendanceRecord> findByStudentIdAndDate(Long studentId, LocalDate date);
    long countByDateAndStatus(LocalDate date, String status);
    long countByDate(LocalDate date);
}
