package com.github.juli220620.image;

import com.github.juli220620.model.BookEntity;
import com.github.juli220620.repo.BookRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations operations;
    private final BookRepo bookRepo;

    public void saveImage(Long bookId,
                            MultipartFile image,
                            String filename,
                            String contentType) {
        try {
            var bookEntity = bookRepo.findById(bookId).orElseThrow(() -> new RuntimeException("No such book found"));
            bookEntity.setImageId(
                    gridFsTemplate.store(
                            image.getInputStream(),
                            filename,
                            contentType)
                            .toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public GridFsResource findImageByBookId(Long bookId) {
        var imageId = bookRepo.findById(bookId)
                .map(BookEntity::getImageId)
                .orElse("");
        return findImage(imageId);
    }

    public GridFsResource findImage(String id) {
        var gridFsFile = Optional.ofNullable(gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id))));
        return gridFsFile.map(operations::getResource).orElse(null);
    }

}
