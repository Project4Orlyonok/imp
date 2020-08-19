import java.util.ArrayList;

public class GenInf {
    public ArrayList<Double> pri = new ArrayList<>();
    public ArrayList<Double> pow = new ArrayList<>();
    public double nakop;
    public double minpri,maxnakop;
//имя агента в конс
    public double price(int time){
//        nakop+=;
        if (nakop>maxnakop) nakop=maxnakop;
        return minpri*maxnakop/nakop;
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

    public void powmin(int time,double elem){
        double lem=pow.get(time);
        pow.remove(time);
        pow.add(time,lem-elem);
    }

}
