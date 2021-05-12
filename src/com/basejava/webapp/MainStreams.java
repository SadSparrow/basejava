package com.basejava.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        int[] ints = {3, 1, 2, 3, 3, 2, 3, 8};
        int[] i = {9, 8, 5, 6, 2};
        System.out.println(minValue(ints));
        System.out.println(minValue(i));

        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> list2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 11);

        System.out.println(oddOrEven(list1));
        System.out.println(oddOrEven(list2));
    }

    private static int minValue(int[] values) {
        //return Arrays.stream(values).distinct().sorted().reduce((x, y) -> Integer.parseInt(x + "" + y)).orElse(-1);
        return Arrays.stream(values).distinct().sorted().reduce((x, y) -> (x * 10) + y).orElse(-1);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {

        return integers.stream().reduce(0, Integer::sum) % 2 == 0 ?
                (integers.stream().filter(p -> p % 2 != 0).collect(Collectors.toList())) :
                (integers.stream().filter(p -> p % 2 == 0).collect(Collectors.toList()));
    }
}