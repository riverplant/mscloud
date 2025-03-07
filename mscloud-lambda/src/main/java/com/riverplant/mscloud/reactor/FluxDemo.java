package com.riverplant.mscloud.reactor;

import java.time.Duration;

import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

public class FluxDemo {

	public static void main(String[] args) throws InterruptedException {
		// Mono: 0|1個元素
		// Flux: N個元素
		//發佈者發佈數據流：源頭
		//1.獲得一個多元素的流 doOnXXX:事件感知,当流发生什么事情的时候，触发一个回调(Hook[钩子函数])
		Flux<Integer> just = Flux.just(1,2,3,4,5)
				.delayElements(Duration.ofSeconds(1))
				.doOnComplete(()->
				     System.out.println("complete......"))
				.doOnCancel(()->
				     System.out.println("cancel......"))
				.doOnError(onError-> 
				     System.out.println("流出错..." + onError))
				.doOnNext(integer->
				     System.out.println("doOnNext..." + integer));
		
		just.subscribe(System.out::print); //只有消费流，才能触发所有事件
		
		
		just.subscribe(new BaseSubscriber<Integer>() {

			
			@Override
			protected void hookOnSubscribe(Subscription subscription) {
				System.out.println("订阅者和发布者绑好了:" + subscription);
				request(1);
			}

			@Override
			protected void hookOnNext(Integer value) {
				System.out.println("元素到达:" + value);
				if(value < 5) {
					request(1);
				}else {
					cancel();
				}
				
			}

			@Override
			protected void hookOnComplete() {
				System.out.println("元素流结束");
			}

			@Override
			protected void hookOnError(Throwable throwable) {
				System.out.println("元素流异常");
			}

			@Override
			protected void hookOnCancel() {
				System.out.println("元素流被取消");
			}

			/**
			 * 相当于finally,无论如何都会被执行到
			 */
			@Override
			protected void hookFinally(SignalType type) {
				// TODO Auto-generated method stub
				super.hookFinally(type);
			}
			
			
			
		});
		
//		Mono<Integer> number = Mono.just(1);
//		
//        // 消費流就是訂閲
//		just.subscribe(e->System.out.print("e=" + e));
//		//流可以重複使用
//		just.subscribe(e->System.out.print("e2=" + e));
//		
//		//對於每個消費者來説流是一樣的，廣播模式
//		//Flux.interval 會生成一個無限流,take會只取5個數字
//		Flux<Long> flux = Flux.interval(Duration.ofSeconds(1)).take(5);// 每秒產生一個遞增數字
//		
//		flux.subscribe(System.out::print);
//		
//		Flux<Integer> fluxRanger = Flux.range(10, 5);
//		fluxRanger.subscribe(System.out::println);

		
		//Thread.currentThread().join();
	}

}
