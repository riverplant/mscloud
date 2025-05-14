package com.riverplant.webflux.entity;

import lombok.Data;


import java.time.LocalDateTime;

@Data
public class BookWithAuthorDto {
    private Long id;
    private String title;
    private Long authorId;
    private String authorName; // 来自联表查询
    private LocalDateTime publishTime;



}
