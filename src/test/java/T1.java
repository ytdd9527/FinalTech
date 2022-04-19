import java.util.ArrayList;
import java.util.List;

public class T1 {
    public static void main(String[] args) {
        int i = 1;
        int j = 64;
        while (j > 0) {
            i++;
            j /= 2;
        }
        System.out.println(i);
    }
}
