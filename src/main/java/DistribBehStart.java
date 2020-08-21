import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.behaviours.Behaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;

public class DistribBehStart extends Behaviour {
    //    int length=;
//    Double[][] price=new Double[4][2];
    double minprice1, minprice2;
    AID agent;
    public int count1 = 0, count2 = 0, kolvo1=0,kolvo2=0;
    double pow;
    GenInf power;
boolean flag=false;
    json json=new json();
    Time time;

    public DistribBehStart(GenInf power,Time time) {
        this.power = power;
        this.time=time;
    }

    @Override
    public void action() {

        MessageTemplate mt = MessageTemplate.MatchProtocol("NeedAuction");
        ACLMessage receivedMsg = myAgent.receive(mt);
        AID[] resultsAID;
        resultsAID = DfSearch("Generation");
//        kolvo1 = 0;
        if (receivedMsg != null) {

            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
            AID topic = new Topic(myAgent).createTopic(receivedMsg.getSender().getLocalName() + "t"); //дописать
            topic = new Topic(myAgent).subsTopic(topic.getLocalName());
//            power.setAll(topic.getLocalName());

            for (AID aid : resultsAID) {
                message.addReceiver(aid);
                //                System.out.println(message);
            }
            message.setProtocol("Start");
            message.setContent(topic.getLocalName());
            myAgent.send(message);
            System.out.println(receivedMsg.getSender().getLocalName()+"  запросил  "+receivedMsg.getContent());
//            System.out.println(receivedMsg);
            myAgent.addBehaviour(new DistribContinue(topic,resultsAID,receivedMsg.getSender().getLocalName(),
                    Double.parseDouble(receivedMsg.getContent()),power,json,time));
//            flag=true;
        }
    }
    @Override
    public boolean done() {
        return flag;
    }


