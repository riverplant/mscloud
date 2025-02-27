package com.riverplant.mscloud.javacore.aqs;

public class DemoMain {

	private static final MyLock2 lock = new MyLock2();
	
	public static void main(String[] args) {
		
		Runnable task = ()->{
			lock.acquire();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				lock.release();
				
			}
		};
		
		
		new Thread(task).start();
		new Thread(task).start();
	}
}
