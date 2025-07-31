package com.example.note.dto.request;

public record SignUpRequest(
        String username,
        String email,
        String password
) {
}
