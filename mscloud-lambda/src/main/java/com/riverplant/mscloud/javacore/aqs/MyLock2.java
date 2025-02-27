package com.riverplant.mscloud.javacore.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class MyLock2 {

	private static class Sync extends AbstractQueuedSynchronizer {
		private static final long serialVersionUID = 3113490673756474654L;

		@Override
		protected boolean tryAcquire(int arg) {
			int state = getState();
			if(compareAndSetState(0, 1)) {
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			//可重入鎖
			if(Thread.currentThread() == getExclusiveOwnerThread()) {
				compareAndSetState(state, state + 1);
				return true;
			}
			return false;
		}

		@Override
		protected boolean tryRelease(int arg) {
			int state = getState();
			if( state == 0 ) throw new IllegalMonitorStateException();
			setState( state - 1 );
			//所有的鎖都已經釋放
			if( getState() == 0) {
				setExclusiveOwnerThread(null);	
			}
			return true;
		}

		@Override
		protected boolean isHeldExclusively() {
			return getState() > 0 ;
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
