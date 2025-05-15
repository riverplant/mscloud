package com.riverplant.security.webflux.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("book")
@Data
public class Book {

    @Id
    private Long id;
    private String title;
    @Column("author_id")
    private Long authorId;
    private LocalDateTime publishTime; //响应式中日期的映射用Instant 或者 LocalXxx

}
