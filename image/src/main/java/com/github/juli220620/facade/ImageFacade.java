package com.github.juli220620.facade;

import com.github.juli220620.controller.BookServiceController;
import com.github.juli220620.service.ImageService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageFacade {
    public static final String NO_SUCH_BOOK_EXISTS_MESSAGE = "No such book exists";
    @Value("${app.placeholder.path}")
    private String placeholderImagePath;

    private final ImageService imageService;
    private final BookServiceController bookService;

    public void uploadImage(Long bookId, String filename, MultipartFile image) {
        if (image == null || StringUtils.isBlank(filename) || bookId == null)
            throw new IllegalArgumentException("Not enough data provided");

        var contentType = image.getContentType();

        if (!imageService.validContentTypes.contains(contentType))
            throw new IllegalArgumentException("Invalid content type");

        if (!bookService.entryExists(bookId)) throw new NoSuchElementException(NO_SUCH_BOOK_EXISTS_MESSAGE);

        imageService.saveImage(bookId, image, filename, contentType);
    }

    @SneakyThrows
    public ResponseEntity<InputStreamResource> downloadImage(Long bookId) {
        if (!bookService.entryExists(bookId)) throw new NoSuchElementException(NO_SUCH_BOOK_EXISTS_MESSAGE);

        var resource = imageService.findImageByBookId(bookId);

        return resource == null ? downloadPlaceholderImage() : getResponse(
                MediaType.parseMediaType(resource.getContentType()),
                resource.contentLength(),
                resource.getFilename(),
                new InputStreamResource(resource.getInputStream())
        );
    }

    @SneakyThrows
    private ResponseEntity<InputStreamResource> downloadPlaceholderImage() {
        var path = Path.of(placeholderImagePath);
        return getResponse(
                MediaType.parseMediaType(Files.probeContentType(path)),
                Files.size(path),
                path.getFileName().toString(),
                getDefaultImageData()
        );
    }

    @SneakyThrows
    private ResponseEntity<InputStreamResource> getResponse(
            MediaType type,
            Long length,
            String filename,
            InputStreamResource body) {
        return ResponseEntity.ok()
                .contentType(type)
                .contentLength(length)
                .header(ContentDisposition.builder("attachment")
                        .filename(filename, StandardCharsets.UTF_8)
                        .build().toString())
                .body(body);
    }

    @SneakyThrows
    private InputStreamResource getDefaultImageData() {
        return new InputStreamResource(
                new FileInputStream(
                        Path.of(placeholderImagePath).toFile()
                )
        );
    }
}
