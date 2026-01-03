package com.apexpath.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apexpath.dto.UserRequestDto;
import com.apexpath.dto.UserUpdateDto;
import com.apexpath.model.User;
import com.apexpath.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
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

    public User create(UserRequestDto userRequest) {
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

    public User update(UserUpdateDto userUpdateDto) {
        var user = getById(userUpdateDto.id());
        user.setEmail(userUpdateDto.email());
        user.setUsername(userUpdateDto.username());
        user.setPassword(passwordEncoder.encode(userUpdateDto.password()));
        userRepository.save(user);
        return user;
    }

    public void updateLatestLogin(String email) {
        var user = getByEmail(email);
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public User getById(UUID id) {   
        return userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                "Usuário não encontrado para o id: " + id
            ));
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException(
                "Usuário não encontrado para o email: " + email
            ));
    }
}
