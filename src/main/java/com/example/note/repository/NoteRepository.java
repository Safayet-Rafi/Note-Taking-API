package com.example.note.repository;

import com.example.note.model.Note;
import com.example.note.projection.NoteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {

    Page<NoteResponse> findAllBy(Pageable pageable);

    Optional<NoteResponse> findProjectionById(UUID id);
}
