package com.riverplant.mscloud.reactor.flow;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 響應式編程: 全異步、消息、事件回調
 */
public class ThreadMain {


    /**
     * 默認使用當前綫程生成整個流的發佈，中間操作
     * Schedulers工具類來自定義綫程
     */
    public void thread() {

       /** Flux.range(1, 10)
                // publishOn: 改變發佈者的綫程，設置在哪個綫程池中運行
                .publishOn(Schedulers.immediate()) // Schedulers.immediate()設置在當前綫程中執行，main綫程
                .publishOn(Schedulers.single()) // Schedulers.single()專門啓用一個新綫程

                //自定義處理流中的數據
                .handle((value, sink) -> {
                    System.out.println("獲取的值: " + value);
                    //通過值創建一個對象
                    User user = new User(UUID.randomUUID().toString(), value);
                    //將user發送給下端的流
                    sink.next(user);

                })//傳入兩個數據(value, sink)
                .log()
                // subscribeOn: 改變訂閱者的綫程，設置在哪個綫程池中運行
                .subscribeOn(Schedulers.immediate())
        //.buffer(3)// buffer为数据添加一个缓冲区,缓存3个元素!!!!!!!消费者一次最多可以拿到3个元素
        //.subscribe(); // 75% 75预取策略limitRate(100)
        ;
**/
        //調度器: 綫程池
        //Schedulers.boundedElastic();//有界、彈性調度;有邊界，不是無限擴充的綫程池 綫程池中有10*CPU核心個綫程，隊列默認100K

        //Schedulers.immediate();//immediate當前綫程執行所有操作，默認

        // Schedulers.single();// 使用固定的單綫程
        /**
         * public ThreadPoolExecutor(int corePoolSize,
         *                               int maximumPoolSize,
         *                               long keepAliveTime,
         *                               TimeUnit unit,
         *                               BlockingQueue<Runnable> workQueue)
         */
        Schedulers.fromExecutor(new ThreadPoolExecutor(4,
                8,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000))); //自定義要給綫程池

        //只要不指定綫程池，默認發佈者使用的綫程就是訂閱者的綫程。因爲只有訂閲了才會動!!!!!!!!!!!!!!!

        Scheduler s = Schedulers.newParallel("parallel-scheduler",4);
        final Flux<String> flux = Flux
                .range(1, 10)
                .map(i-> 10 + i)
                .log()// 此時因爲沒有指定發佈者綫程池，所以發佈者使用與消費者一樣的綫程
                .publishOn(s) // 此時指定了腹部這使用的綫程池
                .map(i -> "value " + i)// 此時因爲指定了發佈者綫程池，所以發佈者使用自己指定的綫程池parallel-scheduler
                .log();

        new Thread( ()->flux.subscribe(System.out::println), "subscribe").start(); // start在消費者中定義了消費綫程

    }

    public static void main(String[] args) {
        new ThreadMain().thread();
    }

}


