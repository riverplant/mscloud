package com.riverplant.mscloud.javacore.aqs;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo {
    private static final int TASK_COUNT = 5;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(TASK_COUNT);
        ExecutorService executor = Executors.newFixedThreadPool(TASK_COUNT);

        for (int i = 0; i < TASK_COUNT; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    System.out.println("Task " + taskId + " is running...");
                    Thread.sleep((long) (Math.random() * 3000));
                    System.out.println("Task " + taskId + " is completed.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown(); // 计数器 -1
                }
            });
        }

        System.out.println("Main thread waiting for tasks to complete...");
        latch.await(); // 等待所有任务完成
        System.out.println("All tasks completed. Main thread continues.");

        executor.shutdown();
    }
}
