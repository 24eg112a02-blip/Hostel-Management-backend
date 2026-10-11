package com.smarthostel.dto;

import java.util.List;
import java.util.Map;

public class DashboardStatsDto {
    private long totalStudents;
    private long totalRooms;
    private long availableBeds;
    private long occupiedBeds;
    private long pendingComplaints;
    private long resolvedComplaints;
    private long pendingLeaveRequests;
    private double todayAttendancePercentage;
    private long activeNoticesCount;
    private List<Map<String, Object>> occupancyByBlock;
    private List<Map<String, Object>> complaintStats;
    private List<Map<String, Object>> weeklyAttendance;

    public DashboardStatsDto() {}

    public long getTotalStudents() { return totalStudents; }
    public void setTotalStudents(long totalStudents) { this.totalStudents = totalStudents; }

    public long getTotalRooms() { return totalRooms; }
    public void setTotalRooms(long totalRooms) { this.totalRooms = totalRooms; }

    public long getAvailableBeds() { return availableBeds; }
    public void setAvailableBeds(long availableBeds) { this.availableBeds = availableBeds; }

    public long getOccupiedBeds() { return occupiedBeds; }
    public void setOccupiedBeds(long occupiedBeds) { this.occupiedBeds = occupiedBeds; }

    public long getPendingComplaints() { return pendingComplaints; }
    public void setPendingComplaints(long pendingComplaints) { this.pendingComplaints = pendingComplaints; }

    public long getResolvedComplaints() { return resolvedComplaints; }
    public void setResolvedComplaints(long resolvedComplaints) { this.resolvedComplaints = resolvedComplaints; }

    public long getPendingLeaveRequests() { return pendingLeaveRequests; }
    public void setPendingLeaveRequests(long pendingLeaveRequests) { this.pendingLeaveRequests = pendingLeaveRequests; }

    public double getTodayAttendancePercentage() { return todayAttendancePercentage; }
    public void setTodayAttendancePercentage(double todayAttendancePercentage) { this.todayAttendancePercentage = todayAttendancePercentage; }

    public long getActiveNoticesCount() { return activeNoticesCount; }
    public void setActiveNoticesCount(long activeNoticesCount) { this.activeNoticesCount = activeNoticesCount; }

    public List<Map<String, Object>> getOccupancyByBlock() { return occupancyByBlock; }
    public void setOccupancyByBlock(List<Map<String, Object>> occupancyByBlock) { this.occupancyByBlock = occupancyByBlock; }

    public List<Map<String, Object>> getComplaintStats() { return complaintStats; }
    public void setComplaintStats(List<Map<String, Object>> complaintStats) { this.complaintStats = complaintStats; }

    public List<Map<String, Object>> getWeeklyAttendance() { return weeklyAttendance; }
    public void setWeeklyAttendance(List<Map<String, Object>> weeklyAttendance) { this.weeklyAttendance = weeklyAttendance; }
}
