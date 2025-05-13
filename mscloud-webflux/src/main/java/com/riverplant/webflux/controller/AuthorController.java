package com.riverplant.webflux.controller;

import com.riverplant.webflux.entity.Author;
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
