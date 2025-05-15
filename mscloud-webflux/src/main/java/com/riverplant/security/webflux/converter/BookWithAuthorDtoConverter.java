package com.riverplant.security.webflux.converter;

import com.riverplant.security.webflux.entity.BookWithAuthorDto;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.LocalDateTime;

@ReadingConverter // ReadingConverter读取数据库数据的时候,把row转成Book
public class BookWithAuthorDtoConverter implements Converter<Row, BookWithAuthorDto> {
    @Override
    public BookWithAuthorDto convert(Row source) {
        BookWithAuthorDto dto = new BookWithAuthorDto();
        dto.setId(source.get("id", Long.class));
        dto.setTitle(source.get("title", String.class));
        dto.setAuthorId(source.get("author_id", Long.class));
        dto.setPublishTime(source.get("publish_time", LocalDateTime.class));
        dto.setAuthorName(source.get("name", String.class)); // join authors.name as name
        return dto;
    }
}
