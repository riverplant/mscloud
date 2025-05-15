package com.riverplant.security.webflux.controller;

import com.riverplant.security.webflux.entity.Author;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/author")
public class AuthorController {


    @GetMapping
    public Flux<Author> getAllAuthor() {

        return null;
    }
}
