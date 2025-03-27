package com.github.juli220620.service;

import com.github.juli220620.controller.BookServiceController;
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

    @Value("#{${app.content.type.values}}")
    public List<String> validContentTypes;

    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations operations;
    private final BookServiceController bookService;

    public void saveImage(Long bookId,
                          MultipartFile image,
                          String filename,
                          String contentType) {
        try {
            var bookEntity = bookService.getBookById(bookId).orElseThrow();

            if (bookEntity.getImageId() != null) {
                gridFsTemplate.delete(new Query(Criteria.where("_id").is(bookEntity.getImageId())));
            }
            bookEntity.setImageId(
                    gridFsTemplate.store(image.getInputStream(), filename, contentType).toString());
            bookService.saveBook(bookEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public GridFsResource findImageByBookId(Long bookId) {
        return bookService.getImageId(bookId)
                .map(this::findImage).orElse(null);
    }

    public GridFsResource findImage(String id) {
        var gridFsFile = Optional.ofNullable(gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id))));
        return gridFsFile.map(operations::getResource).orElse(null);
    }

}
