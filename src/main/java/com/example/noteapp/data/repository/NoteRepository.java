package com.example.noteapp.data.repository;

import com.example.noteapp.data.entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
}
