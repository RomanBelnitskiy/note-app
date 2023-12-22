package com.example.noteapp.controller.controller;

import com.example.noteapp.controller.request.CreateNoteRequest;
import com.example.noteapp.controller.request.UpdateNoteRequest;
import com.example.noteapp.controller.response.NoteResponse;
import com.example.noteapp.service.dto.NoteDto;
import com.example.noteapp.service.mapper.NoteMapper;
import com.example.noteapp.service.service.NoteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notes")
@Validated
public class NoteController {
    @Autowired
    private NoteService service;
    @Autowired
    private NoteMapper mapper;

    @GetMapping
    public ResponseEntity<?> getAllNotes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mapper.toResponse(service.listAll()));
    }

    @PostMapping
    public ResponseEntity<NoteResponse> createNewNote(@Valid @NotNull @RequestBody CreateNoteRequest request) {
        NoteDto dto = service.add(mapper.toDto(request));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toResponse(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(@PathVariable @NotNull @Min(1) Long id,
                           @Valid @NotNull @RequestBody UpdateNoteRequest request) {
        NoteDto dto = service.update(mapper.toDto(id, request));

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(mapper.toResponse(dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNote(@PathVariable @NotNull @Min(1) Long id) {
        service.deleteById(id);
    }
}
