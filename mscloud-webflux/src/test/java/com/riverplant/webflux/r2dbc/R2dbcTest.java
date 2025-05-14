package com.riverplant.webflux.r2dbc;

import com.riverplant.webflux.entity.Author;

import com.riverplant.webflux.entity.AuthorWithBooks;
import com.riverplant.webflux.entity.Book;
import com.riverplant.webflux.repository.AuthorRepository;
import com.riverplant.webflux.repository.BookRepository;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * 1.  R2dbcAutoConfiguration ： 主要配置連接工廠，連接池
 * 2.  R2dbcDataAutoConfiguration：
 * R2dbcEntityTemplate： 操作數據庫的響應式客戶端(CRUD API)
 * 數據類型映射關係，轉換器、自定義R2dbcCustomConversions 轉換器組件
 * 數據庫類型  對應 Java數據類型轉換
 * 3.  R2dbcRepositoriesAutoConfiguration: 開啓Spring Data 開啓聲明式接口方式的CRUD
 * 4.  R2dbcTransactionManagerAutoConfiguration
 */
@SpringBootTest
public class R2dbcTest {
    //1. r2dbc 在數據庫層面天然支持高并發、高吞吐量
    //2. 并不能提升開發效率
    @Test
    void connection() {
        // r2dbc基於全異步、響應式、消息驅動
        //1. 獲取連接工廠
        ConnectionFactory connectionFactory = ConnectionFactories.get("r2dbc:h2:mem:///testdb");

        //2. 獲取到連接，發送sql
        Mono.from(connectionFactory.create())
                .flatMapMany(conn ->
                        // 使用 Mono.from(...) 包裝 execute()
                        Mono.from(conn.createStatement("CREATE TABLE IF NOT EXISTS person (id BIGINT  PRIMARY KEY, name VARCHAR(100), age INT);").execute())
                                .thenMany(
                                        Flux.concat(
                                                Mono.from(conn.createStatement("INSERT INTO person VALUES (1, 'Alice', 30);").execute()),
                                                Mono.from(conn.createStatement("INSERT INTO person VALUES (2, 'Bob', 45);").execute())
                                        )
                                )
                                .thenMany(conn.createStatement("SELECT id, name FROM person WHERE age > $1")
                                        .bind("$1", 40)
                                        .execute())

                ).flatMap(result -> result.map(readable -> {
                            Long id = readable.get("id", Long.class);
                            String name = readable.get("name", String.class);
                            return new Author(id,name);
                        })

                )
                .subscribe(author -> System.out.println("author = " + author))
        ;

    }


    //Spring Data 提供的兩個核心底層組件
    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate; //CRUD API

    @Autowired
    DatabaseClient databaseClient; // 數據庫客戶端

    @Test
    void r2dbcEntityTemplate() {
        //Query對象  Query By Criteria: QBC
        //1. 代表查詢條件 where id = 1 and name = zhang san
        Criteria criteria = Criteria.empty();
        criteria.and("id").is(1L)
                .and("name").is("zhang san");

        //2. 封裝為查詢對象
        Query query = Query.query(criteria);

        /*
         * 在使用 Spring R2DBC 的 R2dbcEntityTemplate 查詢時，
         * 雖然你沒有明確寫 FROM 哪張表，
         * 但它是根據你提供的實體類別 (Author.class) 來決定資料 表的
         */
        r2dbcEntityTemplate.select(query, Author.class)
                .doOnNext(aAuther -> System.out.println("aAuther => " + aAuther))
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();


    }


    @Test
        //非同步操作，测试不会等待flux運行完成
        // 貼近底層，join操作好做
    void databaseClient() {
        databaseClient.sql("select * from authors where id= :id")
                .bind("id", 1L)
                .fetch() //抓取數據，獲取FetchSpec<Map<String, Object>>
                .all()// // 回傳 Flux<Map<String, Object>>
                .map(row -> {
                    Long id = ((Number) row.get("id")).longValue();
                    String name = (String) row.get("name");
                    return new Author( id, name );
                })
                .subscribe(author -> System.out.println("author ===>" + author));

    }

    @Test
// 使用 StepVerifier（推薦）, 讓測試等待 Flux 結束
    void databaseClient2() {
        StepVerifier.create(
                        databaseClient.sql("select * from authors where id= :id")
                                .bind("id", 1L)
                                .fetch() //抓取數據，獲取FetchSpec<Map<String, Object>>
                                .all()// // 回傳 Flux<Map<String, Object>>
                                .map(
                                        row -> {
                                            System.out.println("row = " + row);
                                            Long id = ((Number) row.get("id")).longValue();
                                            String name = (String) row.get("name");
                                            return new Author(id, name);
                                        }))
                .assertNext(author -> {
                    assertNotNull(author.getName(), "Author name is null");
                    assertEquals("zhang san", author.getName());
                })
                .verifyComplete();
    }

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;


    @Test
    void authorRepository() {
        StepVerifier.create(
                        authorRepository.findById(1L)
                      )
                .expectNextMatches(author -> author.getName().equals("zhang san"))
                .verifyComplete();
    }


    @Test
    void findAllByCustome() {
        StepVerifier.create(
                        authorRepository.findAllByCustome().doOnNext(aAuther -> System.out.println("aAuther => " + aAuther))

                )
                .expectNextCount(1)
                .verifyComplete();
    }


    @Test
    void bookRepositoryFindByIdInAndTileLike() {
        StepVerifier.create(
                        bookRepository.findAllByIdInAndTitleLike(List.of(1L), "Reacti%")
                )
                .expectNextMatches(booke -> booke.getTitle().equals("Reactive Spring"))
                .verifyComplete();
    }


    @Test
    void findBookAndAhthor() {
        bookRepository.findBookAndAhthor(1L)
                .doOnNext(book-> System.out.println("bool==> " + book))
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void findAhthorWithBooks() {
        Mono<Author> authorMono = authorRepository.findById(1L);
        Mono<List<Book>> booksMono  = bookRepository.findByAuthorId(1L).collectList();

        Mono<AuthorWithBooks> result = authorMono.zipWith(booksMono, AuthorWithBooks::new);

        result.subscribe(System.out::println);

        StepVerifier.create( result )
                .assertNext(authorWithBooks -> {
                   assertThat(authorWithBooks.getAuthor()).isNotNull();
                })
                .verifyComplete();

    }

}
