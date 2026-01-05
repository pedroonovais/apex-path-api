package com.apexpath.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "attributes")
public class Attribute {
    
    @Id
    @GeneratedValue
    @Column()
    private UUID id;

    @NotNull(message = "O nome do atributo é obrigatorio")
    @Size(max = 50, message = "O tamanho máximo da mensagem é de 50 caracteres.")
    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    void onPersist() {
        this.createdAt = LocalDateTime.now();
    }
}
