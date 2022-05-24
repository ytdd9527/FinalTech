package io.taraxacum.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Final_ROOT
 */
public class JavaUtil<T> {
    /**
     * 反转数组
     * @param ints 输入的数组
     */
    public static int[] reserve(int[] ints) {
        int[] result = new int[ints.length];
        for (int i = 0; i < ints.length; i++) {
            result[i] = ints[ints.length - i - 1];
        }
        return result;
    }

    public List<T> reserve(List<T> list) {
        List<T> result = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            result.add(list.get(i));
        }
        return result;
    }

    public T[] reserve(T[] stack) {
        List<T> collect = Arrays.stream(stack).collect(Collectors.toList());
        Collections.shuffle(collect);
        T[] result = (T[]) new Object[stack.length];
        for(int i = 0; i < collect.size(); i++) {
            result[i] = collect.get(i);
        }
        return result;
    }

    public static int[] shuffle(int[] ints) {
        List<Integer> collect = Arrays.stream(ints).boxed().collect(Collectors.toList());
        Collections.shuffle(collect);
        int[] result = new int[ints.length];
        for(int i = 0; i < collect.size(); i++) {
            result[i] = collect.get(i);
        }
        return result;
    }

    public static double[] disperse(int size, Number... value) {
        if(size == 1 && value.length > 0) {
            return new double[] {value[0].doubleValue()};
        } else if(size == 0 || value.length == 0) {
            return new double[0];
        }
        double[] result = new double[size--];
        for(int i = 0; i <= size; i++) {
            double p = ((double) i) / size * (value.length - 1);
            double value1 = value[(int) Math.floor(p)].doubleValue() * (1 - p + Math.floor(p));
            double value2 = value[(int) Math.ceil(p)].doubleValue() * (p - Math.floor(p));
            result[i] = value1 + value2;
        }
        return result;
    }
}
