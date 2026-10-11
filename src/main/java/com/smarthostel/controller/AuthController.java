package com.smarthostel.controller;

import com.smarthostel.dto.AuthRequest;
import com.smarthostel.dto.AuthResponse;
import com.smarthostel.dto.RegisterRequest;
import com.smarthostel.entity.Student;
import com.smarthostel.entity.User;
import com.smarthostel.repository.StudentRepository;
import com.smarthostel.repository.UserRepository;
import com.smarthostel.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        String query = request.getUsernameOrEmail();
        Optional<User> userOpt = userRepository.findByEmail(query);
        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByRollNumber(query);
        }

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid credentials. User does not exist."));
        }

        User user = userOpt.get();

        // Check password (support BCrypt and demo fallback match)
        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword()) ||
                          request.getPassword().equals(user.getPassword());

        if (!matches) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid password. Please check your credentials."));
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return ResponseEntity.ok(new AuthResponse(token, user));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Email already registered: " + request.getEmail()));
        }

        if (request.getRollNumber() != null && userRepository.existsByRollNumber(request.getRollNumber())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Roll number already registered: " + request.getRollNumber()));
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRollNumber(request.getRollNumber());
        user.setRole("STUDENT");
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        // Also create a Student record
        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setRollNumber(request.getRollNumber());
        student.setCourse(request.getCourse() != null ? request.getCourse() : "B.Tech Computer Science");
        student.setYear(request.getYear() != null ? request.getYear() : 1);
        student.setStatus("ACTIVE");
        studentRepository.save(student);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(token, user));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Missing token"));
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid or expired token"));
        }

        String email = jwtUtil.getEmailFromToken(token);
        Optional<User> userOpt = userRepository.findByEmail(email);
        return userOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
