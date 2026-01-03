package com.apexpath.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome de usuário é obrigatório")
    private String username;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    private String email;
    
    @JsonIgnore
    @NotBlank(message = "A senha é obrigatória")
    private String password;

    @NotNull(message = "A experiência é obrigatória")
    private Long experience;

    @NotNull(message = "A força é obrigatória")
    private int strength;

    @NotNull(message = "A resistência é obrigatória")
    private int resistance;

    @NotNull(message = "A inteligência é obrigatória")
    private int intelligence;
    
    @NotNull(message = "A disciplina é obrigatória")
    private int discipline;

    @NotNull(message = "A fé é obrigatória")
    private int faith;

    @NotNull(message = "O carisma é obrigatório")
    private int charisma;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
}
