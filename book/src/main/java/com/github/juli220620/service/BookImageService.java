package com.github.juli220620.service;

import com.github.juli220620.mapper.BookEntityMapper;
import com.github.juli220620.model.BookImageDto;
import com.github.juli220620.repo.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookImageService {

    private BookRepo repo;
    private BookEntityMapper mapper;

    public Optional<BookImageDto> findImageIdById(Long id) {
        return repo.findById(id).map(mapper::entityToImageDto);
    }

    public Optional<String> getImageId(Long bookId) {
        return repo.getImageId(bookId);
    }

    public void save(BookImageDto dto) {
        var entity = repo.findById(dto.getId()).orElseThrow();

        entity.setImageId(dto.getImageId());
        repo.save(entity);
    }

    public boolean entryExists(Long bookId) {
        return repo.entryExists(bookId);
    }

}
