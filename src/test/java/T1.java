import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class T1 {
    public static void main(String[] args) {
        int a = 1;
        int b = 9;
        String v = String.valueOf(((double)a) / b * 100.0);
        if(v.contains(".")) {
            v = v.substring(0, Math.min(v.indexOf(".") + 3, v.length()));
        }
        System.out.println(v + "%");
    }
}
