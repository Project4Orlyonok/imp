import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParsConfig {
//    public ArrayList<Double> pow = new ArrayList<>();

//    public void setPow() {
//        for (int i = 0; i < 24; i++) {
//            pow.add(1.0);
//        }
//
//    }

    public double pow(int time,String name) {
        Config config = WorkWithCfgs.unMarshalAny(Config.class,name);
        List<ConfigAtr> conf= config.getPower();
//        System.out.println(conf.get(time).getLoad());
        return conf.get(time).getLoad();

    }
}
