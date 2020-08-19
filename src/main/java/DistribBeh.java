import jade.core.AID;
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
    public int count1 = 0, count2 = 0, kolvo = 4;
    double pow;

    @Override
    public void action() {

//        MessageTemplate mt = MessageTemplate.MatchProtocol("NeedAuction");
        ACLMessage receivedMsg = myAgent.receive();
        if (receivedMsg != null) {
            switch (receivedMsg.getProtocol()) {
                case "NeedAuction": {
                    ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                    AID topic = createTopic(receivedMsg.getSender().getLocalName() + "t"); //дописать
                    topic = new Topic(myAgent).subsTopic(topic.getLocalName());
                    AID[] resultsAID;
                    resultsAID = DfSearch("Generation", receivedMsg);
//                    length= resultsAID.length;


                    for (AID aid : resultsAID) {
                        message.addReceiver(aid);
                        message.setProtocol("Start");
                        message.setContent(topic.getLocalName());
                        myAgent.send(message);
//                System.out.println(message);
                    }
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
                                message1.addReceiver(myAgent.getAID("Consumer1"));
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
                                    count1 = 0;kolvo=4;
                                    ACLMessage message1 = new ACLMessage(ACLMessage.INFORM);
                                    message1.addReceiver(myAgent.getAID("Consumer1"));
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
                        case "Consumer2t": {
//                            if (receivedMsg.getContent().equals("Left")) kolvo--;
//                            else {
                            if (count2 == 0) {
                                minprice2 = Double.parseDouble(receivedMsg.getContent());
                                agent = receivedMsg.getSender();
                            } else {
                                if (minprice2 > (Double.parseDouble(receivedMsg.getContent()))) {
                                    minprice2 = (Double.parseDouble(receivedMsg.getContent()));
                                    agent = receivedMsg.getSender();
                                }
                            }
                            count2++;
                            if (kolvo == 0) {
                                ACLMessage message1 = new ACLMessage(ACLMessage.INFORM);
                                message1.addReceiver(myAgent.getAID("Consumer2"));
                                message1.setProtocol("End");
//                                message1.setOntology(topic.getLocalName());
                                message1.setContent("No");
                                myAgent.send(message1);
                            } else {
                                if (count2 == kolvo) {
//                                System.out.println(minprice2 + "   dis2   "+agent.getLocalName());
                                    ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                                    message.setContent(String.valueOf(pow));
                                    message.setProtocol("Winner");
                                    message.addReceiver(agent);
                                    myAgent.send(message);
                                    count2 = 0;kolvo=4;
                                    ACLMessage message1 = new ACLMessage(ACLMessage.INFORM);
                                    message1.addReceiver(myAgent.getAID("Consumer2"));
                                    message1.setProtocol("End");
//                                message1.setOntology(topic.getLocalName());
                                    message1.setContent(String.valueOf(minprice2));
                                    myAgent.send(message1);
                                }
//                            System.out.println(+"   2");
                            }
//                        }
                        }
//                    System.out.println(price[1][0]);

//                    System.out.println(receivedMsg.getOntology());
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


    public AID createTopic(String topicName) {
        TopicManagementHelper topicHelper;
        AID jadeTopic = null;
        try {
            topicHelper = (TopicManagementHelper)
                    myAgent.getHelper(TopicManagementHelper.SERVICE_NAME);
            jadeTopic = topicHelper.createTopic(topicName);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return jadeTopic;
    }

    public AID[] DfSearch(String type, ACLMessage receivedMsg) {
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
        AID[] resultsAID = new AID[results.length + 1];
        for (int i = 0; i < results.length; i++) {
            resultsAID[i] = results[i].getName();
        }
        resultsAID[resultsAID.length - 1] = receivedMsg.getSender();
        return resultsAID;
    }

    private AID subsTopic(String topicName) {
        TopicManagementHelper topicHelper = null;
        AID jadeTopic = null;
        try {
            topicHelper = (TopicManagementHelper)
                    myAgent.getHelper(TopicManagementHelper.SERVICE_NAME);
            jadeTopic = topicHelper.createTopic(topicName);
            topicHelper.register(jadeTopic);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return jadeTopic;
    }
}
