import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Const {
    public ArrayList<Double> pow = new ArrayList<>();

    public void setPow() {
        for (int i = 0; i < 24; i++) {
            pow.add(Math.random());
        }
    }

    public double pow(int time) {
        return pow.get(time);
    }
}
