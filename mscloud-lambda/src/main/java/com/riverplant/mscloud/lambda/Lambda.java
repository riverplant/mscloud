package com.riverplant.mscloud.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class Lambda {

	
	public static void main(String[] args) {
		Lambda lambda = new Lambda();
		lambda.sort();
	}
	
	
	public void sort() {
		List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
		
		//比較器
		//正序
		Collections.sort(names, (o1,o2)->o1.compareTo(o2));
		System.out.println("正序:" + names);
		//倒序
	    Collections.sort(names, (o1,o2)->o2.compareTo(o1));
		System.out.println("倒序:" + names);
		//類::方法；引用類中的實例方法,只能順序比較
		Collections.sort(names, String::compareTo);
		
		
		Predicate<Integer> event = t->t%2==0;
		event.test(2);//正向判斷
		event.negate().test(2); //反向判斷
	}
	
	
	
}
