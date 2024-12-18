package com.github.juli220620.controller;

import com.github.juli220620.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@RestController
@RequestMapping("/api/book/image")
@RequiredArgsConstructor
public class ImageController {

    @Value("${app.placeholder.path}")
    private String placeholderImagePath;
    private final ImageService imageService;

    @PutMapping
    public void uploadImage(@RequestParam(name = "filename") String filename,
                            @RequestParam(name = "image") MultipartFile file,
                            @RequestParam(name = "bookId") Long bookId) {
        imageService.saveImage(bookId, file, filename, file.getContentType());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<InputStreamResource> downloadImage(@PathVariable Long bookId) {
        var resource = imageService.findImageByBookId(bookId);

        if (resource == null) return downloadPlaceholderImage();

        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(resource.getContentType()))
                    .contentLength(resource.contentLength())
                    .header(ContentDisposition.builder("attachment")
                            .filename(resource.getFilename(), StandardCharsets.UTF_8)
                            .build().toString())
                    .body(new InputStreamResource(resource.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseEntity<InputStreamResource> downloadPlaceholderImage() {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(Files.probeContentType(Path.of(placeholderImagePath))))
                    .header(ContentDisposition.builder("attachment")
                            .filename(Path.of(placeholderImagePath).getFileName().toString(), StandardCharsets.UTF_8)
                            .build().toString())
                    .body(new InputStreamResource(new FileInputStream(
                            Objects.requireNonNull(
                                    getClass().getClassLoader()
                                            .getResource(placeholderImagePath))
                                    .toURI().toURL().getFile())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
