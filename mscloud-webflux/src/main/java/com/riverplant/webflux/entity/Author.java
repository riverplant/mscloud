package com.riverplant.webflux.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("authors")
@Data
@NoArgsConstructor
public class Author {

    @Id
    private Long id;
    private String name;

    @Transient
    private List<Book> books;


    public Author(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
