package com.github.juli220620.service;

import com.github.juli220620.mapper.BookEntityMapper;
import com.github.juli220620.model.BookDto;
import com.github.juli220620.model.BookEntity;
import com.github.juli220620.repo.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookSearchService {

    private final BookRepo repo;
    private final BookEntityMapper mapper;

    public List<BookDto> findAllByAuthor(String author) {
        return mapList(repo.findAllByAuthor(author));
    }

    public List<BookDto> findByDescriptionPart(String descriptionPart) {
        return mapList(repo.findByDescriptionPart(descriptionPart));
    }

    private List<BookDto> mapList(List<BookEntity> unmappedList) {
        return unmappedList.stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }
}
