package com.example.note.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoteNotFoundException(NoteNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(UserAlreadyExist.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExist(UserAlreadyExist ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(404).body(response);
    }

}
