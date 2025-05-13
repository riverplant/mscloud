package com.riverplant.webflux.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDateTime;

@Table("book")
@Data
public class Book {

    @Id
    private Long id;
    private String title;
    private Long authorId;
    private LocalDateTime publishTime; //响应式中日期的映射用Instant 或者 LocalXxx

    @Transient
    private Author author;// 每一本书对应一个唯一的作者

}
