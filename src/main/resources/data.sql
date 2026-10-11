-- Smart Hostel Management System - Initial Seed Data

-- 1. Users table (Passwords: student123, warden123, admin123)
INSERT INTO users (id, name, email, password, role, roll_number, phone) VALUES
(1, 'Aarav Sharma', 'aarav.sharma@hostel.edu', '$2a$10$7Zq7vUjYnO3c0e1gY9F0/.lK6F4Yq2V2v0sH7G2c5y9k1w3e5r7t9', 'STUDENT', 'CS2023045', '+91 98765 43210'),
(2, 'Dr. Mary Joseph', 'warden.boys@hostel.edu', '$2a$10$7Zq7vUjYnO3c0e1gY9F0/.lK6F4Yq2V2v0sH7G2c5y9k1w3e5r7t9', 'WARDEN', NULL, '+91 94455 66778'),
(3, 'Dr. K. Raman', 'admin@hostel.edu', '$2a$10$7Zq7vUjYnO3c0e1gY9F0/.lK6F4Yq2V2v0sH7G2c5y9k1w3e5r7t9', 'ADMIN', NULL, '+91 98112 33445')
ON DUPLICATE KEY UPDATE name=VALUES(name);

-- 2. Students table
INSERT INTO students (id, name, email, phone, roll_number, course, year, room_number, block, guardian_name, guardian_phone, address, status, created_at) VALUES
(1, 'Aarav Sharma', 'aarav.sharma@hostel.edu', '+91 98765 43210', 'CS2023045', 'B.Tech Computer Science', 3, 'A-204', 'Block A (Boys)', 'Ramesh Sharma', '+91 98112 34567', '42 Maple Street, Bangalore, Karnataka', 'ACTIVE', '2024-08-01'),
(2, 'Ananya Iyer', 'ananya.iyer@hostel.edu', '+91 98765 43211', 'EC2023012', 'B.Tech Electronics & Comm', 3, 'B-102', 'Block B (Girls)', 'Suresh Iyer', '+91 98223 45678', '15 Temple Road, Chennai, Tamil Nadu', 'ACTIVE', '2024-08-01'),
(3, 'Rohan Gupta', 'rohan.gupta@hostel.edu', '+91 98765 43212', 'ME2022088', 'B.Tech Mechanical Engg', 4, 'A-101', 'Block A (Boys)', 'Mahesh Gupta', '+91 98334 56789', '88 Civil Lines, Jaipur, Rajasthan', 'ACTIVE', '2023-08-01'),
(4, 'Sneha Reddy', 'sneha.reddy@hostel.edu', '+91 98765 43213', 'CS2024031', 'B.Tech Computer Science', 2, 'B-205', 'Block B (Girls)', 'Venkat Reddy', '+91 98445 67890', '102 Jubilee Hills, Hyderabad, Telangana', 'ACTIVE', '2025-08-01'),
(5, 'Vikram Patel', 'vikram.patel@hostel.edu', '+91 98765 43214', 'CE2023055', 'B.Tech Civil Engg', 3, 'C-301', 'Block C (Freshers)', 'Kirit Patel', '+91 98556 78901', '74 Ring Road, Ahmedabad, Gujarat', 'ACTIVE', '2024-08-01')
ON DUPLICATE KEY UPDATE name=VALUES(name);

-- 3. Rooms table
INSERT INTO rooms (id, room_number, block, floor, capacity, occupied_beds, room_type, status, monthly_rent) VALUES
(1, 'A-101', 'Block A', 1, 3, 2, 'Non-AC', 'AVAILABLE', 3500.0),
(2, 'A-204', 'Block A', 2, 2, 2, 'AC', 'FULL', 5500.0),
(3, 'B-102', 'Block B', 1, 2, 1, 'AC', 'AVAILABLE', 5500.0),
(4, 'B-205', 'Block B', 2, 3, 1, 'Non-AC', 'AVAILABLE', 3500.0),
(5, 'C-101', 'Block C', 1, 4, 0, 'Non-AC', 'AVAILABLE', 3000.0),
(6, 'C-301', 'Block C', 3, 3, 2, 'Non-AC', 'AVAILABLE', 3200.0)
ON DUPLICATE KEY UPDATE room_number=VALUES(room_number);

-- 4. Complaints table
INSERT INTO complaints (id, title, category, description, submission_date, status, student_id, student_name, student_roll_number, room_number, admin_remarks, resolved_date) VALUES
(1, 'Ceiling fan making squeaking noise', 'Electrical', 'Ceiling fan in room A-204 is vibrating heavily and making loud screeching sound at speed 4 and 5.', '2026-10-09', 'IN_PROGRESS', 1, 'Aarav Sharma', 'CS2023045', 'A-204', 'Electrician technician visited; bearings need replacement. Work order #E-142 issued.', NULL),
(2, 'Study desk reading lamp socket not working', 'Electrical', 'The electrical power strip near Bed 2 has tripped breaker and is not giving supply.', '2026-10-05', 'RESOLVED', 1, 'Aarav Sharma', 'CS2023045', 'A-204', 'MCB reset and replacement 16A modular socket fitted by electrician.', '2026-10-06'),
(3, 'Cold water pressure low in 2nd floor washroom', 'Plumbing', 'Shower unit pressure is extremely low during morning peak hours (7:30 to 8:30 AM).', '2026-10-10', 'SUBMITTED', 2, 'Ananya Iyer', 'EC2023012', 'B-102', 'Inspection scheduled with maintenance plumber for Monday morning.', NULL),
(4, 'Wi-Fi Access Point disconnection', 'Wi-Fi', 'Block C 3rd floor router repeatedly drops connection every 20 minutes.', '2026-10-08', 'IN_PROGRESS', 5, 'Vikram Patel', 'CE2023055', 'C-301', 'Campus IT network administrator dispatched to reflash firmware.', NULL)
ON DUPLICATE KEY UPDATE title=VALUES(title);

