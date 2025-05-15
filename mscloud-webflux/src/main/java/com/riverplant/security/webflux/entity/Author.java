package com.riverplant.security.webflux.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Table("authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    private Long id;
    private String name;

}
