package com.riverplant.security.mscloud.javacore.aqs;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SemaphoreDemo {
    private static final int THREAD_COUNT = 6;
    private static final Semaphore semaphore = new Semaphore(3); // 3 个许可证

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    semaphore.acquire(); // 获取许可
                    System.out.println("Task " + taskId + " is executing...");
                    Thread.sleep((long) (Math.random() * 3000));
                    System.out.println("Task " + taskId + " is done.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release(); // 释放许可
                }
            });
        }

        executor.shutdown();
    }
}
