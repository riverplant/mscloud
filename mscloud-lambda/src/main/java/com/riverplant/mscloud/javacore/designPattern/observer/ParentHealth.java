package com.riverplant.mscloud.javacore.designPattern.observer;

import java.util.ArrayList;
import java.util.List;

public class ParentHealth implements Subject {

	private List<Observer> observers = new ArrayList<>();
	
	private String message;
	
	@Override
	public void registerObserver(Observer o) {
		observers.add(o);

	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(0);

	}

	@Override
	public void notifyObserver() {
		for(Observer observer : observers)
			observer.update(message);

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
		//收到消息后通知所有的觀察者
		notifyObserver();
	}
	
	

}
