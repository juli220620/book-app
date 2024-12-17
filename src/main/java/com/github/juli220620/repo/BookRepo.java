package com.github.juli220620.repo;

import com.github.juli220620.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<BookEntity, Long>, ParametrizedBookSearchRepo {

    @Query("select e.imageId from BookEntity e where e.id = :bookId")
    String getImageId(@Param("bookId") Long bookId);
}
