package com.riverplant.security.mscloud.javacore.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class MyLock{

	private static class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int acquires) {
            // 通过 CAS 方式修改 state，确保只有一个线程能获取锁
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread()); // 记录当前持有锁的线程
                return true;
            }
            return false; // 其他线程获取失败，进入等待队列
        }

        @Override
        protected boolean tryRelease(int releases) {
            if (getState() == 0) throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null); // 清除锁的拥有者
            setState(0); // 释放锁
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }
    }

    private final Sync sync = new Sync();

    public void acquire() {
        sync.acquire(1);
        System.out.println(Thread.currentThread().getName()+"get lock");
    }

    public void release() {
        sync.release(1);
        System.out.println(Thread.currentThread().getName()+"release lock");
    }
	
}
