package com.github.juli220620.controller;

import com.github.juli220620.model.BookDto;
import com.github.juli220620.service.BookSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book/search")
public class ParametrizedSearchBookController {

    private final BookSearchService service;

    @GetMapping("/author")
    public List<BookDto> findAllByAuthor(@RequestBody String author) {
        return service.findAllByAuthor(author);
    }

    @GetMapping("/descr")
    public List<BookDto> findByDescriptionPart(@RequestBody String descriptionPart) {
        return service.findByDescriptionPart(descriptionPart);
    }

    @GetMapping("/name")
    public List<BookDto> findByName(@RequestBody String name) {
        return service.findByName(name);
    }
}
