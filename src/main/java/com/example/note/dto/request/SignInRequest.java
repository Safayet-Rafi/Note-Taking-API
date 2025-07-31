package com.example.note.dto.request;

public record SignInRequest(
        String email,
        String password
) {
}
