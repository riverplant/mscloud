package com.riverplant.security.webflux.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class MyWebFilter implements WebFilter {
    @Override
    public Mono<Void> filter( ServerWebExchange exchange,  WebFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        System.out.println("請求處理放行到目標方法之前");

        Mono<Void> filter = chain
                .filter(exchange) // 放行
                .doOnError(err -> {
                    System.out.println("目標方法異常之後...");
                })//目標發生異常以後.....
                .doFinally(signalType -> {
                    System.out.println("目標方法執行之後...");
                });


        System.out.println("Mono是異步執行，所以主綫程執行到這一句并不代表Mono中的過濾器已經執行完成");

        return filter;
    }
}
