package io.taraxacum.finaltech.util;

import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class JavaUtil<T> {
    /**
     * 反转数组
     * @param ints 输入的数组
     */
    public static void reserve(int[] ints) {
        for(int i = 0; i < ints.length / 2; i++) {
            int k = ints[i];
            ints[i] = ints[ints.length - 1 - i];
            ints[ints.length - 1 - i] = k;
        }
    }

    public List<T> reserve(List<T> list) {
        List<T> result = new ArrayList<>();
        for(int i = list.size() - 1; i >= 0; i--) {
            result.add(list.get(i));
        }
        return result;
    }
}
