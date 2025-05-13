package com.riverplant.webflux.controller;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/webflux")
public class HelloController {

    /**
     *
     * @param key
     * @param exchange  //ServerWebExchange 中封裝了ServerHttpRequest和 ServerHttpResponse
     * @param webSession: 替代以前的HttpSession, 可以獲得session
     * @return
     */
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "key", required = false, defaultValue = "haha") String key,
                        ServerWebExchange exchange,
                        WebSession webSession,
                        HttpMethod httpMethod) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        httpMethod.name();//put|get|post...

        webSession.getAttributes().put("key", "value");

        return "Hello";
    }

    //1. 返回單個數據Mono: Order, User, String,Map
    //2. 返回多個數據Flux
    @GetMapping("/haha")
    public Mono<String> haha(@RequestParam(value = "key", required = false, defaultValue = "haha") String key) {

        return Mono.just(key);
    }


    //1. 返回單個數據Mono: Order, User, String,Map
    //2. 返回多個數據Flux
    //3. 配合Flux, 完成SSE: 服務端事件推送
    @GetMapping("/flux")
    public Flux<String> flux() {

        return Flux.just("data1", "data2","data3");
    }


    //sse: 調用該接口會每秒生成一個數據形成一個數據流!!!!!!!
    //Chatgpt: 服務端推送
    @GetMapping(value= "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)// 設置返回一個數據流!!!!!
    public Flux<ServerSentEvent<String>> sse() {

        return Flux.range(1,10)
                .map(i->{
                    // 構建一個SSE對象
                  return  ServerSentEvent.builder("ha-" + i)
                            .id(i+"")
                            .comment("hei-" + i)
                            .event("haha")
                            .build();
                })
                .delayElements(Duration.ofMillis(500));

    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
       return  ResponseEntity.status(305)
               .header("aaa","bbb")
               .contentType(MediaType.APPLICATION_JSON)
               .body("haha");
    }


    //RestController無法完成跳轉

    /**
     *
     * @return Rendering: 新方法的頁面跳轉
     */
    @GetMapping("/render")
    public Rendering render() {
        //return Rendering.redirectTo("http://www.baidu.com").build();
        return Rendering.redirectTo("/aaa").build(); // 重定向到當前項目根路徑下的aaa
    }
}
