import jade.core.AID;
import jade.core.ServiceException;
import jade.core.behaviours.Behaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class GenBehStart extends Behaviour {
    boolean flag = false;
    public AID topic;
    Time time;

    public GenBehStart(Time time) {
        this.time = time;
    }

    @Override
    public void action() {
//        ACLMessage receivedMsg;
        MessageTemplate mt = MessageTemplate.MatchProtocol("Start");
        ACLMessage receivedMsg = myAgent.receive(mt);
        if (receivedMsg != null) {
            topic = subsTopic(receivedMsg.getContent());
//            flag = true;
        }
//        System.out.println(topic);
        if (topic!=null) {
            myAgent.addBehaviour(new GenBehDur(time,topic));
            }

    }

    @Override
    public boolean done() {
        return false;
    }

//    @Override
//    public int onEnd() {
//        myAgent.addBehaviour(new GenBehDur(topic));
//        return super.onEnd();
//    }


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
