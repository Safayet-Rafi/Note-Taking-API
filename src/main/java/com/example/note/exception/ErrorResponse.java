package com.example.note.exception;

public record ErrorResponse(
        String message,
        String timestamp
) {
}
