import jade.core.AID;
import jade.core.ServiceException;
import jade.core.behaviours.Behaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class GenBehStart extends Behaviour {
    boolean flag = false;//private+имена
    public AID topic;
    Time time;
    GenerationInfo power;
    JsonGen jsonGen=new JsonGen();

//    GenInf power = new GenInf();


    public GenBehStart(Time time, GenerationInfo power) {
        this.time = time;
        this.power=power;
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchProtocol("Start");
        ACLMessage receivedMsg = myAgent.receive(mt);
        if (receivedMsg != null) {
            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
            topic=subsTopic(receivedMsg.getContent());//уйти от этого
            message.addReceiver(topic);
            message.setProtocol("Ready");
            message.setContent("OK");
//            message.setOntology(receivedMsg.getOntology());
            myAgent.send(message);
//            System.out.println(message.getOntology()+"  genstart  "+topic.getLocalName());
            myAgent.addBehaviour(new GenBehDur(time,topic,  myAgent.getLocalName(),power,jsonGen));
//            power.setAll(topic.getLocalName());
            System.out.println(myAgent.getLocalName()+"  подписался   "+topic.getLocalName());
//            flag = true;
        }
    }

    @Override
    public boolean done() {
        return flag;
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
////        ACLMessage receivedMsg;
////      //    @Override
////    public int onEnd() {
////        myAgent.addBehaviour(new GenBehDur(topic));
////        return super.onEnd();
////    }  MessageTemplate mt = MessageTemplate.MatchProtocol("Start");
//        ACLMessage receivedMsg = myAgent.receive();
//        if (receivedMsg != null) {
//            switch (receivedMsg.getProtocol()) {
////                case "Power": {
////                    if (Double.parseDouble(receivedMsg.getContent()) <= power.power(time.getCurrentTime(), myAgent.getLocalName())) {
////                        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
////                        message.addReceiver(topic);
////                        message.setProtocol("Price");
////                        message.setOntology(topic.getLocalName());
////                        message.setContent(String.valueOf(power.price(time.getCurrentTime(), myAgent.getLocalName())));
////                        System.out.println(message.getContent() + "  " + topic.getLocalName());
////                        myAgent.send(message);
////                    }
////                    } else {
////                        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
////                        message.addReceiver(topic);
////                        message.setProtocol("Price");
////                        message.setOntology(topic.getLocalName());
////                        message.setContent("Left");
////                        myAgent.send(message);
////                    }
////                    break;
////                }
//
//                case "Winner": {
////                    flag=true;
////                    System.out.println(receivedMsg);
////                    power.powmin(time.getCurrentTime(), Double.parseDouble(receivedMsg.getContent()));
//
////                    System.out.println(receivedMsg.getContent());
//                    break;
//                }
//                case "Start": {
////                    System.out.println(receivedMsg);
//                    topic = subsTopic(receivedMsg.getContent());
////                    System.out.println(topic);
////                   отправку подтверждения подписки + выкинуть  в отдельное поведение->
//                    MessageTemplate mt = MessageTemplate.MatchProtocol("Power");//+топик
//                    ACLMessage receivedMsg1 = myAgent.receive(mt);
//                    if ((receivedMsg1 != null)) {
////                        System.out.println(receivedMsg1);
////                        System.out.println(receivedMsg1.getContent()+"   "+myAgent.getLocalName());
////                        System.out.println(Double.parseDouble(receivedMsg1.getContent())+"     "+power.power(time.getCurrentTime(), myAgent.getLocalName())+"    "+myAgent.getLocalName()+"   "+topic.getLocalName());
//                        if (Double.parseDouble(receivedMsg1.getContent()) <= power.power(time.getCurrentTime(), myAgent.getLocalName())) {
//
//                            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
//                            message.addReceiver(topic);
//                            message.setProtocol("Price");
//                            message.setOntology(topic.getLocalName());
//                            message.setContent(String.valueOf(power.price(time.getCurrentTime(), myAgent.getLocalName())));
////                        System.out.println(message.getContent()+"  "+topic.getLocalName());
//                            myAgent.send(message);
////                            System.out.println(message);
//                        }
//                        else{
//                            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
//                            message.addReceiver(topic);
//                            message.setProtocol("Price");
//                            message.setOntology(topic.getLocalName());
//                            message.setContent("Left");
////                        System.out.println(message.getContent()+"  "+topic.getLocalName());
//                            myAgent.send(message);
////                            System.out.println(message);
//                        }
//
//                    }
//                    break;
//                }
//            }
//
//
//        } else {
//            block();
//        }


