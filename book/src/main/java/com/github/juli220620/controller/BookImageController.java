package com.github.juli220620.controller;

import com.github.juli220620.model.BookImageDto;
import com.github.juli220620.service.BookImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/book-service")
@RequiredArgsConstructor
public class BookImageController {

    private final BookImageService service;

    @GetMapping("/book/{id}")
    public Optional<BookImageDto> getBookById(@PathVariable Long id) {
        return service.findImageIdById(id);
    }

    @GetMapping("/image/{bookId}")
    public Optional<String> getImageId(@PathVariable Long bookId) {
        return service.getImageId(bookId);
    }

    @PutMapping("/book/save")
    public void save(@RequestBody BookImageDto dto) {
        service.save(dto);
    }

    @GetMapping("/book/exists/{id}")
    public boolean entryExist(@PathVariable Long id) {
        return service.entryExists(id);
    }

}
