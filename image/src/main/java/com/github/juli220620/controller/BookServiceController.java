package com.github.juli220620.controller;

import com.github.juli220620.model.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "book-service", fallback = BookServiceController.BookServiceFallback.class)
public interface BookServiceController {

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    BookDto getBookById(@PathVariable Long id);

    @RequestMapping(method = RequestMethod.GET, value = "/image/{bookId}")
    String getImageId(@PathVariable Long bookId);

    @RequestMapping(method = RequestMethod.PUT, value = "/save")
    void saveBook(BookDto bookDto);

    @RequestMapping(method = RequestMethod.GET, value = "/exists/{bookId}")
    boolean entryExists(@PathVariable Long bookId);

    @Component
    class BookServiceFallback implements BookServiceController {

        @Override
        public BookDto getBookById(Long id) {
            return null;
        }

        @Override
        public String getImageId(Long bookId) {
            return null;
        }

        @Override
        public void saveBook(BookDto bookDto) {
            throw new UnsupportedOperationException("A problem occurred. Please try again later");
        }

        @Override
        public boolean entryExists(Long bookId) {
            return false;
        }
    }
}
