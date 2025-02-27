package com.riverplant.mscloud.stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Streammain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	public void test() {
		//1. 創建流
		Stream<Integer> streammain = Stream.of(1,2,3);
		Stream<Integer> concatStream = Stream.concat(Stream.of(2,3,4), streammain);
		Stream<Object> buildStream = Stream.builder().add("11").add("22").build();
		
		
		
		//2. 從集合容器中獲取流
		List<Integer> list = List.of(1,2);
		
		list.stream();
		
		
		Set<String> set = Set.of();
		set.stream();
		
		Map<String, Object> map = Map.of();
		map.keySet().stream();
		map.values().stream();
		//流的所有操作都是無狀態，所有數據狀態僅在此函數内有效!!!!!!!!!
		//流内的數據不使用增刪改
		System.out.println("主綫程:" + Thread.currentThread().getName());
		
		//定義現場安全的list
		List<Object> objectList = Collections.synchronizedList(new ArrayList<>());
		//流是否并發，和For有什麽區別: 默認流操作不是并發的，和主綫程用同一個綫程
		long count = Stream.of(1, 2, 3, 4, 5)
				.parallel() // 開啓并發后將需要考慮安全問題!!!!!
				.filter(i -> i > 2)
				.count();
		
		//flatMap: 一對多映射
		
	}

}
