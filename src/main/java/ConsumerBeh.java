import jade.core.Agent;
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
        poww.setPow();
    }

    @Override
    protected void onTick() {
        if (myAgent.getLocalName().equals("Consumer1")) {
            double pow = poww.pow(time.getCurrentTime(), myAgent.getLocalName())+3;
        }
        else {
            double pow = poww.pow(time.getCurrentTime(), myAgent.getLocalName());
        }
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.setContent(String.valueOf(pow));
        message.setProtocol("NeedAuction");
        message.addReceiver(myAgent.getAID("Distributor"));
        myAgent.send(message);
        MessageTemplate mt = MessageTemplate.MatchProtocol("End");
        ACLMessage receivedMsg = myAgent.receive(mt);
        if (receivedMsg != null) {
            System.out.println(receivedMsg.getContent()+"   "+myAgent.getLocalName());
//            flag = true;
        }
//        ожидание сообщ от дистр о вышло/не вышло
    }
}
