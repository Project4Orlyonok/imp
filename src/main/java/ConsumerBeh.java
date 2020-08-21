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
    Const poww;
    double pow;

    //    List<Double> pow=Arrays.asList(1.0,2.4,3.7,4.0,5.8);
    public ConsumerBeh(Agent a, int period, Time time,Const poww) {
        super(a, period);
        this.time = time;
        this.poww=poww;

    }

    @Override
    public void onStart() {
//        poww.setPow();
        super.onStart();
    }

    @Override
    protected void onTick() {

        pow = poww.pow(time.getCurrentTime()/60,myAgent.getLocalName()+".xml");
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
                    if (!(receivedMsg.getContent().equals("No"))) {
                        System.out.println(myAgent.getLocalName() + " купил за  " + receivedMsg.getContent());
                        System.out.println("");
                        flag = true;
                    }
                    else  {
                        System.out.println(myAgent.getLocalName() + " не купил ");
                        System.out.println("");
//                        запрос минимальной мощности
                        flag = true;
                    }
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
