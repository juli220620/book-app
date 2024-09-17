package com.github.juli220620.service;

import com.github.juli220620.mapper.BookEntityMapper;
import com.github.juli220620.model.BookDto;
import com.github.juli220620.repo.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookCrudService {

    private final BookRepo repo;
    private final BookEntityMapper mapper;

    public void addBook(BookDto dto) {
        repo.save(mapper.dtoToEntity(dto));
    }

    public List<BookDto> listAll() {
        return repo.findAll().stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    public BookDto editBook(BookDto editedData) {
        return repo.findById(editedData.getId())
                .map(mapper::entityToDto)
                .map(it -> mapper.dtoToDto(editedData, it))
                .map(mapper::dtoToEntity)
                .map(repo::save)
                .map(mapper::entityToDto)
                .orElseThrow();
    }

    public void deleteBook(Long id) {
        repo.deleteById(id);
    }

}
