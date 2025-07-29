package com.example.note.dto.request;

import java.util.UUID;

public record CreateNoteRequest(
        String title,
        String content,
        UUID userId
) {
}
