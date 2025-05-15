package com.riverplant.security.mscloud.javacore.aqs;

import java.util.concurrent.Semaphore;

public class OrderThread {
/**
 * s1和s2一開始都無法獲得信號量，所以s3會率先獲得信號量打印c,然後釋放s1,s1會獲得信號量打印a,然後釋放s2...
 * @param args
 * @throws InterruptedException
 */
	public static void main(String[] args) throws InterruptedException {
		Semaphore s1 = new Semaphore(1);
		Semaphore s2 = new Semaphore(1);
		Semaphore s3 = new Semaphore(1);
		
		s1.acquire();
		s2.acquire();
		
		new Thread(()->{
			while(true) {
				try {
					s1.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				try {
					Thread.sleep(500);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				System.out.print("a");
				s2.release();
				
			}
			
			
		}).start();
		
		
		new Thread(()->{
			while(true) {
				try {
					s2.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				try {
					Thread.sleep(500);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				System.out.print("b");
				s3.release();
				
			}
			
			
		}).start();
	
	
	
	new Thread(()->{
		while(true) {
			try {
				s3.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(500);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
			System.out.print("c");
			s1.release();
			
		}
		
		
	}).start();
}

}
