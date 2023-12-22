package com.example.noteapp.service.service;

import com.example.noteapp.data.entity.NoteEntity;
import com.example.noteapp.data.repository.NoteRepository;
import com.example.noteapp.service.dto.NoteDto;
import com.example.noteapp.service.exception.NoteNotFoundException;
import com.example.noteapp.service.mapper.NoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class NoteService implements ServiceBase<NoteDto, Long> {
    private final NoteRepository repository;
    private final NoteMapper mapper;

    @Override
    public List<NoteDto> listAll() {
        return mapper.toDtos(repository.findAll());
    }

    @Override
    public NoteDto add(NoteDto noteDto) {
        requireNonNull(noteDto);

        NoteEntity entity = mapper.toEntity(noteDto);
        entity = repository.save(entity);

        return mapper.toDto(entity);
    }

    @Override
    public void deleteById(Long id) {
        requireNonNull(id);

        repository.deleteById(id);
    }

    @Override
    @Transactional
    public NoteDto update(NoteDto dto) {
        requireNonNull(dto);
        requireNonNull(dto.getId());

        NoteEntity entity = repository.findById(dto.getId())
                .orElseThrow(() -> new NoteNotFoundException(dto.getId()));
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setFavorite(dto.isFavorite());

        return mapper.toDto(
                repository.save(entity)
        );
    }

    @Override
    public NoteDto getById(Long id) {
        requireNonNull(id);

        NoteEntity entity = repository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        return mapper.toDto(entity);
    }
}
