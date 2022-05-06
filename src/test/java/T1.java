import io.taraxacum.common.util.StringNumberUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class T1 {
    public static void main(String[] args) {
        System.out.println(StringNumberUtil.mul("123", "456"));
        System.out.println(StringNumberUtil.mul("123456789", "987654321"));
        System.out.println(StringNumberUtil.mul("6", "235230"));
        System.out.println(StringNumberUtil.mul("123", "0"));
    }
}
