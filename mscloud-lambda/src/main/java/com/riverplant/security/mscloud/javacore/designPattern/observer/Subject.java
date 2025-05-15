package com.riverplant.security.mscloud.javacore.designPattern.observer;
/**
 * 主題接口
 */
public interface Subject {

	void registerObserver(Observer o);
	void removeObserver(Observer o);
	void notifyObserver();
}
