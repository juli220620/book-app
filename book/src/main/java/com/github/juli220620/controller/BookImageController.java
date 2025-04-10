package com.github.juli220620.controller;

import com.github.juli220620.model.BookImageDto;
import com.github.juli220620.service.BookImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookImageController {

    private final BookImageService service;

    @GetMapping("/{id}")
    public BookImageDto getBookById(@PathVariable Long id) {
        return service.findImageIdById(id).orElse(null);
    }

    @GetMapping("/image/{bookId}")
    public String getImageId(@PathVariable Long bookId) {
        return service.getImageId(bookId).orElse(null);
    }

    @PutMapping("/save")
    public void save(@RequestBody BookImageDto dto) {
        service.save(dto);
    }

    @GetMapping("/exists/{id}")
    public boolean entryExist(@PathVariable Long id) {
        return service.entryExists(id);
    }

}
