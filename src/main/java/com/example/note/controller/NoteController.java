package com.example.note.controller;

import com.example.note.dto.request.CreateNoteRequest;
import com.example.note.model.Note;
import com.example.note.service.NoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> getAll(){
        return noteService.getAll();
    }

    @GetMapping("/{id}")
    public Note getById(@PathVariable UUID id){
        return noteService.getById(id);
    }

    @PostMapping
    public Note create(@RequestBody CreateNoteRequest createNoteRequest){
        return noteService.create(createNoteRequest);
    }
}