    public AID[] DfSearch(String type) {
        DFAgentDescription dfc = new DFAgentDescription();
        ServiceDescription dfs = new ServiceDescription();
        dfs.setType(type);
        dfc.addServices(dfs);

        DFAgentDescription[] results = new DFAgentDescription[0];
        try {
            results = DFService.search(myAgent, dfc);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        AID[] resultsAID = new AID[results.length];
        for (int i = 0; i < results.length; i++) {
            resultsAID[i] = results[i].getName();
        }

        return resultsAID;
    }

////                    System.out.println(message);
////                    try {
////                        Thread.sleep(100);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
//                    ACLMessage message1 = new ACLMessage(ACLMessage.INFORM);
//                    for (AID aid : resultsAID) {
//                        message1.addReceiver(aid);
//                        //                System.out.println(message);
//                    }
//                    message1.setProtocol("Power");
//                    message1.setContent(receivedMsg.getContent());
////                    pow = Double.parseDouble(receivedMsg.getContent());
//                    myAgent.send(message1);
////                    System.out.println(message1);
//
//                    break;
//                }
//                case "Price": {
//                    switch (receivedMsg.getOntology()) {
//                        case "Consumer1t": {
//                            String agen="Consumer1";
////                            System.out.println(receivedMsg);
//                            if (receivedMsg.getContent().equals("Left")) kolvo1++;
//                            else {
//
//                                if (count1 == 0 && kolvo1 < resultsAID.length) {
//                                    minprice1 = Double.parseDouble(receivedMsg.getContent());
//                                    agent = receivedMsg.getSender();
//                                    count1++;
//                                }
//                                if (count1 != 0 && kolvo1 < resultsAID.length) {
//                                    if (minprice1 > (Double.parseDouble(receivedMsg.getContent()))) {
//                                        minprice1 = (Double.parseDouble(receivedMsg.getContent()));
//                                        agent = receivedMsg.getSender();
//                                    }
//                                    count1++;
//                                }
//                            }
////        System.out.println(kolvo + "   " + receivedMsg.getOntology());
////        count1++;
//                            if (kolvo1 == resultsAID.length) {
//                                ACLMessage message1 = new ACLMessage(ACLMessage.INFORM);
//                                message1.addReceiver(myAgent.getAID(agen));
//                                message1.setProtocol("End");
////                                message1.setOntology(topic.getLocalName());
//                                message1.setContent("No");
//                                myAgent.send(message1);
////            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
////            for (AID aid : resultsAID) {
////                message.addReceiver(aid);
////                //                System.out.println(message);
////            }
////            message.setProtocol("Winner");
////            message.setContent("-");
////            myAgent.send(message);
//
//                            }
//                            if (count1 == (resultsAID.length - kolvo1) && kolvo1 < resultsAID.length) {
////                                System.out.println(minprice1 + "   dis1    "+agent.getLocalName());
//                                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
//                                for (AID aid : resultsAID) {
//                                    message.addReceiver(aid);
//                                    //                System.out.println(message);
//                                }
//                                message.setProtocol("Winner");
//                                message.setOntology(String.valueOf(pow));
//                                message.setContent(agent.getLocalName());
//                                myAgent.send(message);
//                                System.out.println(kolvo1+"   "+receivedMsg.getOntology());
//                                count1 = 0;
//                                kolvo1 = 0;
//                                ACLMessage message1 = new ACLMessage(ACLMessage.INFORM);
//                                message1.addReceiver(myAgent.getAID(agen));
//                                message1.setProtocol("End");
////                                message1.setOntology(topic.getLocalName());
//                                message1.setContent(String.valueOf(minprice1));
//                                myAgent.send(message1);
//                            }
//                        }
//                        case "Consumer2t": {
//                            String agen="Consumer2";
////                            System.out.println(receivedMsg);
//                            if (receivedMsg.getContent().equals("Left")) kolvo2++;
//                            else {
//
//                                if (count2 == 0 && kolvo2 < resultsAID.length) {
//                                    minprice2 = Double.parseDouble(receivedMsg.getContent());
//                                    agent = receivedMsg.getSender();
//                                    count1++;
//                                }
//                                if (count2 != 0 && kolvo2 < resultsAID.length) {
//                                    if (minprice2 > (Double.parseDouble(receivedMsg.getContent()))) {
//                                        minprice2 = (Double.parseDouble(receivedMsg.getContent()));
//                                        agent = receivedMsg.getSender();
//                                    }
//                                    count2++;
//                                }
//                            }
////        System.out.println(kolvo + "   " + receivedMsg.getOntology());
////        count1++;
//                            if (kolvo2 == resultsAID.length) {
//                                ACLMessage message1 = new ACLMessage(ACLMessage.INFORM);
//                                message1.addReceiver(myAgent.getAID(agen));
//                                message1.setProtocol("End");
////                                message1.setOntology(topic.getLocalName());
//                                message1.setContent("No");
//                                myAgent.send(message1);
////            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
////            for (AID aid : resultsAID) {
////                message.addReceiver(aid);
////                //                System.out.println(message);
////            }
////            message.setProtocol("Winner");
////            message.setContent("-");
////            myAgent.send(message);
//
//                            }
//                            if (count2 == (resultsAID.length - kolvo2) && kolvo2 < resultsAID.length) {
////                                System.out.println(minprice1 + "   dis1    "+agent.getLocalName());
//                                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
//                                for (AID aid : resultsAID) {
//                                    message.addReceiver(aid);
//                                    //                System.out.println(message);
//                                }
//                                message.setProtocol("Winner");
//                                message.setOntology(String.valueOf(pow));
//                                message.setContent(agent.getLocalName());
//                                myAgent.send(message);
//                                System.out.println(kolvo2+"  "+receivedMsg.getOntology());
//                                count2 = 0;
//                                kolvo2 = 0;
//                                ACLMessage message1 = new ACLMessage(ACLMessage.INFORM);
//                                message1.addReceiver(myAgent.getAID(agen));
//                                message1.setProtocol("End");
////                                message1.setOntology(topic.getLocalName());
//                                message1.setContent(String.valueOf(minprice1));
//                                myAgent.send(message1);
//                            }
//                        }
////
//                    }
//                    break;
//                }
//            }
//
////            System.out.println(message1);
//        } else {
//            block();
//        }
//    }
//

//    public void Price(ACLMessage receivedMsg, AID[] resultsAID, String agen) {
//        System.out.println(receivedMsg);
//        if (receivedMsg.getContent().equals("Left")) kolvo++;
//        else {
//
//            if (count1 == 0 && kolvo < resultsAID.length) {
//                minprice1 = Double.parseDouble(receivedMsg.getContent());
//                agent = receivedMsg.getSender();
//                count1++;
//            }
//            if (count1 != 0 && kolvo < resultsAID.length) {
//                if (minprice1 > (Double.parseDouble(receivedMsg.getContent()))) {
//                    minprice1 = (Double.parseDouble(receivedMsg.getContent()));
//                    agent = receivedMsg.getSender();
//                }
//                count1++;
//            }
//        }
////        System.out.println(kolvo + "   " + receivedMsg.getOntology());
////        count1++;
//        if (kolvo == resultsAID.length) {
//            ACLMessage message1 = new ACLMessage(ACLMessage.INFORM);
//            message1.addReceiver(myAgent.getAID(agen));
//            message1.setProtocol("End");
////                                message1.setOntology(topic.getLocalName());
//            message1.setContent("No");
//            myAgent.send(message1);
////            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
////            for (AID aid : resultsAID) {
////                message.addReceiver(aid);
////                //                System.out.println(message);
////            }
////            message.setProtocol("Winner");
////            message.setContent("-");
////            myAgent.send(message);
//
//        }
//        if (count1 == (resultsAID.length - kolvo) && kolvo < resultsAID.length) {
////                                System.out.println(minprice1 + "   dis1    "+agent.getLocalName());
//            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
//            for (AID aid : resultsAID) {
//                message.addReceiver(aid);
//                //                System.out.println(message);
//            }
//            message.setProtocol("Winner");
//            message.setOntology(String.valueOf(pow));
//            message.setContent(agent.getLocalName());
//            myAgent.send(message);
//
//            count1 = 0;
//            kolvo = 0;
//            ACLMessage message1 = new ACLMessage(ACLMessage.INFORM);
//            message1.addReceiver(myAgent.getAID(agen));
//            message1.setProtocol("End");
////                                message1.setOntology(topic.getLocalName());
//            message1.setContent(String.valueOf(minprice1));
//            myAgent.send(message1);
//        }
////                            System.out.println(agent.getLocalName()+"   1");
////                            }
//    }

}


