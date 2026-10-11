package com.smarthostel.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "admin_accounts")
public class AdminAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String role; // ADMIN, WARDEN

    @Column(nullable = false)
    private String phone;

    private String blockAssigned;

    @Column(nullable = false)
    private String status = "ACTIVE"; // ACTIVE, INACTIVE

    private LocalDate createdAt = LocalDate.now();

    public AdminAccount() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getBlockAssigned() { return blockAssigned; }
    public void setBlockAssigned(String blockAssigned) { this.blockAssigned = blockAssigned; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }
}
