import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Time time = new Time();
//        Thread some = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    System.out.println(time.getCurrentTime());
//                }
//            }
//        });
//        Time time=new Time();
//        some.start();

        Const cons=new Const();
        cons.setPow();
//        Config config=WorkWithCfgs.unMarshalAny(Config.class,"Consumer1.xml");
//        for (ConfigAtr con:config.getPower()){
//            System.out.println(con.getLoad());
//        }
        System.out.println(cons.pow(2,"Consumer1.xml"));
        Map<String,Double> map=new HashMap<>();
        map.put("Sun",1.0);
        map.put("Wind",8.0);
        map.put("Heat",2.0);
        System.out.println(map.get("Wind"));
//        GenInf gen=new GenInf("Heat");
//        System.out.println(gen.price(4));
//        System.out.println(gen.power(4));
    }
}
