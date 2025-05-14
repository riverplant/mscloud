package com.riverplant.webflux.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class BookWithAuthorDto {
    private Long id;
    private String title;
    private Long authorId;
    private String authorName; // 来自联表查询
    private LocalDateTime publishTime;


    @Override
    public String toString() {
        return "BookWithAuthorDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                ", publishTime=" + publishTime +
                '}';
    }
}
