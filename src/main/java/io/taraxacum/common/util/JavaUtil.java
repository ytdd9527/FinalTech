package io.taraxacum.common.util;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Final_ROOT
 */
public class JavaUtil {

    public static <T> Set<T> toSet(T... objects) {
        Set<T> result = new HashSet<>(objects.length);
        result.addAll(Arrays.asList(objects));
        return result;
    }
    public static <T> List<T> toList(T... objects) {
        List<T> result = new ArrayList<>(objects.length);
        result.addAll(Arrays.asList(objects));
        return result;
    }
    public static int[] reserve(int[] objects) {
        int[] result = objects.clone();
        for (int i = 0; i < objects.length; i++) {
            result[i] = objects[objects.length - 1 - i];
        }
        return result;
    }
    public static <T> List<T> reserve(List<T> objectList) {
        List<T> result = new ArrayList<>(objectList);
        for (int i = 0; i < objectList.size(); i++) {
            result.set(i, objectList.get(objectList.size() - 1 - i));
        }
        return result;
    }

    public static int[] shuffle(int[] objects) {
        List<Integer> collect = Arrays.stream(objects).boxed().collect(Collectors.toList());
        Collections.shuffle(collect);
        int[] result = objects.clone();
        for (int i = 0; i < collect.size(); i++) {
            result[i] = collect.get(i);
        }
        return result;
    }
    public static <T> T[] shuffle(T[] objects) {
        List<T> collect = Arrays.stream(objects).collect(Collectors.toList());
        Collections.shuffle(collect);
        T[] result = objects.clone();
        for (int i = 0; i < collect.size(); i++) {
            result[i] = collect.get(i);
        }
        return result;
    }
    public static <T> List<T> shuffle(List<T> objectList) {
        List<T> list = new ArrayList<>(objectList);
        Collections.shuffle(list);
        return list;
    }

    public static double[] disperse(int size, Number... value) {
        if (size == 1 && value.length > 0) {
            return new double[] {value[0].doubleValue()};
        } else if (size == 0 || value.length == 0) {
            return new double[0];
        }
        double[] result = new double[size--];
        for (int i = 0; i <= size; i++) {
            double p = ((double) i) / size * (value.length - 1);
            double value1 = value[(int) Math.floor(p)].doubleValue() * (1 - p + Math.floor(p));
            double value2 = value[(int) Math.ceil(p)].doubleValue() * (p - Math.floor(p));
            result[i] = value1 + value2;
        }
        return result;
    }

    public static long testTime(@Nonnull Runnable runnable) {
        long beginTime = System.nanoTime();
        runnable.run();
        return System.nanoTime() - beginTime;
    }
}
