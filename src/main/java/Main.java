import java.util.*;

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
//        System.out.println(cons.pow(2,"Consumer1.xml"));
        Map<String,Double> map=new HashMap<>();
        map.put("Heat",2.0);map.put("Sun",1.0);
        map.put("Wind",8.0);

        map.replace("Wind",7.2);
        map.put("Wind",7.4);
//        System.out.println(map);
        map.entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue().reversed());
//        System.out.println(map);
//        System.out.println(map.get("Wind"));
//        Double[][] powers=new Double[4][3];
//        for (int i=0;i<4;i++){
//            powers[i][0]= Double.valueOf(i+0.2);
//            powers[i][1]= Double.valueOf(i+0.1);
//            powers[i][2]= Double.valueOf(i+0.3);
//        }
//        for (int i=0;i<4;i++){
//            for (int i1=0;i1<3;i1++){
//                System.out.print(powers[i][i1]+" ");
//            }
//            System.out.println("");
//        }
//        System.out.println("");
////        Arrays.stream(powers).sorted();
//        for (int i=0;i<4;i++){
//            for (int i1=0;i1<3;i1++){
//                System.out.print(powers[i][i1]+" ");
//            }
//            System.out.println("");
//        }
//        List<List<Double>> powers=new ArrayList<>(4);
//        powers.add(3.9,5.7,2.9);
//        powers.add(2.9);
//        powers.add(5.9);
//        powers.add(9.9);
//        Collections.sort(powers);
//        System.out.println(powers);
//        System.out.println(Arrays.stream(powers).toArray());
//        GenInf gen=new GenInf("Heat");
//        System.out.println(gen.price(4));
//        System.out.println(gen.power(4));
    }
}
