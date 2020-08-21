import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class DistribContinue extends Behaviour {

    AID topic;
    AID[] resultsAID;
    String consumer;
    double power;
    boolean flag = false;


    public DistribContinue(AID topic, AID[] resultsAID, String consumer, double power) {
        this.topic = topic;
        this.resultsAID = resultsAID;
        this.consumer = consumer;
        this.power = power;
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchProtocol("Ready"), MessageTemplate.MatchTopic(topic));
        ACLMessage receivedMsg = myAgent.receive(mt);
        if (receivedMsg != null) {
            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
            message.addReceiver(topic);
            message.setProtocol("Power");
            message.setContent(String.valueOf(power));

            myAgent.send(message);
            myAgent.addBehaviour(new DistribFinal(topic, resultsAID, consumer,power));
            flag = true;
        }
    }

    @Override
    public boolean done() {
        return flag;
    }

}
