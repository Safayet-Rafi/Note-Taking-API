package com.example.note.dto.response;


public record EmailMessage(
        String to,
        String subject,
        String body
) {}
