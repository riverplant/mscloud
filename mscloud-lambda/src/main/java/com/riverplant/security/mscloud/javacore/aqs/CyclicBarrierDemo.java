package com.riverplant.security.mscloud.javacore.aqs;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierDemo {
    private static final int TASK_COUNT = 3;
    private static final CyclicBarrier barrier = new CyclicBarrier(TASK_COUNT, 
        () -> System.out.println("All tasks completed. Merging results..."));

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(TASK_COUNT);

        for (int i = 0; i < TASK_COUNT; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    System.out.println("Task " + taskId + " is running...");
                    Thread.sleep((long) (Math.random() * 3000));
                    System.out.println("Task " + taskId + " reached the barrier.");
                    barrier.await(); // 等待所有线程到达屏障
                    System.out.println("Task " + taskId + " continues.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
    }
}
