package com.github.juli220620.controller;

import com.github.juli220620.aspect.DoCashing;
import com.github.juli220620.model.BookDto;
import com.github.juli220620.service.BookCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookCrudService service;

    @PostMapping
    public void addBook(@RequestBody BookDto dto) {
        service.addBook(dto);
    }

    @GetMapping
    public List<BookDto> listAll() {
        return service.listAll();
    }

    @DoCashing
    @PutMapping
    public BookDto updateBook(@RequestBody BookDto editedDto) {
        return service.editBook(editedDto);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        service.deleteBook(id);
    }
}
