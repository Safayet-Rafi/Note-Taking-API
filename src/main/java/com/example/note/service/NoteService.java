package com.example.note.service;

import com.example.note.dto.request.CreateNoteRequest;
import com.example.note.dto.request.UpdateNoteRequest;
import com.example.note.exception.NoteNotFoundException;
import com.example.note.exception.UserNotFoundException;
import com.example.note.model.Note;
import com.example.note.model.NoteVersion;
import com.example.note.model.User;
import com.example.note.projection.NoteResponse;
import com.example.note.repository.NoteRepository;
import com.example.note.repository.NoteVersionRepository;
import com.example.note.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final NoteVersionRepository noteVersionRepository;

    public NoteService(NoteRepository noteRepository,
                       UserRepository userRepository,
                       NoteVersionRepository noteVersionRepository
    ){
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.noteVersionRepository = noteVersionRepository;
    }

    public Page<NoteResponse> getAll(Pageable pageable){

        return noteRepository.findAllBy(pageable);
    }

    public NoteResponse getById(UUID id){
        return noteRepository.findProjectionById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));
    }

    public String create(CreateNoteRequest createNoteRequest){
        User user = userRepository.findById(createNoteRequest.userId())
                .orElseThrow( () -> new UserNotFoundException("User Not Found"));

        Note note = new Note(
                createNoteRequest.title(),
                createNoteRequest.content(),
                user,
                1
        );

        noteRepository.save(note);

        return "Note Created Successfully";
    }

    public NoteResponse update(UUID id, UpdateNoteRequest updateNoteRequest){
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));

        NoteVersion noteVersion = new NoteVersion(
                note.getTitle(),
                note.getContent(),
                note.getCurrentVersion(),
                note
        );

        noteVersionRepository.save(noteVersion);

        note.setContent(updateNoteRequest.content());
        note.setCurrentVersion(note.getCurrentVersion() + 1);

        noteRepository.save(note);

        return noteRepository.findProjectionById(id)
                .orElseThrow( () -> new NoteNotFoundException("Note Not Found"));
    }

}
