import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ConsumerBeh extends TickerBehaviour {
    public Time time;
    Const poww = new Const();
    double pow;

    //    List<Double> pow=Arrays.asList(1.0,2.4,3.7,4.0,5.8);
    public ConsumerBeh(Agent a, long period, Time time) {
        super(a, period);
        this.time = time;

    }

    @Override
    public void onStart() {
        poww.setPow();
        super.onStart();
    }

    @Override
    protected void onTick() {

        pow = poww.pow(time.getCurrentTime(), myAgent.getLocalName()+".xml");
//        System.out.println(poww.pow(time.getCurrentTime(),myAgent.getLocalName()));
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.setContent(String.valueOf(pow));
        message.setProtocol("NeedAuction");
        message.addReceiver(myAgent.getAID("Distributor"));
        myAgent.send(message);
//        System.out.println(message);
        myAgent.addBehaviour(new Behaviour() {
            boolean flag = false;

            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.MatchProtocol("End");
                ACLMessage receivedMsg = myAgent.receive(mt);
                if (receivedMsg != null) {
                    System.out.println(myAgent.getLocalName()+ " купил за  " +receivedMsg.getContent()  );
                    System.out.println("");
                    flag = true;
                }
            }

            @Override
            public boolean done() {
                return flag;
            }
        });

//        else{
//            block();
//        }
//        ожидание сообщ от дистр о вышло/не вышло
    }
}
