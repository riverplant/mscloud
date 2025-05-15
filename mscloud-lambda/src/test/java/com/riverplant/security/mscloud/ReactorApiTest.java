package com.riverplant.security.mscloud;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * Reactor API test
 */
public class ReactorApiTest {
    //1. filter
    @Test
    void filter() {

        Flux.just(1, 2, 3, 4)
                .filter(i -> i % 2 == 0)
                .log()
                .subscribe();
    }


    //2. flatMap: 扁平化
    @Test
    void flatMap() {

        /**  Flux.just("zhang shan", "Li si")
         .flatMap( v-> {
         String[]s = v.split(" ");
         return Flux.fromArray(s); // 把數據包裝成多元素流
         }).log() // 將兩個人的名字按照空格拆分,打印所有的姓和名

         .subscribe();**/


        Flux<Flux<String>> nestedList = Flux.just(
                Flux.just("Apple", "Banana"),
                Flux.just("Orange", "Mango"),
                Flux.just("Grapes", "Peach"));

        Flux<String> flattened = nestedList.flatMap(flux -> flux).log();
        flattened.subscribe();
    }

    //concatMap
    @Test
    void concatMap() {

        Flux.just(1, 2)
                .concatMap(s -> Flux.just(s + "->a")
                )
                .log().subscribe();

   /**     Flux
                .concat(
                        Flux.just(6, 7, 8),
                        Flux.just(1, 2, 3),
                        Flux.just("gg", "hh")) // 與老流的元素類型不需要一致
                .log().subscribe();

        Flux.just(1, 2)
                .concatWith(Flux.just(6, 7, 8) // 與老流的元素類型必須要一致
                )
                .log().subscribe();**/

    }

    /**
     * transform :  一次性定義時套用，所有訂閱共用同一變換結果,
     * transformDeferred : 每次新訂閱都重新套用變換邏輯，有狀態轉換
     */
    @Test
    void transform() {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        Function<Flux<String>, Flux<String>> toUppser = flux -> {
            // 外部atomicInteger只會因爲調用而增加一次
            System.out.println(atomicInteger.incrementAndGet());
            System.out.println("轉換邏輯被套用了！");
            return flux.map(String::toUpperCase);
        };

        Flux<String> flux1 = Flux.just("apple", "banana")
                .transform(toUppser);


        flux1.subscribe(System.out::println);
        flux1.subscribe(System.out::println); // 此次訂閱不會重新套用 toUpper
    }

    @Test
    void transformDeferred() {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        Function<Flux<String>, Flux<String>> toUppser = flux -> {
            // 外部atomicInteger會因爲調用而增加2次
            System.out.println(atomicInteger.incrementAndGet());
            System.out.println("轉換邏輯被套用了！");
            return flux.map(String::toUpperCase);
        };


        Flux<String> flux2 = Flux.just("apple", "banana")
                .transformDeferred(toUppser);


        flux2.subscribe(System.out::println);
        flux2.subscribe(System.out::println); // 此次訂閱將會重新套用 toUpper

    }

    /**
     * defaultIfEmpty
     * switchIfEmpty
     */
    @Test
    void empty() {
        // Mono.just(null);  // 流裏面有一個null值元素，不爲空
        // Mono.empty();  // 流裏面沒有元元素，流裏面沒有元元素只有完成信號爲空
        haha()
                .defaultIfEmpty("X") // 發佈者元素為null, 指定一個默認值
                .log()
                .subscribe(v -> System.out.println("v = " + v));

        haha()
                .switchIfEmpty(Mono.just("haha")) // 動態兜底數據
                .log()
                .subscribe(v -> System.out.println("v = " + v));

    }

    Mono<String> haha() {
        return Mono.just("a");
    }

    /**
     * merge: 按照時間合并，不保證從第一個flux按順序合并
     * mergeWith
     * mergeSequential: 按照流第一個元素的發佈時間排隊
     */
    @Test
    void merge() {

     /**   Flux.merge(
                        Flux.just(1, 2, 3).delayElements(Duration.ofSeconds(1)), //使用了延時API，將會自動開啓異步
                        Flux.just("a", "b", "c").delayElements(Duration.ofMillis(1500)),
                        Flux.just(4, 5, 6).delayElements(Duration.ofMillis(800)))
                .log()
                .subscribe();**/
    }

