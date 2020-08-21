import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class GenBehDur extends Behaviour {
    AID topic;
    GenInf power = new GenInf();
    Time time;
    boolean flag = false;

    public GenBehDur(Time time, AID topic) {
        this.time = time;
        this.topic = topic;
        power.setmas();
//        power.setPri();
//        power.setPow();
    }


    @Override
    public void action() {
//        GenInf power=new GenInf(getAgent().getLocalName());
        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchProtocol("Power"), MessageTemplate.MatchTopic(topic));
        ACLMessage receivedMsg = myAgent.receive(mt);
        if ((receivedMsg != null)) {
            double pow=power.power(time.getCurrentTime(), myAgent.getLocalName());
            if (Double.parseDouble(receivedMsg.getContent()) <= pow) {
                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                message.addReceiver(topic);
                message.setProtocol("Price");
                message.setOntology(topic.getLocalName());
                message.setContent(String.valueOf(power.price(time.getCurrentTime(), myAgent.getLocalName())));
                System.out.println(myAgent.getLocalName()+"  предложил в   "+topic.getLocalName()+"  "+
                        +pow+" за "+ message.getContent());
                myAgent.send(message);

            } else {
                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                message.addReceiver(topic);
                message.setProtocol("Price");
                message.setOntology(topic.getLocalName());
                message.setContent("Left");
                myAgent.send(message);
                System.out.println(myAgent.getLocalName()+"  вышел из   "+topic.getLocalName());
            }
            myAgent.addBehaviour(new GenBehFinal(topic,power));
            flag=true;
        }
    }

    @Override
    public boolean done() {
        return flag;
    }

}

//                    } else {
//                        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
//                        message.addReceiver(topic);
//                        message.setProtocol("Price");
//                        message.setOntology(topic.getLocalName());
//                        message.setContent("Left");
//                        myAgent.send(message);
//                    }
//                    break;
//                }
//
//                    case "Winner": {
//                        flag=true;
//                        System.out.println(receivedMsg);
////                    power.powmin(time.getCurrentTime(), Double.parseDouble(receivedMsg.getContent()));
//
////                    System.out.println(receivedMsg.getContent());
//                }
//            }
////            System.out.println(receivedMsg.getContent());
////            System.out.println(power.pow(time.getCurrentTime())*3+"    "+myAgent.getLocalName());
//
//
//        }
//    }