-- 5. Attendance table
INSERT INTO attendance_records (id, student_id, student_name, roll_number, room_number, date, status, marked_by, remarks) VALUES
(1, 1, 'Aarav Sharma', 'CS2023045', 'A-204', '2026-10-11', 'PRESENT', 'Dr. Mary Joseph', 'Present during 9:30 PM roll call'),
(2, 2, 'Ananya Iyer', 'EC2023012', 'B-102', '2026-10-11', 'PRESENT', 'Dr. Mary Joseph', 'Present'),
(3, 3, 'Rohan Gupta', 'ME2022088', 'A-101', '2026-10-11', 'PRESENT', 'Dr. Mary Joseph', 'Present in room'),
(4, 4, 'Sneha Reddy', 'CS2024031', 'B-205', '2026-10-11', 'ON_LEAVE', 'Dr. Mary Joseph', 'Outpass approved for Diwali weekend'),
(5, 5, 'Vikram Patel', 'CE2023055', 'C-301', '2026-10-11', 'PRESENT', 'Dr. Mary Joseph', 'Present')
ON DUPLICATE KEY UPDATE status=VALUES(status);

-- 6. Notices table
INSERT INTO notices (id, title, description, publication_date, author, author_role, is_important, category) VALUES
(1, 'Hostel Water Tank Disinfection Schedule', 'Annual overhead water reservoir cleaning and chemical chlorination is scheduled for Thursday from 8:00 AM to 1:00 PM. Water supply will be suspended during this window.', '2026-10-10', 'Dr. K. Raman', 'Chief Warden', 1, 'Maintenance'),
(2, 'Dining Hall Diwali Special Banquet Notice', 'All residents are invited to the traditional festive dinner on Sunday from 7:30 PM in the central mess hall. Cultural performances by hostel council will precede the dinner.', '2026-10-08', 'Student Mess Secretary', 'Hostel Council', 0, 'Events'),
(3, 'Revised Night Curfew & Biometric Timings', 'All undergraduate residents must record biometric check-in at the hostel gate by 10:00 PM on weekdays and 10:30 PM on Saturdays. Gate logs are synced with department attendance.', '2026-10-01', 'Dr. Mary Joseph', 'Warden (Boys)', 1, 'Rules'),
(4, 'High-Speed Wi-Fi Bandwidth Upgrade', 'Campus IT has installed 5 GHz dual-band mesh nodes across all residential blocks. Speeds upgraded from 50 Mbps to 150 Mbps per room.', '2026-09-28', 'Campus IT Support', 'Network Administrator', 0, 'General')
ON DUPLICATE KEY UPDATE title=VALUES(title);

-- 7. Leave Applications table
INSERT INTO leave_applications (id, student_id, student_name, roll_number, room_number, leave_type, start_date, end_date, reason, destination, emergency_contact, status, applied_date, admin_remarks, reviewed_by) VALUES
(1, 1, 'Aarav Sharma', 'CS2023045', 'A-204', 'Home Visit', '2026-10-18', '2026-10-22', 'Visiting family for traditional festival celebrations and family reunion.', 'Sector 14, Chandigarh, Punjab', '+91 98112 34567', 'PENDING', '2026-10-10', 'Awaiting telephonic verification with father.', 'Dr. Mary Joseph'),
(2, 2, 'Ananya Iyer', 'EC2023012', 'B-102', 'Academic', '2026-10-14', '2026-10-16', 'Attending IEEE Student Technical Symposium at IIT Madras.', 'IIT Madras Campus, Chennai', '+91 98223 45678', 'APPROVED', '2026-10-07', 'Department recommendation letter verified. Outpass issued.', 'Dr. Mary Joseph'),
(3, 4, 'Sneha Reddy', 'CS2024031', 'B-205', 'Home Visit', '2026-10-10', '2026-10-15', 'Sister wedding celebration.', 'Jubilee Hills, Hyderabad', '+91 98445 67890', 'APPROVED', '2026-10-05', 'Parental consent form submitted and verified.', 'Dr. Mary Joseph')
ON DUPLICATE KEY UPDATE reason=VALUES(reason);

-- 8. Admin Accounts table
INSERT INTO admin_accounts (id, name, email, role, phone, block_assigned, status, created_at) VALUES
(1, 'Dr. K. Raman', 'admin@hostel.edu', 'ADMIN', '+91 98112 33445', 'All Campus Blocks', 'ACTIVE', '2023-01-15'),
(2, 'Dr. Mary Joseph', 'warden.boys@hostel.edu', 'WARDEN', '+91 94455 66778', 'Block A (Boys)', 'ACTIVE', '2023-06-20'),
(3, 'Prof. Sunita Rao', 'warden.girls@hostel.edu', 'WARDEN', '+91 97788 11223', 'Block B (Girls)', 'ACTIVE', '2023-08-10')
ON DUPLICATE KEY UPDATE name=VALUES(name);
