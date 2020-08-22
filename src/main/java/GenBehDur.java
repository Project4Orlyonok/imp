import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class GenBehDur extends Behaviour {
    AID topic;
    GenInf power;
    Time time;
    boolean flag = false;
    JsonGen jsonGen;

    public GenBehDur(Time time, AID topic, String agent, GenInf power, JsonGen jsonGen) {
        this.time = time;
        this.topic = topic;
        this.power = power;
        this.jsonGen = jsonGen;
//        power.setAll(agent);
//        power.setPri();
//        power.setPow();
    }

    @Override
    public void onStart() {
//        power.setAll(topic.getLocalName());
        super.onStart();
    }

    @Override
    public void action() {
//        GenInf power=new GenInf(getAgent().getLocalName());
        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchProtocol("Power"), MessageTemplate.MatchTopic(topic));
        ACLMessage receivedMsg = myAgent.receive(mt);
        if ((receivedMsg != null)) {
//            power.powmin(Double.parseDouble(receivedMsg.getContent()),myAgent.getLocalName());
//            System.out.println(power.nakop.get(myAgent.getLocalName()));
//            double pow = power.power(time.getCurrentTime(), myAgent.getLocalName(), topic.getLocalName());
//            System.out.println(power.nakop.get(myAgent.getLocalName()));
//            System.out.println(receivedMsg.getContent() + "  "+topic.getLocalName());
            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
            message.addReceiver(topic);
            message.setProtocol("Price");
            message.setContent(power.power(time.getCurrentTime(),myAgent.getLocalName(), Double.parseDouble(receivedMsg.getContent())));
            System.out.println(myAgent.getLocalName()+" в "+topic.getLocalName()+" за "+message.getContent());
            myAgent.send(message);
            power.minpower(Double.parseDouble(receivedMsg.getContent()),myAgent.getLocalName());
            myAgent.addBehaviour(new GenBehFinal(topic, power, jsonGen, time));
            flag = true;
        }
    }

    @Override
    public boolean done() {
        return flag;
    }

}
// if(Double.parseDouble(receivedMsg.getContent())<=pow){
////                message.setOntology(String.valueOf(pow));
//                message.setContent(String.valueOf(power.price(time.getCurrentTime(), myAgent.getLocalName())));
//                System.out.println(myAgent.getLocalName()+"  предложил в   "+topic.getLocalName()+"  "
//                +message.getOntology()+" за "+ message.getContent());
//
//
//                power.minpower(Double.parseDouble(receivedMsg.getContent()),myAgent.getLocalName());
////                System.out.println(receivedMsg.getContent()+"  "+myAgent.getLocalName());
////                System.out.println(power.nakop.get(myAgent.getLocalName())+"  "+myAgent.getLocalName());
////                power.powmin(Double.parseDouble(receivedMsg.getContent()),myAgent.getLocalName());
//
//            } else {
//                //                message.setOntology(topic.getLocalName());
//                message.setContent("Left");
//                System.out.println(myAgent.getLocalName()+"  вышел из   "+topic.getLocalName());
//            }
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

