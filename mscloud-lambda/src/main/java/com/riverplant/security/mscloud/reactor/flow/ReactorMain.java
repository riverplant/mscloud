package com.riverplant.security.mscloud.reactor.flow;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.SignalType;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Reactor 核心
 */
public class ReactorMain {

    /**
     * 在沒有訂閲流之前什麽也不做
     * subscribe方法
     */
    public static void subscribeCusomer() {

        // doOnXxx: doOnXxx发生这个事件的时候产生一个回调
        // onXxx: 发生合格事件后执行一个动作，这个动作可以改变元素、信号

        Flux<String> flux = Flux.range(1, 0)
                .map(i -> "hh:" + i);

        flux.subscribe();// 流被訂閲, 默認訂閲

        //傳入一個consumer,對流的結果進行操作
        flux.subscribe(System.out::println);
        // 傳入兩個consumer, 其中包扣對錯誤異常的處理
        flux.subscribe(
                System.out::println,
                throwable -> {
                    System.out.println("throwable = " + throwable);
                });


        /*
         * 傳入3個consumer, 其中包扣對錯誤異常的處理, 和對流正常結束后的處理
         */
        flux.subscribe(
                System.out::println,
                throwable -> {
                    System.out.println("throwable = " + throwable);
                },
                () -> System.out.println("流正常結束"));

        // 自定義消費者
        flux.subscribe(new BaseSubscriber<String>() {
            // 生命周期鈎子，訂閲關係綁定的時候觸發
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                System.out.println(" 訂閱者綁定: " + subscription);
                request(1); // 向上游請求一個數據
            }

            // 儅數據到達的時候觸發
            @Override
            protected void hookOnNext(String value) {
                System.out.println(" 數據到達: " + value);
                if ("haha: 5".equals(value))
                    cancel(); // 取消流
                request(1); // 向上游繼續請求一個數據
            }

            @Override
            protected void hookOnComplete() {
                System.out.println(" 流正常結束 ");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                System.out.println(" 流異常... " + throwable);
            }

            @Override
            protected void hookOnCancel() {
                System.out.println(" 流被取消 ");
            }

            @Override
            protected void hookFinally(SignalType type) {
                System.out.println(" 最终回调... ");
            }
        });


    }

    public void buffer() {
        Flux<List<Integer>> flux = Flux.range(1, 10)
                .buffer(3)// buffer为数据添加一个缓冲区,缓存3个元素!!!!!!!消费者一次最多可以拿到3个元素
                .log();
        // 消费者每次request(1), 拿到的是buffer大小的数据
        flux.subscribe(v -> System.out.println("v = " + v));
    }


    public void limit() {
        Flux.range(1, 100)
                .log()
                .limitRate(30) // 一次预取30个元素
                //.buffer(3)// buffer为数据添加一个缓冲区,缓存3个元素!!!!!!!消费者一次最多可以拿到3个元素
                .subscribe(); // 75% 75预取策略limitRate(100)
        // 第一次抓取100个数据，如果75%已经处理了，继续抓取新的75%!!!!!!!!
        //75% 为负载因子
    }

    /**
     * 通过编程同步创建序列
     * Sink: Sink接收器、水槽、通道。数据的接收端
     */
    public void generator() {
       /* Flux<Object> flux = Flux.generate(sink -> {
            for (int i = 0; i < 100; i++) {
                sink.next("haha-" + i); //传递数据，可以能抛出不受检异常
            }
        });**/

        Flux<Object> flux = Flux.generate(AtomicInteger::new,  // 初始值为0
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    if (i == 7) {
                        sink.error(new RuntimeException("不喜欢7"));
                    } else if (i <= 10) {
                        // 0-10
                        sink.next(i); //把元素传出去
                    } else {
                        sink.complete();
                    }


                    return state;
                });
        flux.log().subscribe();
    }

    /**
     * 游戲監聽器
     */
    class GameListener {
        private FluxSink<Object> sink;

        public GameListener(FluxSink<Object> sink) {
            this.sink = sink;
        }

        //用戶上綫
        public void online(String userName) {
            sink.next(userName);//傳入userName
        }
    }

    /**
     * 通过编程異步多綫程创建序列
     * Sink: Sink接收器、水槽、通道。数据的接收端
     */
    public void create() {


        Flux<Object> flux = Flux.create(sink -> {

            GameListener listener = new GameListener(sink);

            for (int i = 0; i < 100; i++) {
                listener.online("user" + i);
            }
        });
        flux.log().subscribe();
    }

    /**
     * 對流中的數據自定義處理
     */
    public void handle() {

        Flux.range(1, 10)
                //自定義處理流中的數據
                .handle((value, sink) -> {
                    System.out.println("獲取的值: " + value);
                    //通過值創建一個對象
                    User user = new User(UUID.randomUUID().toString(), value);
                    //將user發送給下端的流
                    sink.next(user);

                })//傳入兩個數據(value, sink)
                .log()
                //.buffer(3)// buffer为数据添加一个缓冲区,缓存3个元素!!!!!!!消费者一次最多可以拿到3个元素
                .subscribe(); // 75% 75预取策略limitRate(100)
    }

    public static void main(String[] args) {
        new ReactorMain().handle();
    }

}

class User {
    private String id;
    private Object userName;

    public User(String id, Object userName) {
        this.id = id;
        this.userName = userName;
    }
}
