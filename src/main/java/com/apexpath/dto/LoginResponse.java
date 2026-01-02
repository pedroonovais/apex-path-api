package com.apexpath.dto;

public record LoginResponse(
    String token,
    String expiration
) {}
