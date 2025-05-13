package com.riverplant.webflux.converter;

import com.riverplant.webflux.entity.Author;
import com.riverplant.webflux.entity.Book;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.Instant;
import java.time.LocalDateTime;

@ReadingConverter // ReadingConverter读取数据库数据的时候,把row转成Book
public class BookConverter implements Converter<Row, Book> {
    @Override
    public Book convert(Row source) {
        Book book = new Book();

        book.setId(source.get("id", Long.class));
        book.setTitle( source.get("title", String.class));
        Long authorId = source.get("author_id", Long.class);
        book.setAuthorId(authorId);
        book.setPublishTime(source.get("publish_time", LocalDateTime.class));
        String name = source.get("name", String.class);
        book.setAuthor(new Author(authorId, name));

        return book;
    }
}
