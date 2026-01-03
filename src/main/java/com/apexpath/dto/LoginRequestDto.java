package com.apexpath.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
    @NotBlank(message = "O email é obrigatório")
    String email,

    @NotBlank(message = "A senha é obrigatória")
    String password
) {}
