import jade.core.Agent;

import java.util.ArrayList;

public class GenInf {
    public ArrayList<Double> pri = new ArrayList<>();
    public ArrayList<Double> pow = new ArrayList<>();
    public double nakop=0;
    public double minpri=4,maxnakop=10;
//    public String agent;



    //имя агента в конс
    public double price(int time,String agent){
//минцена разная для всех
        return minpri*maxnakop/nakop;
    }
    public double power(int time,String agent){
        if (agent.equals("System")||agent.equals("Heat")){
            nakop=0;
        }
        nakop+=new GenHour().PowHour(agent,time);
        if (nakop>maxnakop) nakop=maxnakop;
        return nakop;
    }

    public void setPri() {
        for (int i = 0; i < 24; i++) {
            pri.add(Math.random()+2);
        }
    }

    public double pri(int time) {
        return pri.get(time);
    }


    public void setPow() {
        for (int i = 0; i < 24; i++) {
            pow.add(Math.random()*100);
        }
    }

    public double pow(int time) {
        return pow.get(time);
    }

//    public void powmin(int time,double elem){
//        double lem=pow.get(time);
//        pow.remove(time);
//        pow.add(time,lem-elem);
//    }

}
