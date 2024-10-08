package com.github.juli220620.controller;

import com.github.juli220620.localization.LocalizedMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @LocalizedMessage
    @GetMapping("/{key}")
    public String getMessage(@PathVariable String key) {
        return key;
    }
}
