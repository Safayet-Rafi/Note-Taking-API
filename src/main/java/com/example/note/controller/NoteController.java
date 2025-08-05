package com.example.note.controller;

import com.example.note.dto.request.CreateNoteRequest;
import com.example.note.dto.request.UpdateNoteRequest;
import com.example.note.dto.response.NoteResponse;
import com.example.note.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notes")
@AllArgsConstructor
public class NoteController {

    private final NoteService noteService;


    @GetMapping
    public ResponseEntity<Page<NoteResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<NoteResponse> result = noteService.getAll(pageable);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getById(@PathVariable UUID id){
        return ResponseEntity.ok(noteService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateNoteRequest createNoteRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(noteService.create(createNoteRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<NoteResponse> update(@PathVariable UUID id, @RequestBody UpdateNoteRequest updateNoteRequest){
        return ResponseEntity.ok(noteService.update(id, updateNoteRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        noteService.delete(id);
        return ResponseEntity.ok("Note deleted successfully");
    }


}
