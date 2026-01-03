package com.apexpath.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apexpath.dto.UserRequest;
import com.apexpath.model.User;
import com.apexpath.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(UserRequest userRequest) {
        try {
            User user = User.builder()
                .username(userRequest.username())
                .email(userRequest.email())
                .password(passwordEncoder.encode(userRequest.password()))
                .build();

            return userRepository.save(user);
        }
        catch (Exception e) {
            log.error("Erro ao criar usuário: {}", e.getMessage());
            throw new RuntimeException("Erro ao criar usuário");
        }
    }
}