    /**
     * 流壓縮: 將第一個flux中的第一個元素和第二個flux中的第一個元素合并為要給tuple([1,a])
     * 在壓縮期間無法結對的元素會被忽略!!!!!!!
     * Tuple: 元組
     * Tuple2: 有兩個元素的元組 <Integer, String>
     * 最多支持8流壓縮
     */
    @Test
    void zip() {

      /**  Flux.just(1, 2, 3)
                .zipWith(Flux.just("a", "b", "c", "d"))
                .map(tuple -> {
                    Integer t1 = tuple.getT1();//獲取元組中過的第一個元素
                    String t2 = tuple.getT2(); // 獲取元組中過的第2個元素
                    return t1 + "===>" + t2;
                })
                .log()
                .subscribe(System.out::println);**/

    }

    /**
     * 錯誤處理: 默認錯誤是一種中斷行爲
     * subscribe: 消費者可以感知 正常元素 try 與 流發生的錯誤 catch
     */
    @Test
    void error() {

        Flux.just(1, 2, 3, 0, 4)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorReturn("Divided by Zero :(") // 一定要寫道map之後,出錯的時候返回兜底給值
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流正常結束"));

        Flux.just(1, 2, 3, 0, 4)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorResume(err -> Mono.just("aaaaa")) // 出錯的時候返回兜底方法
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流正常結束"));

    }


    /**
     * 流正常結束包裝成業務異常并且重新抛出!!!!!!!!!
     */
    @Test
    void catchWrapToBusinessException() {

        Flux.just(1, 2, 3, 0, 4)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorResume(err -> Flux.error(new BusinessException(err.getMessage() + ": 錯誤"))) // 出錯的時候返回兜底方法
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流正常結束"));


        Flux.just(1, 2, 3, 0, 4)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorMap(err -> new BusinessException(err.getMessage() + ": 錯誤")) // 將原來的異常變成包裝類異常
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流正常結束"));


        // 捕獲異常，記錄特殊的錯誤日志，重新抛出
        Flux.just(1, 2, 3, 0, 4)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .doOnError(err -> {
                    // log(err.getMessage()); 記錄日志
                    System.out.println(err.getMessage());
                })
                .doFinally(signalType -> {
                    System.out.println("流信號: " + signalType);
                })
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流正常結束"));

        /*
         * 不讓錯誤停止流，記錄日志后忽略當前錯誤繼續流
         */
        Flux.just(1, 2, 3, 0, 4)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorContinue((throwable, value) -> {
                    System.out.println("err = " + throwable);
                    System.out.println("value = " + value);
                    System.out.println("發現value = " + value + "有問題，我會記錄這個問題");
                }) //發生了錯粗時候繼續完成流
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流正常結束"));


        //onErrorComplete(): 把錯誤結束信號，替換爲正常結束信號
        //onErrorStop(): 錯誤后停止流，源頭終端，所有監聽者全部結束

    }


    /**
     * 超時和延遲與重拾機制
     */
    @Test
    void retryAndTimeout() throws IOException {

        Flux.just(1)
                .delayElements(Duration.ofSeconds(3))//延遲3秒發送
                .log()
                .timeout(Duration.ofSeconds(1))//規定超時時間
                .retry(3)//設置重試次數
                .map(i -> i + "haha")//因爲超時而無法接收到
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流正常結束"));

        System.in.read();


    }


    /**
     * 獲得流中的所有數據
     */
    @Test
    void block() throws IOException {

        List<Integer> block = Flux.just(1)
                .map(i -> i + 10)
                .collectList()
                .block();


    }

    /**
     *
     * 并發流
     * 百萬數據，8個綫程，每個綫程處理100，進行分批處理一直到接結束
     */
    @Test
    void paralleFlux() {

        Flux.range(1, 100)
                .buffer(10)//設置每個現場處理幾個
                .doOnNext(list->
                    System.out.println("收到一組buffer: " + list +
                            " | 綫程: " + Thread.currentThread().getName()))
                .parallel(8)//設置8個并發綫程
                .runOn(Schedulers.newParallel("yy"))
                .log()
               // .flatMap(Flux::fromIterable)
                .flatMap(list->{
                    System.out.println("正在處理: " + list +
                            " | 處理綫程: " + Thread.currentThread().getName());
                    return Flux.fromIterable(list)
                            .delayElements(Duration.ofMillis(50)); // 模擬處理延遲
                })
                .collectSortedList(Integer::compareTo)
                .subscribe(v -> System.out.println("v = " + v));


    }

