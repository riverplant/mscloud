package com.riverplant.mscloud.javacore.aqs;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForkJoinTest {
	private static final Logger logger = LoggerFactory.getLogger(ForkJoinTest.class);
	private static int MAX = 100;
	private static int[] inst = new int[MAX];
	
	static {
		Random r = new Random();
		
		for(int index = 0; index <MAX ; index++) {
			inst[index] = r.nextInt(1000);
		}
	}
	
	public static void main(String[] args) {
		long beginTime = System.currentTimeMillis();
		ForkJoinPool pool = new ForkJoinPool();
		try {
			MyTask task = new MyTask(inst);
			ForkJoinTask<int[]>  taskResult = pool.submit(task);
			System.out.println(Arrays.toString(taskResult.get()));

		}catch (Exception e) {
			logger.error("Unexpected error occurred", e);
		}finally {
			pool.shutdown();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("耗時:"+ ( endTime - beginTime ));
	}
	
	static class MyTask extends RecursiveTask<int[]> {
      
		private static final long serialVersionUID = -984291097867525509L;
		private int[] source;
        public MyTask(int[] source) {
        	this.source = source;
        }
		@Override
		protected int[] compute() {
			int sourceLen = source.length;
			
			if(sourceLen > 2) {
				int midIndex = sourceLen / 2;//找到中間的索引
				MyTask task1 = new MyTask(Arrays.copyOf(source, midIndex));
				task1.fork();//把計算任務提交到ForkJoinPool計算列表中
				MyTask task2 = new MyTask(Arrays.copyOfRange(source, midIndex, sourceLen));
				task2.fork();//把計算任務提交到計算列表中
				
				int result1[] = task1.join();
				int result2[] = task2.join();
				int merge[] = joinInts(result1, result2);
				return merge;
			}else {
				if(sourceLen == 1 || source[0] <= source[1]) {
					return source;
				}else {
					int targetp[] = new int[sourceLen];
					targetp[0] = source[1];
					targetp[1] = source[0];
					return targetp;
				}
			}
			
		}
		
		/**
		 * 合并兩個有序集合
		 * @param result1
		 * @param result2
		 * @return
		 */
		private int[] joinInts(int[] result1, int[] result2) {
			int re1Length = result1.length;
			int re2Length = result2.length;
			int destInts[] = new int[re1Length + re2Length];
			
			int i=0,j=0,k=0;
			
			while(i<re1Length && j< re2Length) {
				if(result1[i] < result2[j]) {
					destInts[k++] = result1[i++];
				}else {
					destInts[k++] = result2[j++];
				}
			}
			
			//儅兩個數組集合中的一個遍歷完成，處理剩下的元素
			while(i<re1Length) {
				destInts[k++] = result1[i++];
			}
			
			while(j<re2Length) {
				destInts[k++] = result2[j++];
			}
			return destInts;
		}
		
	}

}
