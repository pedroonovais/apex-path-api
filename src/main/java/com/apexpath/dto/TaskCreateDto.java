package com.apexpath.dto;

import java.util.Set;
import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskCreateDto {
    
    @NotBlank(message = "O nome da tarefa é obrigatório")
    @Size(max = 50, message = "O nome da tarefa deve ter no máximo 50 caracteres.")
    private String name;

    @NotBlank(message = "A descrição da tarefa é obrigatória")
    @Size(max = 250, message = "A descrição deve ter no máximo 250 caracteres.")
    private String description;

    @NotNull(message = "A experiência é obrigatória")
    private Long experience;

    @NotNull(message = "É necessário ter pelo menos um atributo")
    @Size(min = 1, message = "A tarefa deve ter pelo menos um atributo")
    private Set<UUID> attributeIds;
}