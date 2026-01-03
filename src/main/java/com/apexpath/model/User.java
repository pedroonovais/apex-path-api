package com.apexpath.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

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

    @PrePersist
    void onPersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.experience = 0L;
        this.strength = 0;
        this.resistance = 0;
        this.intelligence = 0;
        this.discipline = 0;
        this.faith = 0;
        this.charisma = 0;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
