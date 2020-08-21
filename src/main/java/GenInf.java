import jade.core.Agent;

import java.util.ArrayList;

public class GenInf {
    public ArrayList<Double> pri = new ArrayList<>();
    public ArrayList<Double> nakopit = new ArrayList<>();
    public double nakop = 0;
    public double minpri = 4, maxnakop = 10;

    //    public String agent
    public int number(String agent) {
        int number = 0;
        switch (agent) {
            case "Sun": {
                number = 0;
                break;
            }
            case "Wind": {
                number = 1;
                break;
            }
            case "Heat": {
                number = 2;
                break;
            }
            case "System": {
                number = 3;
                break;
            }
        }
        return number;
    }

    public void setmas(){
        for (int i=0;i<4;i++)
        nakopit.add(0.0);
    }

    public double minprice(String agent) {
        double minprice = 0;
        switch (agent) {
            case "Sun": {
                minprice = 2;
                break;
            }
            case "Wind": {
                minprice = 4;
                break;
            }
            case "Heat": {
                minprice = 7;
                break;
            }
            case "System": {
                minprice = 3;
                break;
            }
        }
        return minprice;
    }


    //имя агента в конс
    public double price(int time, String agent) {
//минцена разная для всех
        return minprice(agent) * maxnakop(agent) / nakopit.get(number(agent));
    }

    public double power(int time, String agent) {//лажа (переползать в минуты)
        if (agent.equals("System") || agent.equals("Heat")) {
            nakopit.set(number(agent), 0.0);
        }
        nakopit.set(number(agent), nakopit.get(number(agent)) + new GenHour().PowHour(agent, time));
        if (nakopit.get(number(agent)) > maxnakop(agent)) nakopit.set(number(agent), maxnakop(agent));
        return nakopit.get(number(agent));
    }

    public void setPri() {
        for (int i = 0; i < 24; i++) {
            pri.add(Math.random() + 2);
        }
    }

    public double pri(int time) {
        return pri.get(time);
    }


//    public void setPow() {
//        for (int i = 0; i < 24; i++) {
//            pow.add(Math.random()*100);
//        }
//    }
//
//    public double pow(int time) {
//        return pow.get(time);
//    }

    public void powmin(double elem, String agent) {
//        System.out.println(nakopit.get(number(agent))+"  before");
        nakopit.set(number(agent), nakopit.get(number(agent)) - elem);
//        System.out.println(nakopit.get(number(agent))+"  after");
    }

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
