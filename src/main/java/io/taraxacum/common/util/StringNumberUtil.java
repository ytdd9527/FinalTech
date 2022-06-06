package io.taraxacum.common.util;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public final class StringNumberUtil {
    private static final String RELATIVE = "-";
    private static final int ZERO_CHAR_VALUE = '0';
    private static final int DOUBLE_OF_ZERO_CHAR_VALUE = '0' + '0';

    public static final String VALUE_INFINITY = "INFINITY";
    public static final String VALUE_MIN = "MIN";
    public static final String ZERO = "0";
    public static final String ONE = "1";
    public static final String INTEGER_MAX_VALUE = String.valueOf(Integer.MAX_VALUE);

    /**
     * 比较两个非负整数大小
     * 第一个数较大时，返回 1
     * 第二个数较大时，返回 -1
     * 否则返回 0
     * 临时使用，后续请替换为 compare(String, String)方法
     * @param stringNumber1 非负整数
     * @param stringNumber2 非负整数
     * @return 比较结果
     */
    @Deprecated
    public static int easilyCompare(@Nonnull String stringNumber1, @Nonnull String stringNumber2) {
        if (VALUE_INFINITY.equals(stringNumber1) && VALUE_INFINITY.equals(stringNumber2)) {
            return 0;
        } else if (VALUE_INFINITY.equals(stringNumber1)) {
            return 1;
        } else if (VALUE_INFINITY.equals(stringNumber2)) {
            return -1;
        }
        if (VALUE_MIN.equals(stringNumber1) && VALUE_MIN.equals(stringNumber2)) {
            return 0;
        } else if (VALUE_MIN.equals(stringNumber1)) {
            return -1;
        } else if (VALUE_MIN.equals(stringNumber2)) {
            return 1;
        }
        if (stringNumber1.length() < stringNumber2.length()) {
            return -1;
        } else if (stringNumber1.length() > stringNumber2.length()) {
            return 1;
        }
        char[] s1 = stringNumber1.toCharArray();
        char[] s2 = stringNumber2.toCharArray();
        for (int i = 0; i < s1.length; i++) {
            if (s1[i] > s2[i]) {
                return 1;
            } else if (s1[i] < s2[i]) {
                return -1;
            }
        }
        return 0;
    }

    /**
     * 简易的加法，令两个非负整数相加
     * @param stringNumber1 非负整数
     * @param stringNumber2 非负整数
     * @return 相加和
     */
    @Nonnull
    private static String easilyAdd(@Nonnull String stringNumber1, @Nonnull String stringNumber2) {
        char[] s1 = stringNumber1.toCharArray();
        char[] s2 = stringNumber2.toCharArray();
        int minLength = Math.min(s1.length, s2.length);
        int maxLength = Math.max(s1.length, s2.length);
        StringBuilder stringBuilder = new StringBuilder(maxLength + 1);
        int i;
        int add = 0;
        int r;
        for (i = 0; i < minLength; i++) {
            r = s1[s1.length - i - 1] + s2[s2.length - i - 1] + add - DOUBLE_OF_ZERO_CHAR_VALUE;
            add = r / 10;
            stringBuilder.append(r % 10);
        }
        int n1;
        int n2;
        for (; i < maxLength; i++) {
            n1 = s1.length > i ? s1[s1.length - i - 1] : ZERO_CHAR_VALUE;
            n2 = s2.length > i ? s2[s2.length - i - 1] : ZERO_CHAR_VALUE;
            r = n1 + n2 + add - DOUBLE_OF_ZERO_CHAR_VALUE;
            add = r / 10;
            stringBuilder.append(r % 10);
        }
        if (add != 0) {
            stringBuilder.append(add);
        }
        return stringBuilder.reverse().toString();
    }

    /**
     * 简易的加法，令数字加一
     * @param stringNumber 非负整数
     * @return 加一后的值
     */
    @Nonnull
    private static String easilyAdd(@Nonnull String stringNumber) {
        char[] s = stringNumber.toCharArray();
        StringBuilder stringBuilder = new StringBuilder(s.length + 1);
        int r;
        int add = 0;
        s[s.length - 1] ++;
        int i;
        for (i = s.length - 1; i >= 0; i --) {
            r = s[i] + add - ZERO_CHAR_VALUE;
            add = r / 10;
            r %= 10;
            stringBuilder.append(r);
            if (add == 0) {
                i--;
                break;
            }
        }
        for (; i >= 0; i--) {
            stringBuilder.append(s[i] - ZERO_CHAR_VALUE);
        }
        if (add > 0) {
            stringBuilder.append(add);
        }
        return stringBuilder.reverse().toString();
    }

    /**
     * 简易的减法，被减数需要比减数大
     * @param stringNumber1
     * @param stringNumber2
     * @return
     */
    @Nonnull
    private static String easilySub(@Nonnull String stringNumber1, @Nonnull String stringNumber2) {
        char[] s1 = stringNumber1.toCharArray();
        char[] s2 = stringNumber2.toCharArray();
        int minLength = Math.min(s1.length, s2.length);
        int maxLength = Math.max(s1.length, s2.length);
        StringBuilder stringBuilder = new StringBuilder(maxLength);
        int i;
        int sub = 0;
        int r;
        for (i = 0; i < minLength; i++) {
            r = s1[s1.length - i - 1] - s2[s2.length - i - 1] - sub;
            sub = 10 - (r + 100) / 10;
            r = (r + 100) % 10;
            stringBuilder.append(r);
        }
        int n1;
        int n2;
        for (; i < maxLength; i++) {
            n1 = s1.length > i ? s1[s1.length - i - 1] : ZERO_CHAR_VALUE;
            n2 = s2.length > i ? s2[s2.length - i - 1] : ZERO_CHAR_VALUE;
            r = n1 - n2 - sub;
            sub = 10 - (r + 100) / 10;
            r = (r + 100) % 10;
            stringBuilder.append(r);
        }
        while (stringBuilder.length() > 0) {
            if (stringBuilder.charAt(stringBuilder.length() - 1) == '0') {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            } else {
                break;
            }
        }
        return stringBuilder.reverse().toString();
    }

    @Nonnull
    private static String easilySub(@Nonnull String stringNumber) {
        char[] s = stringNumber.toCharArray();
        StringBuilder stringBuilder = new StringBuilder(stringNumber.length());
        int i;
        int sub = 0;
        int r;
        s[s.length - 1]--;
        for (i = s.length - 1; i >= 0; i--) {
            r = s[i]  - sub - ZERO_CHAR_VALUE;
            sub = 10 - (r + 100) / 10;
            r = (r + 100) % 10;
            stringBuilder.append(r);
            if (sub == 0) {
                i--;
                break;
            }
        }
        for (; i >= 0; i--) {
            stringBuilder.append(s[i]);
        }
        while (stringBuilder.length() > 0) {
            if (stringBuilder.charAt(stringBuilder.length() - 1) == '0') {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            } else {
                break;
            }
        }
        if(stringBuilder.length() == 0) {
            return StringNumberUtil.ZERO;
        }
        return stringBuilder.reverse().toString();
    }

    @Nonnull
    public static String easilyMul(@Nonnull String stringNumber1, @Nonnull String stringNumber2) {
        char[] s1 = stringNumber1.toCharArray();
        char[] s2 = stringNumber2.toCharArray();
        int[] result = new int[s1.length + s2.length];
        StringBuilder stringBuilder = new StringBuilder();
        int r;
        int point;
        int c1;
        for (int i = 0; i < s1.length; i++) {
            c1 = s1[s1.length - i - 1] - ZERO_CHAR_VALUE;
            point = s1.length + s2.length - i - 1;
            for (int j = 0; j < s2.length; j++) {
                r = c1 * (s2[s2.length - j - 1] - ZERO_CHAR_VALUE);
                result[point] += r % 10;
                result[point-- - 1] += r / 10;
            }
        }
        for (int l = s1.length + s2.length  - 1; l > 0; l--) {
            result[l - 1] += result[l] / 10;
            result[l] %= 10;
        }
        int i ;
        for (i = 0; i < result.length; i++) {
            if (result[i] != 0) {
                break;
            }
        }
        for (; i < result.length; i++) {
            stringBuilder.append((char)(result[i] + ZERO_CHAR_VALUE));
        }
        if (stringBuilder.length() == 0) {
            stringBuilder.append(ZERO);
        }
        return stringBuilder.toString();
    }

    public static int compare(String stringNumber1, String stringNumber2) {
        if (stringNumber1.equals(stringNumber2)) {
            return 0;
        }
        if (stringNumber1.contains(VALUE_INFINITY)) {
            return stringNumber1.startsWith(RELATIVE) ? -1 : 1;
        }
        if (stringNumber2.contains(VALUE_INFINITY)) {
            return stringNumber2.startsWith(RELATIVE) ? 1 : -1;
        }
        boolean r1 = stringNumber1.startsWith(RELATIVE);
        boolean r2 = stringNumber2.startsWith(RELATIVE);
        if (!r1 && !r2) {
            return StringNumberUtil.easilyCompare(stringNumber1, stringNumber2);
        } else if (r1) {
            return -1;
        } else if (r2) {
            return 1;
        }
        String s1 = r1 ? stringNumber1.substring(1) : stringNumber1;
        String s2 = r2 ? stringNumber2.substring(1) : stringNumber2;
        return easilyCompare(s1, s2);
    }

    public static String min(String stringNumber1, String stringNumber2) {
        if (stringNumber1.equals(stringNumber2)) {
            return stringNumber1;
        }
        if (stringNumber1.contains(VALUE_INFINITY)) {
            return stringNumber1.startsWith(RELATIVE) ? stringNumber1 : stringNumber2;
        }
        if (stringNumber2.contains(VALUE_INFINITY)) {
            return stringNumber2.startsWith(RELATIVE) ? stringNumber2 : stringNumber1;
        }
        boolean r1 = stringNumber1.startsWith(RELATIVE);
        boolean r2 = stringNumber2.startsWith(RELATIVE);
        if (r1 && !r2) {
            return stringNumber1;
        } else if (!r1 && r2) {
            return stringNumber2;
        } else if (r1) {
            String s1 = stringNumber1.substring(1);
            String s2 = stringNumber2.substring(1);
            return easilyCompare(s1, s2) > 0 ? s1 : s2;
        }
        return easilyCompare(stringNumber1, stringNumber2) > 0 ? stringNumber2 : stringNumber1;
    }

    public static String max(String stringNumber1, String stringNumber2) {
        if (stringNumber1.equals(stringNumber2)) {
            return stringNumber1;
        }
        if (stringNumber1.contains(VALUE_INFINITY)) {
            return stringNumber1.startsWith(RELATIVE) ? stringNumber2 : stringNumber1;
        }
        if (stringNumber2.contains(VALUE_INFINITY)) {
            return stringNumber2.startsWith(RELATIVE) ? stringNumber1 : stringNumber2;
        }
        boolean r1 = stringNumber1.startsWith(RELATIVE);
        boolean r2 = stringNumber2.startsWith(RELATIVE);
        if (r1 && !r2) {
            return stringNumber2;
        } else if (!r1 && r2) {
            return stringNumber1;
        } else if (r1) {
            String s1 = stringNumber1.substring(1);
            String s2 = stringNumber2.substring(1);
            return easilyCompare(s1, s2) > 0 ? s2 : s1;
        }
        return easilyCompare(stringNumber1, stringNumber2) > 0 ? stringNumber1 : stringNumber2;
    }

    public static String add(@Nonnull String stringNumber1, @Nonnull String stringNumber2) {
        if (stringNumber1.contains(VALUE_INFINITY) || stringNumber2.contains(VALUE_INFINITY)) {
            return VALUE_INFINITY;
        }
        boolean r1 = stringNumber1.startsWith(RELATIVE);
        boolean r2 = stringNumber2.startsWith(RELATIVE);
        if (r1 && r2) {
            return RELATIVE + easilyAdd(stringNumber1.substring(1), stringNumber2.substring(1));
        } else if (!r1 && !r2) {
            return easilyAdd(stringNumber1, stringNumber2);
        }
        String s1 = r1 ? stringNumber1.substring(1) : stringNumber1;
        String s2 = r2 ? stringNumber2.substring(1) : stringNumber2;
        int c = easilyCompare(s1, s2);
        if (c > 0) {
            if (r1) {
                return RELATIVE + easilySub(s1, s2);
            } else {
                return easilySub(s1, s2);
            }
        } else if (c < 0) {
            if (r2) {
                return RELATIVE + easilySub(s2, s1);
            } else {
                return easilySub(s2, s1);
            }
        }
        return "0";
    }

    public static String add(String stringNumber) {
        if (stringNumber.contains(VALUE_INFINITY)) {
            return VALUE_INFINITY;
        }
        if (stringNumber.indexOf(RELATIVE) == 0) {
            return RELATIVE + easilySub(stringNumber.substring(1));
        } else {
            return easilyAdd(stringNumber);
        }
    }

    public static String sub(String stringNumber1, String stringNumber2) {
        if (stringNumber1.contains(VALUE_INFINITY)) {
            return stringNumber1;
        }
        if (stringNumber2.contains(VALUE_INFINITY)) {
            return stringNumber2.startsWith(RELATIVE) ? VALUE_INFINITY : RELATIVE + VALUE_INFINITY;
        }
        boolean r1 = stringNumber1.startsWith(RELATIVE);
        boolean r2 = stringNumber2.startsWith(RELATIVE);
        if (!r1 && r2) {
            return easilyAdd(stringNumber1, stringNumber2.substring(1));
        } else if (r1 && !r2) {
            return RELATIVE + easilyAdd(stringNumber1.substring(1), stringNumber2);
        }
        String s1 = r1 ? stringNumber1.substring(1) : stringNumber1;
        String s2 = r2 ? stringNumber2.substring(1) : stringNumber2;
        int c = easilyCompare(s1, s2);
        if (c > 0) {
            if (r1) {
                return RELATIVE + easilySub(s1, s2);
            } else {
                return easilySub(s1, s2);
            }
        } else if (c < 0) {
            if (r2) {
                return easilySub(s2, s1);
            } else {
                return RELATIVE + easilySub(s2, s1);
            }
        }
        return "0";
    }

    public static String sub(String stringNumber) {
        if (stringNumber.contains(VALUE_INFINITY)) {
            return stringNumber;
        }
        if (stringNumber.equals(StringNumberUtil.ZERO)) {
            return "-1";
        }
        if (stringNumber.startsWith(RELATIVE)) {
            return RELATIVE + easilyAdd(stringNumber.substring(1));
        } else {
            return easilySub(stringNumber);
        }
    }

    public static String mul(String stringNumber1, String stringNumber2) {
        boolean r1 = stringNumber1.startsWith(RELATIVE);
        boolean r2 = stringNumber2.startsWith(RELATIVE);
        String s1 = r1 ? stringNumber1.substring(1) : stringNumber1;
        String s2 = r2 ? stringNumber2.substring(1) : stringNumber2;
        if (VALUE_INFINITY.equals(s1) || VALUE_INFINITY.equals(s2)) {
            return r1 == r2 ? VALUE_INFINITY : RELATIVE + VALUE_INFINITY;
        }
        if (ZERO.equals(s1) || ZERO.equals(s2)) {
            return ZERO;
        }
        return (r1 == r2 ? "" : RELATIVE) + easilyMul(s1, s2);
    }
}
