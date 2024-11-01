package com.github.juli220620.repo;

import com.github.juli220620.model.BookEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParametrizedBookSearchRepo {
    List<BookEntity> findAllByAuthor(String author);
    List<BookEntity> findByDescriptionPart(String descriptionPart);
    List<BookEntity> findByName(String name);
}
