package com.github.juli220620.controller;

import com.github.juli220620.model.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient(value = "book-service")
public interface BookServiceController {

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    Optional<BookDto> getBookById(@PathVariable Long id);

    @RequestMapping(method = RequestMethod.GET, value = "/image/{bookId}")
    Optional<String> getImageId(@PathVariable Long bookId);

    @RequestMapping(method = RequestMethod.PUT, value = "/save")
    void saveBook(BookDto bookDto);

    @RequestMapping(method = RequestMethod.GET, value = "/exists/{bookId}")
    boolean entryExists(@PathVariable Long bookId);
}
