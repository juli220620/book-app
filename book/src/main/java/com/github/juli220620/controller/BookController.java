package com.github.juli220620.controller;

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

    @GetMapping(params = "page")
    public PagedBookRs pageAll(@RequestParam("page") int pageNumber) {
        var res = service.pageAll(pageNumber);
        return new PagedBookRs(pageNumber + 1, res.getTotalPages(), res.getContent());
    }

    @PutMapping
    public BookDto updateBook(@RequestBody BookDto editedDto) {
        return service.editBook(editedDto);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        service.deleteBook(id);
    }

    @GetMapping("/{id}")
    public BookDto findById(@PathVariable Long id) {
        return service.findById(id);
    }
}
