import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ConsumerBeh extends TickerBehaviour {
    private Time time;
    private ParsConfig getConfig;//private+name
    private double requementPower;
    private int period;
    private JsonCons jsonCons = new JsonCons();
    private int StartTime;

    //    List<Double> pow=Arrays.asList(1.0,2.4,3.7,4.0,5.8);
    public ConsumerBeh(Agent a, int period, Time time, ParsConfig getConfig) {
        super(a, period);
        this.time = time;
        this.getConfig = getConfig;
        this.period = period;

    }


    @Override
    protected void onTick() {
        StartTime = time.getCurrentTime();
//        requementPower = 1.0;
        requementPower = getConfig.pow(time.getCurrentTime() / 60 %24, myAgent.getLocalName() + ".xml");
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);//имена сообщениям
        message.setContent(String.valueOf(requementPower));
        message.setProtocol("NeedAuction");
        message.setOntology("All");
        message.addReceiver(myAgent.getAID("Distributor"));
        myAgent.send(message);
//        System.out.println(message);

        myAgent.addBehaviour(new Behaviour() {//+comment
            boolean flag = false;

            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.MatchProtocol("End");
                ACLMessage receivedMsg = myAgent.receive(mt);

                if (receivedMsg != null) {
                    Map<String, String> data;

                    if (!(receivedMsg.getContent().equals("No"))) {
                        System.out.println(myAgent.getLocalName() + " купил за  " + receivedMsg.getContent());
                        System.out.println("");
                        data = jsonCons.data(Double.parseDouble(receivedMsg.getContent()),
                                Double.parseDouble(receivedMsg.getOntology()),
                                "Ok", myAgent.getLocalName(), time.getCurrentTime() / 60);

                        flag = true;
                    } else {
                        System.out.println(myAgent.getLocalName() + " не купил ");
                        System.out.println("");
                        data = jsonCons.data(0.0,
                                Double.parseDouble(receivedMsg.getOntology()),
                                "No", myAgent.getLocalName(), time.getCurrentTime() / 60);
//                        System.out.println(time.getCurrentTime()+"  "+StartTime+"  "+myAgent.getLocalName()+"  "+period);
                        if (time.getCurrentTime() - StartTime < 0.75 * period/1000) {

//                        запрос минимальной мощности
//                        flag = true;
                            try {
                                Thread.sleep(time.minute*5);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                            message.setContent(String.valueOf(requementPower));
                            message.addReceiver(myAgent.getAID("Distributor"));
                            message.setProtocol("NeedAuction");
                            message.setOntology("All");
//                            message.addReceiver(myAgent.getAID("Distributor"));
                            myAgent.send(message);

                        } else {
                            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                            message.setContent(String.valueOf(requementPower));
                            message.addReceiver(myAgent.getAID("Distributor"));
                            message.setProtocol("NeedAuction");
                            message.setOntology("System");
                            myAgent.send(message);
//                            flag=true;
                        }
//                        что-то с выходом 1 раз запросить, если не совсем вышло, то в систему

                    }

                    String stroka = jsonCons.stroka(data, myAgent.getLocalName());
                    String fileName = String.format("C:\\Users\\anna\\IdeaProjects\\imp\\%s.json", myAgent.getLocalName());
                    try {
                        FileOutputStream file = new FileOutputStream(fileName);
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
