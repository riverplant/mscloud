package com.riverplant.security.mscloud.reactor.flow;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;

public class FlowDemo {
	/**
	 * 處理器既是一個訂閱者，獲取上游的數據，又是一個發佈者，將處理過后的數據發佈給下游
	 */
	static class MyProcessor extends SubmissionPublisher<String> implements Subscriber<String> {

		private Subscription subscription; //保存綁定關係
		
		@Override
		public void onSubscribe(Subscription subscription) {
			System.out.println("processor訂閲綁定完成");
			
			this.subscription = subscription;
			
			subscription.request(1); //找上游拿數據
		}

		@Override //數据到達，觸發這個回調
		public void onNext(String item) {
			System.out.println("processor拿到數據，開始處理");
			item += ": hehe";
			submit(item); // 將加工後的數據發出去
			subscription.request(1); //再要一個新數據，繼續加工
		}

		@Override
		public void onError(Throwable throwable) {
			
			
		}

		@Override
		public void onComplete() {
			
			
		}
		
	}
/**
 * 1. publisher: 發佈者
 * 2. Subscriber: 訂閱者
 * 3. Subscription: 訂閲關係
 */
	public static void main(String[] args) throws InterruptedException {
		
		//1. 定義一個發佈者, 發佈數據

        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
		
		//定義了處理器之後，綁定關係變成發佈者綁處理器，處理器綁訂閱者!!!!!!
		MyProcessor myProcessor = new MyProcessor();
			
		//2. 定義一個訂閲者，訂閲發佈者發佈的數據
		Subscriber<String> subscriber = new Subscriber<>() {

			private Subscription subscription;
			
			//在訂閲時,會收到訂閲關係作爲參數，此時就等於知道了發佈者和訂閱者之間的契約
			@Override//在訂閲時  事件出發機制，在xxx時間發生時，執行這個回調
			public void onSubscribe(Subscription subscription) {
				System.out.println(Thread.currentThread().getName());
				System.out.println("訂閲開始了:" + subscription);
				this.subscription = subscription;
				//從上游請求一個數據
				//Long.MAX_VALUE請求最多
				this.subscription.request(1);
				
			}

			//在下一個元素到達時,執行這個回調。接收到了新數據
			@Override
			public void onNext(String item) {
				System.out.println(Thread.currentThread().getName());
				System.out.println("訂閲者接收到數據:" + item);
				//這裏必須要開啓請求下一條數據
				this.subscription.request(1); //在請求下一條數據!!!!!!
				
			}

			//在錯誤發生時
			@Override
			public void onError(Throwable throwable) {
				System.out.println(Thread.currentThread().getName());
				System.out.println("訂閲者接收到錯誤信號:" + throwable);
				
			}

			@Override
			public void onComplete() {
				System.out.println(Thread.currentThread().getName());
				System.out.println("訂閲者接收到完成信號:");	
			}
			
			
			
		};
		
		//3. 綁定發佈者和訂閲者
		//publisher.subscribe(subscriber);
		//綁定關係發生改變
		publisher.subscribe(myProcessor); //此時處理器相當於訂閲者
		myProcessor.subscribe(subscriber); //此時處理器相當於發佈者
		
		//如果有多個處理器，綁定關係： 發佈者綁定處理器1， 處理器1綁定處理器2....最後綁定訂閱者
		//Stream   filter  map flatmap collector
		
		//必須要先訂閲好，再發數據，否則收不到任何數據
		for(int i=0;i<10;i++) {
			//發佈者發佈10條數據給消息隊列[JVM内置的緩衝區]
			publisher.submit("p-" + i); //publisher發佈的所有數據在它的buff區
		}
		
		//發佈者有數據，訂閱者就會拿到
		//訂閱者u需要給上游一個信號，讓發佈者可以發元素下來了:背壓模式!!!!!!!!
		
		//發佈者通道關閉
		publisher.close();
		Thread.sleep(1000);

	}

}
