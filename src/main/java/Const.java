import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Const {
    public ArrayList<Double> pow = new ArrayList<>();

    public void setPow() {
        for (int i = 0; i < 24; i++) {
            pow.add(Math.random()*100);
        }
    }

    public double pow(int time,String name) {
        Config config = WorkWithCfgs.unMarshalAny(Config.class,name);
        System.out.println(config);
        return pow.get(time);
    }
}
