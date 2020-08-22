import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class GenBehFinal extends Behaviour {
    boolean flag=false;
    AID topic;
    GenInf power;
    JsonGen jsonGen;
    Time time;

    public GenBehFinal(AID topic, GenInf power,JsonGen jsonGen,Time time) {
        this.topic = topic;
        this.power = power;
        this.jsonGen=jsonGen;
        this.time=time;
    }



    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchProtocol("Winner"), MessageTemplate.MatchTopic(topic));
        ACLMessage receivedMsg = myAgent.receive(mt);
        if (receivedMsg != null) {
            if ((receivedMsg.getContent().equals(myAgent.getLocalName()))){
                power.powmin(Double.parseDouble(receivedMsg.getOntology()),myAgent.getLocalName());
//                System.out.println(receivedMsg.getOntology());
            }
            power.minpower(0.0,myAgent.getLocalName());
            Map<String,String> data= jsonGen.dataGen(power.allpower(myAgent.getLocalName()),
                    myAgent.getLocalName(),time.getCurrentTime()/60);
            String stroka = jsonGen.stroka(data);
            String fileName =String.format("C:\\Users\\anna\\IdeaProjects\\imp\\%s.json",myAgent.getLocalName());
            try {
                FileOutputStream file =new FileOutputStream(fileName);
                file.write(stroka.getBytes());
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            flag = true;
        }
    }

    @Override
    public boolean done() {
        return flag;
    }
}
