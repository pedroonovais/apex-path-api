package com.apexpath.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apexpath.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
