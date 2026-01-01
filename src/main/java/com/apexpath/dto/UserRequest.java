package com.apexpath.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
    @NotBlank(message = "O nome de usuário é obrigatório")
    String username,
    
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    String email,

    @NotBlank(message = "A senha é obrigatória")
    String password
) {}
