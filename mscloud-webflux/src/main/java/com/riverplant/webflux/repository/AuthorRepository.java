package com.riverplant.webflux.repository;

import com.riverplant.webflux.entity.Author;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Collection;

//Spring Data R2DBC 不解析 JPQL，只能執行純 SQL!!!!!
@Repository
public interface AuthorRepository extends R2dbcRepository<Author, Long> {

    //QBC: Query by Criteria
    //QBE: Query by Example

    Flux<Author> findAllByIdInAndNameLike(Collection<Long> ids, String name);

    //Spring Data R2DBC 不解析 JPQL，只能執行純 SQL!!!!!
    @Query("select * from  authors")
    Flux<Author> findAllByCustome();

    //1. 一本书有一个作者
    //2. 一个作者有很多图书


}
