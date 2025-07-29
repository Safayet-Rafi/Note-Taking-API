package com.example.note.service;

import com.example.note.dto.request.CreateNoteRequest;
import com.example.note.model.Note;
import com.example.note.model.User;
import com.example.note.repository.NoteRepository;
import com.example.note.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository){
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public List<Note> getAll(){

        return noteRepository.findAll();
    }

    public Note getById(UUID id){
        return noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));
    }

    public Note create(CreateNoteRequest createNoteRequest){
        User user = userRepository.findById(createNoteRequest.userId())
                .orElseThrow( () -> new RuntimeException("User Not Found"));

        Note note = new Note(
                createNoteRequest.title(),
                createNoteRequest.content(),
                user
        );

        return noteRepository.save(note);
    }

}
