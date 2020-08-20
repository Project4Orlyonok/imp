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

public class DistribBeh extends Behaviour {
    //    int length=;
//    Double[][] price=new Double[4][2];
    double minprice1, minprice2;
    AID agent;
    public int count1 = 0, count2 = 0, kolvo;
    double pow;

    @Override
    public void action() {

//        MessageTemplate mt = MessageTemplate.MatchProtocol("NeedAuction");
        ACLMessage receivedMsg = myAgent.receive();
        AID[] resultsAID;
        resultsAID = DfSearch("Generation");
        kolvo = resultsAID.length;
        if (receivedMsg != null) {
            switch (receivedMsg.getProtocol()) {
                case "NeedAuction": {
                    ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                    AID topic = new Topic(myAgent).createTopic(receivedMsg.getSender().getLocalName() + "t"); //дописать
                    topic = new Topic(myAgent).subsTopic(topic.getLocalName());

                    for (AID aid : resultsAID) {
                        message.addReceiver(aid);
                        //                System.out.println(message);
                    }
                    message.setProtocol("Start");
                    message.setContent(topic.getLocalName());
                    myAgent.send(message);

                    ACLMessage message1 = new ACLMessage(ACLMessage.INFORM);
                    message1.addReceiver(topic);
                    message1.setProtocol("Power");
                    message1.setContent(receivedMsg.getContent());
                    pow = Double.parseDouble(receivedMsg.getContent());
                    myAgent.send(message1);


                    break;
                }
                case "Price": {
                    switch (receivedMsg.getOntology()) {
                        case "Consumer1t": {
                            Price(receivedMsg, resultsAID.length,"Consumer1");
                        }
                        case "Consumer2t": {
                            Price(receivedMsg, resultsAID.length,"Consumer2");
                        }
//
                    }
                }
            }

//            System.out.println(message1);
        } else {
            block();
        }
    }


    @Override
    public boolean done() {
        return false;
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

    public void Price(ACLMessage receivedMsg, int length, String agen) {
        if (receivedMsg.getContent().equals("Left")) kolvo--;
        else {
            if (count1 == 0) {
                minprice1 = Double.parseDouble(receivedMsg.getContent());
                agent = receivedMsg.getSender();
            } else {
                if (minprice1 > (Double.parseDouble(receivedMsg.getContent()))) {
                    minprice1 = (Double.parseDouble(receivedMsg.getContent()));
                    agent = receivedMsg.getSender();
                }
            }
            count1++;
            if (kolvo == 0) {
                ACLMessage message1 = new ACLMessage(ACLMessage.INFORM);
                message1.addReceiver(myAgent.getAID(agen));
                message1.setProtocol("End");
//                                message1.setOntology(topic.getLocalName());
                message1.setContent("No");
                myAgent.send(message1);
            } else {
                if (count1 == kolvo) {
//                                System.out.println(minprice1 + "   dis1    "+agent.getLocalName());
                    ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                    message.setContent(String.valueOf(pow));
                    message.setProtocol("Winner");
                    message.addReceiver(agent);
                    myAgent.send(message);
                    count2 = 0;
                    kolvo = length;
                    ACLMessage message1 = new ACLMessage(ACLMessage.INFORM);
                    message1.addReceiver(myAgent.getAID(agen));
                    message1.setProtocol("End");
//                                message1.setOntology(topic.getLocalName());
                    message1.setContent(String.valueOf(minprice1));
                    myAgent.send(message1);
                }
//                            System.out.println(agent.getLocalName()+"   1");
//                            }
            }
        }
    }

}
