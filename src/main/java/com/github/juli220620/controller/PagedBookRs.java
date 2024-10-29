package com.github.juli220620.controller;

import com.github.juli220620.model.BookDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PagedBookRs {

    private int currentPage;
    private int totalPages;
    private List<BookDto> content;
}
