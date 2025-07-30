package com.example.note.projection;

import java.util.UUID;

public interface NoteResponse {
    UUID getId();
    String getTitle();
    String getContent();

}
