import jade.core.Agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GenInf {
    //    public ArrayList<Double> pri = new ArrayList<>();
//    public ArrayList<Double> nakopit = new ArrayList<>();
    //    public double nakop = 0;
//    public double minpri = 4, maxnakop = 10;
    Map<String, Double> minprice = new HashMap<>();
    Map<String, Double> maxnakop = new HashMap<>();
    Map<String, Double> nakop = new HashMap<>();
    Map<String, Double> Power = new HashMap<>();
    public Map<String, Integer> LastAsk = new HashMap<>();
    public Map<String, Double> DopPower = new HashMap<>();
    int Starttime;


    public GenInf(int starttime) {
        Starttime = starttime;
    }

    public void setAll(String topic) {
        String agent = "";
//        LastAsk.put(topic, Starttime);

        agent = "Sun";
        minprice.put(agent, 2.0);
        maxnakop.put(agent, 5.0);
        nakop.put(agent, 2.0);
        LastAsk.put(agent, Starttime);
        Power.put(agent, 0.0);
        DopPower.put(agent, 0.0);

        agent = "Wind";
        minprice.put(agent, 2.5);
        maxnakop.put(agent, 5.0);
        nakop.put(agent, 2.0);
        LastAsk.put(agent, Starttime);
        Power.put(agent, 0.0);
        DopPower.put(agent, 0.0);

        agent = "Heat";
        minprice.put(agent, 6.0);
        maxnakop.put(agent, 5.0);
        nakop.put(agent, 0.0);
        LastAsk.put(agent, Starttime);
        Power.put(agent, 0.0);
        DopPower.put(agent, 0.0);

        agent = "System";
        minprice.put(agent, 9.0);
        maxnakop.put(agent, 5.0);
        nakop.put(agent, 0.0);
        LastAsk.put(agent, Starttime);
        Power.put(agent, 0.0);
        DopPower.put(agent, 0.0);
    }

    public double price(int time, String agent) {
//минцена разная для всех
        return minprice.get(agent) * maxnakop.get(agent) / nakop.get(agent);
    }

    public String power(int time, String agent, double zapros) {//лажа (переползать в минуты)

        double power;
        if ((time / 60 - LastAsk.get(agent) / 60) >= 1) {
            Power.put(agent, new GenHour().PowHour(agent, time / 60) / 60);
            if ((agent.equals("System") || agent.equals("Heat"))) {
                nakop.replace(agent, 0.0);
            }
        }
//        System.out.println(Power.get(agent)*60+"  "+agent+"  "+time);
//        System.out.println(LastAsk.get(agent) + "  time   " + time);
        power = Power.get(agent) * (time - LastAsk.get(agent));
//        System.out.println(Power.get(agent) *60+"   "+agent);
        nakop.replace(agent, nakop.get(agent) + power);
        if (nakop.get(agent) > maxnakop.get(agent))
            nakop.replace(agent, maxnakop.get(agent));
//        System.out.println(LastAsk.get(agent)+"  "+agent);
        LastAsk.replace(agent, time);
        double currentpower=(nakop.get(agent) - DopPower.get(agent));
//        System.out.println(nakop.get(agent) + "  " + agent + "  " + DopPower.get(agent));
//        System.out.println(currentpower+"  "+agent);
        if (zapros < (currentpower)) {
            return String.valueOf(minprice.get(agent) * maxnakop.get(agent)/currentpower*zapros);
        } else {
            return "Left";
        }
    }

    public double allpower(String agent) {
        return nakop.get(agent);
    }

    public void minpower(double elem, String agent) {
        DopPower.put(agent, elem);
//        System.out.println(DopPower.get(agent));
    }

    public void powmin(double elem, String agent) {
//        System.out.println(nakop.get(agent)+" before "+agent);
        nakop.replace(agent, nakop.get(agent) - elem);
//        System.out.println(nakop.get(agent)+" after "+agent);
    }


//        nakopit.set(agent), nakopit.get(number(agent)) - elem);
//        System.out.println(nakopit.get(number(agent))+"  after");
//    } //        System.out.println(LastAsk);
//        System.out.println(LastAsk.get(topic) + "  time   " + time);
    //            System.out.println(nakop.get("System")+"  nakop");
    //            System.out.println(LastAsk.get("System")+"  time   "+time);
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

    public double maxnakop(String agent) {
        double maxnakop = 0;
        switch (agent) {
            case "Sun": {
                maxnakop = 2;
                break;
            }
            case "Wind": {
                maxnakop = 4;
                break;
            }
            case "Heat": {
                maxnakop = 7;
                break;
            }
            case "System": {
                maxnakop = 3;
                break;
            }
        }
        return maxnakop;
    }

}
