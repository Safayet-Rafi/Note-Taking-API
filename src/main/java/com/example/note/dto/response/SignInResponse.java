package com.example.note.dto.response;

public record SignInResponse(
        String accessToken,
        String refreshToken
) {}
