package com.smarthostel.repository;

import com.smarthostel.entity.LeaveApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveApplication, Long> {
    List<LeaveApplication> findByStudentId(Long studentId);
    List<LeaveApplication> findByStatus(String status);
    long countByStatus(String status);
}
