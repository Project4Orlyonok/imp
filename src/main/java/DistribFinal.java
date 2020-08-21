import jade.core.AID;
import jade.core.Agent;
import jade.core.MessageQueue;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class DistribFinal extends Behaviour {
    AID topic;
    AID[] resultsAID;
    String consumer;
    AID agent;
    double minprice = 1000.1;
    int count = 0,kolvo=0;
    double pow;
    boolean flag = false;
    json json;
    Time time;

    public DistribFinal(AID topic, AID[] resultsAID, String consumer,double pow,json json,Time time) {
        this.topic = topic;
        this.resultsAID = resultsAID;
        this.consumer = consumer;
        this.pow=pow;
        this.json=json;
        this.time=time;
    }


    @Override
    public void action() {
        String agen = consumer;
        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchProtocol("Price"), MessageTemplate.MatchTopic(topic));
        ACLMessage receivedMsg = myAgent.receive(mt);

        if (receivedMsg != null) {
//            System.out.println(receivedMsg.getSender().getLocalName()+"  предложил в   "+topic.getLocalName()+"  "
//                +receivedMsg.getOntology()+" за "+ receivedMsg.getContent());
            count++;

//                            System.out.println(receivedMsg);
            if (!(receivedMsg.getContent().equals("Left"))) {
                kolvo++;

                if (minprice > (Double.parseDouble(receivedMsg.getContent()))) {
                    minprice = (Double.parseDouble(receivedMsg.getContent()));
                    agent = receivedMsg.getSender();
//                        System.out.println(agent.getLocalName());
                }

            }
//            System.out.println(count + "   " + topic.getLocalName());
            if (count == resultsAID.length && 0!=kolvo) {
                ACLMessage message=new ACLMessage(ACLMessage.INFORM);
                message.setProtocol("Winner");
                message.addReceiver(topic);
                message.setOntology(String.valueOf(pow));
                message.setContent(agent.getLocalName());
                myAgent.send(message);
                System.out.println(topic.getLocalName() + "   выиграл  " + agent.getLocalName());
//                System.out.println("");
                Map<String,String> data= json.data(minprice,pow,agent.getLocalName(),agen,time.getCurrentTime());
                String stroka = json.stroka(data);
                String fileName ="C:\\Users\\anna\\IdeaProjects\\imp\\Cons.json";
                try {
                    FileOutputStream file =new FileOutputStream(fileName);
                    file.write(stroka.getBytes());
                    file.flush();
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ACLMessage message1 = new ACLMessage(ACLMessage.INFORM);
                message1.addReceiver(myAgent.getAID(agen));
                message1.setProtocol("End");
                message1.setContent(String.valueOf(minprice));
                myAgent.send(message1);
                flag = true;

            }
            if (kolvo==0 && count == resultsAID.length){
                ACLMessage message1=new ACLMessage(ACLMessage.INFORM);
                message1.setProtocol("Winner");
                message1.setOntology(String.valueOf(pow));
                message1.setContent(" ");
                message1.addReceiver(topic);
                myAgent.send(message1);

                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                message.addReceiver(myAgent.getAID(agen));
                message.setProtocol("End");
//                                message1.setOntology(topic.getLocalName());
                message.setContent("No");
                myAgent.send(message);
                System.out.println(topic.getLocalName() + "   недостаточно энергии");
                flag = true;
                System.out.println("");
            }
        }


//            System.out.println(kolvo1);
//        System.out.println(kolvo + "   " + receivedMsg.getOntology());
//        count1++;
//            if (kolvo1 == resultsAID.length) {
//                ACLMessage message1 = new ACLMessage(ACLMessage.INFORM);
//                message1.addReceiver(myAgent.getAID(agen));
//                message1.setProtocol("End");
////                                message1.setOntology(topic.getLocalName());
//                message1.setContent("No");
//                myAgent.send(message1);
//                System.out.println(topic.getLocalName() + "   недостаточно энергии");
//                flag = true;
//                System.out.println("");
//            }
//        } else {}
//            if (count1 == (resultsAID.length - kolvo1) && kolvo1 < resultsAID.length) {//лаааажаааа
////                                System.out.println(minprice1 + "   dis1    "+agent.getLocalName());
//                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
//                for (AID aid : resultsAID) {
//                    message.addReceiver(aid);
//                    //                System.out.println(message);
//                }
//                message.setProtocol("Winner");
//                message.setOntology(String.valueOf(pow));
//                message.setContent(agent.getLocalName());
//                myAgent.send(message);
//                System.out.println(topic.getLocalName() + "   выиграл  " + agent.getLocalName());
//                System.out.println("");
////                System.out.println(kolvo1+"   "+receivedMsg.getOntology());
//                count1 = 0;
//                kolvo1 = 0;
//                ACLMessage message1 = new ACLMessage(ACLMessage.INFORM);
//                message1.addReceiver(myAgent.getAID(agen));
//                message1.setProtocol("End");
////                                message1.setOntology(topic.getLocalName());
//                message1.setContent(String.valueOf(minprice1));
//                myAgent.send(message1);
//                flag = true;
//            }
    }


    @Override
    public boolean done() {
        return flag;
    }
}
//            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
//            for (AID aid : resultsAID) {
//                message.addReceiver(aid);
//                //                System.out.println(message);
//            }
//            message.setProtocol("Winner");
//            message.setContent("-");
//            myAgent.send(message);


//            if (count1 == (resultsAID.length - kolvo1) && kolvo1 < resultsAID.length) {//лаааажаааа
////                                System.out.println(minprice1 + "   dis1    "+agent.getLocalName());
//                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
//                for (AID aid : resultsAID) {
//                    message.addReceiver(aid);
//                    //                System.out.println(message);
//                }
//                message.setProtocol("Winner");
//                message.setOntology(String.valueOf(pow));
//                message.setContent(agent.getLocalName());
//                myAgent.send(message);
//                System.out.println(topic.getLocalName()+"   выиграл  "+agent.getLocalName());
//                System.out.println("");
////                System.out.println(kolvo1+"   "+receivedMsg.getOntology());
//                count1 = 0;
//                kolvo1 = 0;
//                ACLMessage message1 = new ACLMessage(ACLMessage.INFORM);
//                message1.addReceiver(myAgent.getAID(agen));
//                message1.setProtocol("End");
////                                message1.setOntology(topic.getLocalName());
//                message1.setContent(String.valueOf(minprice1));
//                myAgent.send(message1);
//                flag=true;
//            }


