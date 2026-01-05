package com.apexpath.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "tasks")
public class Task {
    
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "O nome da tarefa é obrigatorio")
    @Size(max = 50, message = "O nome da tarefa deve ter no maximo 50 caracteres.")
    private String name;

    @NotBlank(message = "A descrição da tarefa é obrigatoria")
    @Size(max = 250, message = "O nome da tarefa deve ter no maximo 250 caracteres.")
    private String description;

    @NotNull(message = "A experiência é obrigatória")
    private Long experience;

    @ManyToMany
    @JoinTable(
        name = "task_attributes",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "attribute_id")
    )
    @NotNull(message = "É necessario ter pelo menos um atributo")
    @Size(min = 1, message = "A tarefa deve ter pelo menos um atributo")
    private Set<Attribute> attributes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    void onPersist() {
        this.createdAt = LocalDateTime.now();
        this.attributes = new HashSet<>();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
