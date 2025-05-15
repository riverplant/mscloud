package com.riverplant.security.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.io.IOException;

@SpringBootApplication
@EnableR2dbcRepositories
public class WebfluxApplication {
    public static void main(String[] args) throws IOException {

       SpringApplication.run(WebfluxApplication.class, args);

/*
        //1. 創建一個能處理http請求的處理器
        HttpHandler httpHandler = (ServerHttpRequest request,
                                   ServerHttpResponse response) -> {
            URI uri = request.getURI();
            System.out.println("當前請求地址: " +uri);
            // 編寫請求處理的業務
            //給瀏覽器寫一個内容URL + "Hello~!"
            //數據的發佈者: Mono<DataBuffer>, Flux<DataBuffer>

            // 創建餉應數據的DataBuffer,作爲數據的緩衝區。response將數據放入 DataBuffer
            //然後一次性提交給web
            DataBufferFactory dataBufferFactory = response.bufferFactory();

            // 獲得數據buffer
            DataBuffer wrap = dataBufferFactory.wrap((uri.toString() + " Hello~!").getBytes());
            //通過發佈者將數據發佈
            return response.writeWith(Mono.just(wrap));

        };

        //2. 啓動一個服務器，監聽8080端口，接收數據，拿到數據交給HttpHandler進行請求處理
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);


        //3. 啓動Netty服務器
        HttpServer.create()
                .host("localhost")
                .port(8080)
                .handle(adapter)
                .bindNow();


        System.out.println("服務器啓動完成.....監聽8080端口, 接收請求");
        System.in.read();
        System.out.println("服務器停止.....");

 **/

    }
}