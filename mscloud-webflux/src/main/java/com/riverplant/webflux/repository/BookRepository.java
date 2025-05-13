package com.riverplant.webflux.repository;

import com.riverplant.webflux.entity.Book;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

//Spring Data R2DBC 不解析 JPQL，只能執行純 SQL!!!!!
@Repository
public interface BookRepository extends R2dbcRepository<Book, Long> {

    //QBC: Query by Criteria
    //QBE: Query by Example

    Flux<Book> findAllByIdInAndTitleLike(Collection<Long> ids, String name);


    //1. 一本书有一个作者
    //2. 一个作者有很多图书



    @Query("select b.*, t.name as name from book b left join authors t on b.author_id = t.id WHERE b.id = ?")
    Mono<Book> findBookAndAhthor(Long bookId);
}
