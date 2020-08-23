import java.util.HashMap;
import java.util.Map;

public class GenerationInfo {
    //    public ArrayList<Double> pri = new ArrayList<>();
//    public ArrayList<Double> nakopit = new ArrayList<>();
    //    public double nakop = 0;
//    public double minpri = 4, maxnakop = 10;
    private Map<String, Double> MapWithMinPrice = new HashMap<>();
    private Map<String, Double> MapWithMaxNakopSize = new HashMap<>();
    private Map<String, Double> MapWithCurrentNakop = new HashMap<>();
    private Map<String, Double> MapWithActualPower = new HashMap<>();
    private Map<String, Integer> MapWithLastAskTime = new HashMap<>();
    private Map<String, Double> MapWithReservePower = new HashMap<>();
    private int StartTime;


    public GenerationInfo(int startTime) {
        StartTime = startTime;
    }

    public void setAll() {

        String agent = "Sun";
        MapWithMinPrice.put(agent, 2.0);
        MapWithMaxNakopSize.put(agent, 34.0);
        MapWithCurrentNakop.put(agent, 2.0);
        MapWithLastAskTime.put(agent, StartTime);
        MapWithActualPower.put(agent, 0.0);
        MapWithReservePower.put(agent, 0.0);

        agent = "Wind";
        MapWithMinPrice.put(agent, 4.5);
        MapWithMaxNakopSize.put(agent, 36.0);
        MapWithCurrentNakop.put(agent, 2.0);
        MapWithLastAskTime.put(agent, StartTime);
        MapWithActualPower.put(agent, 0.0);
        MapWithReservePower.put(agent, 0.0);

        agent = "Heat";
        MapWithMinPrice.put(agent, 6.0);
        MapWithMaxNakopSize.put(agent, 7.0);
        MapWithCurrentNakop.put(agent, 7.0);
        MapWithLastAskTime.put(agent, StartTime);
        MapWithActualPower.put(agent, 0.0);
        MapWithReservePower.put(agent, 0.0);

        agent = "System";
        MapWithMinPrice.put(agent, 9.0);
        MapWithMaxNakopSize.put(agent, 300.0);
        MapWithCurrentNakop.put(agent, 300.0);
        MapWithLastAskTime.put(agent, StartTime);
        MapWithActualPower.put(agent, 0.0);
        MapWithReservePower.put(agent, 0.0);
    }

//

    public String FormPrice(int time, String agent, double request) {//лажа (переползать в минуты)  комментарии о содержании

        double power = 0;
        if ((time / 60 - MapWithLastAskTime.get(agent) / 60) >= 1) {
            MapWithActualPower.put(agent, new GenHour().PowHour(agent, time / 60) / 60);
            if ((agent.equals("System") || agent.equals("Heat"))) {
                MapWithCurrentNakop.replace(agent, 0.0);
                power = MapWithActualPower.get(agent) * 60;

            }
        }

        if (!(agent.equals("System") || agent.equals("Heat"))) {
            while (time - MapWithLastAskTime.get(agent) < 0) {
                time += 24 * 60;
            }
            power = MapWithActualPower.get(agent) * (time - MapWithLastAskTime.get(agent));
            System.out.println(power+"  "+agent+"   "+time+"   "+MapWithLastAskTime.get(agent));
        }
        MapWithCurrentNakop.replace(agent, MapWithCurrentNakop.get(agent) + power);
        if (MapWithCurrentNakop.get(agent) > MapWithMaxNakopSize.get(agent))
            MapWithCurrentNakop.replace(agent, MapWithMaxNakopSize.get(agent));

        MapWithLastAskTime.replace(agent, time);
        double currentPower = (MapWithCurrentNakop.get(agent) - MapWithReservePower.get(agent));
        if (request <= (currentPower)) {
            return String.valueOf(MapWithMinPrice.get(agent) * MapWithMaxNakopSize.get(agent) / currentPower * request);
        } else {
            return "Left";
        }
    }

    public double allPower(String agent) {
        return MapWithCurrentNakop.get(agent);
    }

    public void reservePower(double elem, String agent) {
        MapWithReservePower.put(agent, MapWithReservePower.get(agent) + elem);
//        System.out.println(DopPower.get(agent));
    }

    public void minPower(double elem, String agent) {
//        System.out.println(nakop.get(agent)+" before "+agent);
        MapWithCurrentNakop.replace(agent, MapWithCurrentNakop.get(agent) - elem);
//        System.out.println(nakop.get(agent)+" after "+agent);
    }

//        System.out.println(Power.get(agent)*60+"  "+agent+"  "+time);
//        System.out.println(nakop.get(agent) + "  " + agent + "  " + DopPower.get(agent));
//        System.out.println(currentPower+"  "+agent);
//        System.out.println(LastAsk.get(agent) + "  time   " + time);
//        nakopit.set(agent), nakopit.get(number(agent)) - elem);
//        System.out.println(nakopit.get(number(agent))+"  after");
//    } //        System.out.println(LastAsk);
//     //        System.out.println(LastAsk.get(agent)+"  "+agent);   System.out.println(LastAsk.get(topic) + "  time   " + time);
    ///        System.out.println(Power.get(agent) *60+"   "+agent);/            System.out.println(nakop.get("System")+"  nakop");
    //         public double price(int time, String agent) {
    //////минцена разная для всех
    ////        return MapWithMinPrice.get(agent) * MapWithMaxNakopSize.get(agent) / MapWithCurrentNakop.get(agent);
    ////    }   System.out.println(LastAsk.get("System")+"  time   "+time);
//    //    public String agent
//    publicint number(String agent) {
//        int number = 0;
//        switch (agent) {
//            case "Sun": {
//                number = 0;
//                break;
//            }
//            case "Wind": {
//                number = 1;
//                break;
//            }
//            case "Heat": {
//                number = 2;
//                break;
//            }
//            case "System": {
//                number = 3;
//                break;
//            }
//        }
//        return number;
//    }
//
//    public void setmas() {
//        for (int i = 0; i < 4; i++)
//            nakopit.add(0.0);
//    }
//
//    public double minprice(String agent) {
//        double minprice = 0;
//        switch (agent) {
//            case "Sun": {
//                minprice = 2;
//                break;
//            }
//            case "Wind": {
//                minprice = 4;
//                break;
//            }
//            case "Heat": {
//                minprice = 7;
//                break;
//            }
//            case "System": {
//                minprice = 3;
//                break;
//            }
//        }
//        return minprice;
//    }
//
//
//    //имя агента в конс
//
//
//    public void setPri() {
//        for (int i = 0; i < 24; i++) {
//            pri.add(Math.random() + 2);
//        }
//    }
//
//    public double pri(int time) {
//        return pri.get(time);
//    }
//
//
////    public void setPow() {
////        for (int i = 0; i < 24; i++) {
////            pow.add(Math.random()*100);
////        }
////    }
////
////    public double pow(int time) {
////        return pow.get(time);
////    }
//
//

//    public double maxnakop(String agent) {
//        double maxnakop = 0;
//        switch (agent) {
//            case "Sun": {
//                maxnakop = 2;
//                break;
//            }
//            case "Wind": {
//                maxnakop = 4;
//                break;
//            }
//            case "Heat": {
//                maxnakop = 7;
//                break;
//            }
//            case "System": {
//                maxnakop = 3;
//                break;
//            }
//        }
//        return maxnakop;
//    }

}
