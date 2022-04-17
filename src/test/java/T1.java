import java.util.ArrayList;
import java.util.List;

public class T1 {
    public static void main(String[] args) {
        List<List<Integer>> l = new ArrayList<>();
        l.add(new ArrayList<>());
        l.get(0);
    }
}
