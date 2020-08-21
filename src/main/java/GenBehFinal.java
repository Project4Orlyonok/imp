import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class GenBehFinal extends Behaviour {
    boolean flag=false;
    AID topic;
    GenInf power;

    public GenBehFinal(AID topic, GenInf power) {
        this.topic = topic;
        this.power = power;
    }



    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchProtocol("Winner"), MessageTemplate.MatchTopic(topic));
        ACLMessage receivedMsg = myAgent.receive(mt);
        if (receivedMsg != null) {
            if (receivedMsg.getContent().equals(myAgent.getLocalName())){
                power.powmin(Double.parseDouble(receivedMsg.getOntology()),myAgent.getLocalName());
//                System.out.println(receivedMsg.getOntology());
            }
            flag = true;
        }
    }

    @Override
    public boolean done() {
        return flag;
    }
}
