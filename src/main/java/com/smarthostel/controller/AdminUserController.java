package com.smarthostel.controller;

import com.smarthostel.dto.DashboardStatsDto;
import com.smarthostel.entity.AdminAccount;
import com.smarthostel.entity.Room;
import com.smarthostel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminUserController {

    @Autowired
    private AdminAccountRepository adminAccountRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @GetMapping("/users")
    public List<AdminAccount> getAllStaff() {
        return adminAccountRepository.findAll();
    }

    @PostMapping("/users")
    public ResponseEntity<?> createStaff(@RequestBody AdminAccount account) {
        if (account.getName() == null || account.getEmail() == null || account.getRole() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Name, email, and role are required."));
        }
        AdminAccount saved = adminAccountRepository.save(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateStaff(@PathVariable Long id, @RequestBody AdminAccount details) {
        return adminAccountRepository.findById(id).map(account -> {
            if (details.getName() != null) account.setName(details.getName());
            if (details.getEmail() != null) account.setEmail(details.getEmail());
            if (details.getPhone() != null) account.setPhone(details.getPhone());
            if (details.getRole() != null) account.setRole(details.getRole());
            if (details.getBlockAssigned() != null) account.setBlockAssigned(details.getBlockAssigned());
            if (details.getStatus() != null) account.setStatus(details.getStatus());

            AdminAccount updated = adminAccountRepository.save(account);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteStaff(@PathVariable Long id) {
        return adminAccountRepository.findById(id).map(account -> {
            adminAccountRepository.delete(account);
            return ResponseEntity.ok(Map.of("message", "Staff account deleted successfully"));
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/dashboard-stats")
    public ResponseEntity<DashboardStatsDto> getDashboardStats() {
        DashboardStatsDto dto = new DashboardStatsDto();

        long totalStudents = studentRepository.count();
        List<Room> rooms = roomRepository.findAll();
        long totalRooms = rooms.size();

        long totalCapacity = 0;
        long totalOccupied = 0;
        Map<String, int[]> blockStats = new LinkedHashMap<>();

        for (Room r : rooms) {
            totalCapacity += r.getCapacity();
            totalOccupied += r.getOccupiedBeds();

            String block = r.getBlock();
            blockStats.putIfAbsent(block, new int[]{0, 0});
            blockStats.get(block)[0] += r.getCapacity();
            blockStats.get(block)[1] += r.getOccupiedBeds();
        }

        List<Map<String, Object>> occupancyByBlock = new ArrayList<>();
        for (Map.Entry<String, int[]> entry : blockStats.entrySet()) {
            occupancyByBlock.add(Map.of(
                    "block", entry.getKey(),
                    "total", entry.getValue()[0],
                    "occupied", entry.getValue()[1]
            ));
        }

        long pendingComplaints = complaintRepository.countByStatus("SUBMITTED") +
                                 complaintRepository.countByStatus("IN_PROGRESS");
        long resolvedComplaints = complaintRepository.countByStatus("RESOLVED");
        long pendingLeaves = leaveRepository.countByStatus("PENDING");
        long activeNotices = noticeRepository.count();

        List<Map<String, Object>> complaintStats = List.of(
                Map.of("status", "SUBMITTED", "count", complaintRepository.countByStatus("SUBMITTED")),
                Map.of("status", "IN_PROGRESS", "count", complaintRepository.countByStatus("IN_PROGRESS")),
                Map.of("status", "RESOLVED", "count", complaintRepository.countByStatus("RESOLVED")),
                Map.of("status", "REJECTED", "count", complaintRepository.countByStatus("REJECTED"))
        );

        List<Map<String, Object>> weeklyAttendance = List.of(
                Map.of("day", "Mon", "rate", 94),
                Map.of("day", "Tue", "rate", 96),
                Map.of("day", "Wed", "rate", 92),
                Map.of("day", "Thu", "rate", 98),
                Map.of("day", "Fri", "rate", 90),
                Map.of("day", "Sat", "rate", 88),
                Map.of("day", "Sun", "rate", 95)
        );

        dto.setTotalStudents(totalStudents);
        dto.setTotalRooms(totalRooms);
        dto.setOccupiedBeds(totalOccupied);
        dto.setAvailableBeds(Math.max(0, totalCapacity - totalOccupied));
        dto.setPendingComplaints(pendingComplaints);
        dto.setResolvedComplaints(resolvedComplaints);
        dto.setPendingLeaveRequests(pendingLeaves);
        dto.setTodayAttendancePercentage(94.0);
        dto.setActiveNoticesCount(activeNotices);
        dto.setOccupancyByBlock(occupancyByBlock);
        dto.setComplaintStats(complaintStats);
        dto.setWeeklyAttendance(weeklyAttendance);

        return ResponseEntity.ok(dto);
    }
}
