package com.github.juli220620.controller;

import com.github.juli220620.model.BookDto;
import com.github.juli220620.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class CrudController {

    private final CrudService service;

    @PutMapping("/add")
    public void addBook(@RequestBody BookDto dto) {
        service.addBook(dto);
    }

    @GetMapping
    public List<BookDto> listAll() {
        return service.listAll();
    }

    @PutMapping("/edit")
    public void editBook(@RequestBody BookDto editedDto) {
        service.editBook(editedDto);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        service.deleteBook(id);
    }
}
