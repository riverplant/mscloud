package com.riverplant.mscloud.lambda;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FlatMapExample {
    public static void main(String[] args) {
        List<List<String>> nestedList = Arrays.asList(
                Arrays.asList("Apple", "Banana"),
                Arrays.asList("Orange", "Mango"),
                Arrays.asList("Grapes", "Peach")
        );

        // 使用 flatMap 将多个 List 合并成一个 List
        List<String> flatList = nestedList.stream()
                .flatMap(List::stream)  // 将 List<String> 转换成 Stream<String>
                .collect(Collectors.toList());

        System.out.println(flatList);
    }
}
