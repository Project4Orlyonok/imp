import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class DistribContinue extends Behaviour {

    AID topic;
    AID[] resultsAID;
    String consumer;
    double power;
    boolean flag = false;
    GenerationInfo pow;
    jsonDistr json;
    Time time;
    String ontology;
    int count = 0;

    public DistribContinue(AID topic, AID[] resultsAID, String consumer, double power, GenerationInfo pow, jsonDistr json, Time time, String ontology) {
        this.topic = topic;
        this.resultsAID = resultsAID;
        this.consumer = consumer;
        this.power = power;
        this.pow = pow;
        this.json = json;
        this.time = time;
        this.ontology = ontology;
    }


    @Override
    public void onStart() {
//        pow.setAll(topic.getLocalName());
//        System.out.println(pow.LastAsk);
        super.onStart();
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchProtocol("Ready"), MessageTemplate.MatchTopic(topic));
        ACLMessage receivedMsg = myAgent.receive(mt);
        if (receivedMsg != null) {
            count++;
        }
        if (count == resultsAID.length) {
            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
            message.addReceiver(topic);
            message.setProtocol("Power");
            message.setContent(String.valueOf(power));
            message.setOntology(ontology);
//            System.out.println(receivedMsg.getOntology()+"  discontrec  "+topic.getLocalName());
            myAgent.send(message);
//            System.out.println(message.getOntology()+"  discontmes  "+topic.getLocalName());
            myAgent.addBehaviour(new DistribFinal(topic, resultsAID, consumer, power, json, time));
            flag = true;
        }
    }

    @Override
    public boolean done() {
        return flag;
    }

}
//            System.out.println(myAgent.getLocalName()+"  отправил  "+message.getContent());