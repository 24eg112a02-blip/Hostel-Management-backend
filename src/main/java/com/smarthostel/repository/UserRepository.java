package com.smarthostel.repository;

import com.smarthostel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByRollNumber(String rollNumber);
    Boolean existsByEmail(String email);
    Boolean existsByRollNumber(String rollNumber);
}
