import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class GenBehDur extends Behaviour {
    AID topic;

    public GenBehDur(AID topic) {
        this.topic = topic;
    }

    @Override
    public void action() {
        MessageTemplate mt=MessageTemplate.and(MessageTemplate.MatchProtocol("Power"),MessageTemplate.MatchTopic(topic));
        ACLMessage receivedMsg = myAgent.receive(mt);
        if (!(receivedMsg ==null)){
            System.out.println(receivedMsg.getContent()+"    gen");
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
