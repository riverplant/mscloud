package com.riverplant.security.mscloud.reactor;

import java.io.IOException;
import java.time.Duration;

import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;
/**
 * Mono:  0|1
 * Flux:  有很多
 * doOnXxx: 事件感知，当流发生什么事的时候，触发一个回调，系统调用提前定义好的(Hook[钩子函数])
 * doOnComplete().......
 */
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

		Flux.range(1, 7)
				.log() // 日志
				.filter(i->i>3)
				.map(i->"haha-" + i)
				.log() // 日志
				.subscribe(System.out::println);

		//System.in.read();
	}

	/**doOnNext: 每一个流的数据到达的时候触发
	 * doOnEach: 每个元素(流的数据和信号)到达的时候触发
	 * doOnRequest: 消费者请求流元素的时候
	 * doOnError: 流发生错误
	 * doOnSubscribe: 流被订阅的时候
	 * doOnTerminate: 发生取消/异常信号终端了流
	 * doOnCancel: 流被取消
	 * doOnDiscard: 流中元素被忽略的时候(经过过滤
	 * SignalType:
	 *     SUBSCRIBE: 被订阅
	 *     REQUEST： 请求了N个元素
	 *     CANCEL：  流被取消了
	 *     ON_SUBSCRIBE: 在订阅的时候
	 *     ON_NEXT: 元素到达的时候
	 *     ON_ERROR: 流错误
	 *     ON_COMPLETE: 流正常完成
	 *     AFTER_TERMINATE: 流被中断以后
	 *     CURRENT_CONTEXT: 当前上下文
	 *     ON_CONTEXT:  感知上下文
     */
	public static void doOn() throws IOException {
		// 创建数据
		Flux<Integer> flux = Flux.range(1, 7)
				.delayElements(Duration.ofSeconds(1)) // 每一个元素延迟一秒发送
				.doOnNext(integer -> {
					System.out.println("doOnNext....." + integer );
				})
				//doOnEach 返回的对象比doOnNext包含更多的信息
				.doOnEach(signal -> System.out.println("doOnEach....." + signal.get()))
				.doOnComplete(() -> {
					System.out.println("流正常结束.....");
				})
				.doOnCancel(() -> {
					System.out.println("流被取消.....");
				})
				.doOnError(throwable-> {
					System.out.println("流出错....." + throwable );
				})

				;

		flux.subscribe(new BaseSubscriber<Integer>() {

			@Override
			protected void hookOnSubscribe(Subscription subscription) {
				System.out.println("发布者和订阅者绑定好了" + subscription );
				request(1);
			}

			@Override
			protected void hookOnNext(Integer value) {
				System.out.println("元素到达" + value );
				if(value < 5) {
					request(1);
				}else {
					cancel();// 取消订阅
				}

			}

			@Override
			protected void hookOnComplete() {
				System.out.println("数据流结束");
			}

			@Override
			protected void hookOnError(Throwable throwable) {
				System.out.println("数据流错误: " + throwable);
			}

			@Override
			protected void hookOnCancel() {
				System.out.println("数据流被取消");
			}

			@Override
			protected void hookFinally(SignalType type) {
				System.out.println("数据流结束, 结束信号: " + type);
			}

		});
	}

}
