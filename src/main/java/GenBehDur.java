import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class GenBehDur extends Behaviour {
    AID topic;
    GenInf power = new GenInf();
    Time time;

    public GenBehDur(Time time, AID topic) {
        this.time = time;
        this.topic = topic;
//        power.setPri();
//        power.setPow();
    }


    @Override
    public void action() {
//        GenInf power=new GenInf(getAgent().getLocalName());
//        MessageTemplate mt=MessageTemplate.and(MessageTemplate.MatchProtocol("Power"),MessageTemplate.MatchTopic(topic));
        ACLMessage receivedMsg = myAgent.receive();
        if ((receivedMsg != null)) {
            switch (receivedMsg.getProtocol()) {
                case "Power": {
                    if (Double.parseDouble(receivedMsg.getContent()) <= power.power(time.getCurrentTime(),myAgent.getLocalName())) {
                        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                        message.addReceiver(topic);
                        message.setProtocol("Price");
                        message.setOntology(topic.getLocalName());
                        message.setContent(String.valueOf(power.price(time.getCurrentTime(),myAgent.getLocalName())));
//                        System.out.println(message.getContent()+"  "+topic.getLocalName());
                        myAgent.send(message);
                    }
//                    } else {
//                        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
//                        message.addReceiver(topic);
//                        message.setProtocol("Price");
//                        message.setOntology(topic.getLocalName());
//                        message.setContent("Left");
//                        myAgent.send(message);
//                    }
                    break;
                }

                    case "Winner": {
//                    power.powmin(time.getCurrentTime(), Double.parseDouble(receivedMsg.getContent()));

//                    System.out.println(receivedMsg.getContent());
                }
            }
//            System.out.println(receivedMsg.getContent());
//            System.out.println(power.pow(time.getCurrentTime())*3+"    "+myAgent.getLocalName());


        }
    }

    @Override
    public boolean done() {
        return false;
    }

}
