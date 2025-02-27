package com.riverplant.mscloud.reactor;

import java.time.Duration;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxDemo {

	public static void main(String[] args) throws InterruptedException {
		// Mono: 0|1個元素
		// Flux: N個元素
		//發佈者發佈數據流：源頭
		//1.獲得一個多元素的流
		Flux<Integer> just = Flux.just(1,2,3,4,5);
		
		Mono<Integer> number = Mono.just(1);
		
        // 消費流就是訂閲
		just.subscribe(e->System.out.print("e=" + e));
		//流可以重複使用
		just.subscribe(e->System.out.print("e2=" + e));
		
		//對於每個消費者來説流是一樣的，廣播模式
		
		Flux<Long> flux = Flux.interval(Duration.ofSeconds(1));// 每秒產生一個遞增數字
		
		flux.subscribe(System.out::print);
		
		Thread.currentThread().join();
	}

}
