package com.github.juli220620.service;

import com.github.juli220620.controller.BookServiceController;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    @Getter
    @Value("#{${app.content.type.values}}")
    private List<String> validContentTypes;

    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations operations;
    private final BookServiceController bookService;

    public void saveImage(Long bookId,
                          MultipartFile image,
                          String filename,
                          String contentType) {
        try {
            var book = Optional.ofNullable(bookService.getBookById(bookId)).orElseThrow();

            var imageId = book.getImageId();
            if (imageId != null) {
                gridFsTemplate.delete(new Query(Criteria.where("_id").is(imageId)));
            }

            book.setImageId(
                    gridFsTemplate.store(image.getInputStream(), filename, contentType).toString());
            bookService.saveBook(book);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public GridFsResource findImageByBookId(Long bookId) {
        return Optional.ofNullable(bookService.getImageId(bookId))
                .map(this::findImage).orElse(null);
    }

    public GridFsResource findImage(String id) {
        var gridFsFile = Optional.ofNullable(gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id))));
        return gridFsFile.map(operations::getResource).orElse(null);
    }
}
