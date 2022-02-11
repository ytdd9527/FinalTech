package io.taraxacum.finaltech.util;

/**
 * @author Final_ROOT
 */
public class JavaUtil {
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
}
