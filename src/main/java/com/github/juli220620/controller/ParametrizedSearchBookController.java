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

    @GetMapping("/{author}")
    public List<BookDto> findAllByAuthor(@PathVariable String author) {
        return service.findAllByAuthor(author);
    }

    @GetMapping("/descr")
    public List<BookDto> findByDescriptionPart(@RequestBody String descriptionPart) {
        return service.findByDescriptionPart(descriptionPart);
    }
}
