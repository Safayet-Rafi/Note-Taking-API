package com.example.note.dto.response;

import java.util.UUID;

public record NoteResponse(
        UUID id,
        String title,
        String content
) {}
