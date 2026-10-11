package com.smarthostel.controller;

import com.smarthostel.entity.Student;
import com.smarthostel.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        if (student.getEmail() == null || student.getName() == null || student.getRollNumber() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Name, email, and roll number are required."));
        }
        Student saved = studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        return studentRepository.findById(id).map(student -> {
            if (studentDetails.getName() != null) student.setName(studentDetails.getName());
            if (studentDetails.getEmail() != null) student.setEmail(studentDetails.getEmail());
            if (studentDetails.getPhone() != null) student.setPhone(studentDetails.getPhone());
            if (studentDetails.getRollNumber() != null) student.setRollNumber(studentDetails.getRollNumber());
            if (studentDetails.getCourse() != null) student.setCourse(studentDetails.getCourse());
            if (studentDetails.getYear() != null) student.setYear(studentDetails.getYear());
            if (studentDetails.getRoomNumber() != null) student.setRoomNumber(studentDetails.getRoomNumber());
            if (studentDetails.getBlock() != null) student.setBlock(studentDetails.getBlock());
            if (studentDetails.getGuardianName() != null) student.setGuardianName(studentDetails.getGuardianName());
            if (studentDetails.getGuardianPhone() != null) student.setGuardianPhone(studentDetails.getGuardianPhone());
            if (studentDetails.getAddress() != null) student.setAddress(studentDetails.getAddress());
            if (studentDetails.getStatus() != null) student.setStatus(studentDetails.getStatus());

            Student updated = studentRepository.save(student);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        return studentRepository.findById(id).map(student -> {
            studentRepository.delete(student);
            return ResponseEntity.ok(Map.of("message", "Student deleted successfully"));
        }).orElse(ResponseEntity.notFound().build());
    }
}
