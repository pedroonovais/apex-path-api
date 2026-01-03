package com.apexpath.dto;

public record LoginResponseDto(
    String token,
    String expiration
) {}
