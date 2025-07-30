package com.example.note.repository;

import com.example.note.model.NoteVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NoteVersionRepository extends JpaRepository<NoteVersion, UUID> {
}
