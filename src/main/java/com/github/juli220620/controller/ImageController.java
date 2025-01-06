package com.github.juli220620.controller;

import com.github.juli220620.facade.ImageFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/book/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageFacade imageFacade;

    @PutMapping
    public void uploadImage(@RequestParam(name = "filename") String filename,
                            @RequestParam(name = "image") MultipartFile file,
                            @RequestParam(name = "bookId") Long bookId) {
        imageFacade.uploadImage(bookId, filename, file);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<InputStreamResource> downloadImage(@PathVariable Long bookId) {
        return imageFacade.downloadImage(bookId);
    }
}
