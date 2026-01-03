package com.apexpath.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateDto(
    @NotBlank
    UUID id,

    @NotBlank
    String username,
    
    @NotBlank
    String email,
    
    @NotBlank
    String password
) {}
