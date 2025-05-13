package com.riverplant.webflux.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("authors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @Id
    private Long id;
    private String name;





}
