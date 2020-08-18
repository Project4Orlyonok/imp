import jade.core.AID;
import jade.core.ServiceException;
import jade.core.behaviours.Behaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class DistribBeh extends Behaviour {

    @Override
    public void action() {
        ACLMessage receivedMsg = myAgent.receive();


//        myAgent.send(message);
//        System.out.println(kolvo);
        if (receivedMsg != null) {
            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
//            System.out.println(receivedMsg.getContent()+"  dis");
            AID topic = createTopic(receivedMsg.getSender().getLocalName()+"t");
//            System.out.println(myAgent.getAID(receivedMsg.getSender().getLocalName()+"t"));
            AID[] resultsAID;
            resultsAID = DfSearch("Generation",receivedMsg);

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
            myAgent.send(message1);
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

    public AID[] DfSearch(String type,ACLMessage receivedMsg) {
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
}
