import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ConsumerBeh extends TickerBehaviour {
    public Time time;
    Const poww;
    double pow;
    JsonCons jsonCons=new JsonCons();
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

                    Map<String,String> data;
                    if (!(receivedMsg.getContent().equals("No"))) {
                        System.out.println(myAgent.getLocalName() + " купил за  " + receivedMsg.getContent());
                        System.out.println("");
                        data= jsonCons.data(Double.parseDouble(receivedMsg.getContent()),
                                Double.parseDouble(receivedMsg.getOntology()),
                                "Ok",myAgent.getLocalName(),time.getCurrentTime()/60);
                        flag = true;
                    }
                    else  {
                        System.out.println(myAgent.getLocalName() + " не купил ");
                        System.out.println("");
                        data= jsonCons.data(0.0,
                                Double.parseDouble(receivedMsg.getOntology()),
                                "No",myAgent.getLocalName(),time.getCurrentTime()/60);
//                        запрос минимальной мощности
                        flag = true;
                    }
                    String stroka = jsonCons.stroka(data);
                    String fileName =String.format("C:\\Users\\anna\\IdeaProjects\\imp\\%s.json",myAgent.getLocalName());
                    try {
                        FileOutputStream file =new FileOutputStream(fileName);
                        file.write(stroka.getBytes());
                        file.flush();
                        file.close();
                    } catch (IOException e) {
                        e.printStackTrace();
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