    // ThreadLocal在響應式編程中無法使用
    //需要使用Context API:  Context: 讀寫  ContextView: 只讀
    @Test
    void threadLocal() {

       Flux.just(1,2,3)
               .transformDeferredContextual((flux,context)->{
                   System.out.println("flux = " + flux);
                   System.out.println("context = " + context);
                   return flux.map(i-> i + "====> "+context.get("prefix"));
               })
              //上游嫩剛拿到下游的最近一次數據
               //Context是反向流，由下游傳到上游
               .contextWrite(Context.of("prefix", "hh")) //ThreadLocal共享了數據,在上游的所有人能看到
               .contextWrite(Context.of("after", "hh"))
               .subscribe(v->System.out.println("v = " + v));


    }



    /**
     * 單播: 單播只能有一個訂閱者
     */
    @Test
    void sinksUnicast() throws IOException {

        //Sinks.many();//發送Flux數據
        // Sinks.one();//發送Mono數據
        // Sinks: 數據管道，所有數據順著管道往下走
        // Sinks.many().unicast();//單播: 管道只能綁定單個訂閱者
        // Sinks.many().multicast();// 多播： 管道能綁定多個訂閱者
        //Sinks.many().replay();// 重放： 能重放元素.儅訂閱者訂閲后可以收到之前已經發佈的數據元素

        Sinks.Many<Object> objectMany = Sinks
                .many()
                .unicast()
                .onBackpressureBuffer(
                        new LinkedBlockingQueue<>(5));

        new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        objectMany.tryEmitNext("a -> " + i);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

        ).start();


        objectMany
                .asFlux()
                .log()
                .subscribe(v -> System.out.println("v = " + v));

        System.in.read();
    }

    /**
     * 多播： 支持多個訂閱者。默認從訂閲的那一刻開始接受元素，無法接受到之前的元素
     *
     * @throws IOException
     */
    @Test
    void sinksMulticast() throws IOException {


        Sinks.Many<Object> objectMany = Sinks
                .many()
                .multicast()
                .onBackpressureBuffer();

        new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        objectMany.tryEmitNext("a -> " + i);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

        ).start();

        // 第一個訂閱者立刻訂閲
        objectMany
                .asFlux()
                .log()
                .subscribe(v -> System.out.println("v1 = " + v));

        // 第2個訂閱者5秒後開始訂閲
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            objectMany
                    .asFlux()
                    .log()
                    .subscribe(v -> System.out.println("v2 = " + v));
        }).start();

        System.in.read();
    }


    /**
     * 重放： 支持多個訂閱者。訂閱者訂閲后可以根據重放機制獲得之前已經發佈的數據
     * 底層利用隊列進行緩存之前數據
     */
    @Test
    void sinksReplay() throws IOException {


        Sinks.Many<Object> objectMany = Sinks
                .many()
                .replay()
                .limit(3);// 設置可重放3個數據

        new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        objectMany.tryEmitNext("a -> " + i);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

        ).start();

        // 第一個訂閱者立刻訂閲
        objectMany
                .asFlux()
                .log()
                .subscribe(v -> System.out.println("v1 = " + v));

        // 第2個訂閱者5秒後開始訂閲
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            objectMany
                    .asFlux()
                    .log()
                    .subscribe(v -> System.out.println("v2 = " + v));
        }).start();

        System.in.read();
    }


    /**
     * 超時和延遲與重拾機制
     * 這是一種 hot 行為（資料已發生、被緩存）；
     * 用在延遲發送或高成本計算的場景下，可以節省資源並加快重複訂閱者的速度；
     * 只有第一次訂閱會真正觸發原始流（range + delay），之後的訂閱者會從 cache 中獲取。
     */
    @Test
    void cache() throws IOException {

        Flux<Integer> cache = Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
                .cache(3);// 設置緩存3個數據,目前1秒發送過一個數據，如果訂閱者7秒之後才開始訂閲，將之惡能獲得緩存中的最後3個數據

        new Thread(() -> {
            try {
                Thread.sleep(7000); // 訂閱者7秒之後才開始訂閲，將之惡能獲得緩存中的最後3個數據
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            cache.subscribe(v -> System.out.println("v = " + v),
                    err -> System.out.println("err = " + err),
                    () -> System.out.println("流正常結束"));
        }).start();
        System.in.read();


    }


}

class BusinessException extends RuntimeException {
    public BusinessException(String msg) {
        super(msg);
    }
}