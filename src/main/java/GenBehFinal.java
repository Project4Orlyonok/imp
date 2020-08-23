import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class GenBehFinal extends Behaviour {
    boolean flag=false;//private
    AID topic;
    GenerationInfo power;
    JsonGen jsonGen;
    Time time;

    public GenBehFinal(AID topic, GenerationInfo power, JsonGen jsonGen, Time time) {
        this.topic = topic;
        this.power = power;
        this.jsonGen=jsonGen;
        this.time=time;
    }



    @Override
    public void action() {
        double power1,power2;//иначе
        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchProtocol("Winner"), MessageTemplate.MatchTopic(topic));
        ACLMessage receivedMsg = myAgent.receive(mt);

        if (receivedMsg != null) {
            power1=power.allPower(myAgent.getLocalName());//comment
//            System.out.println(power.allpower(myAgent.getLocalName())+"  "+myAgent.getLocalName());

            if ((receivedMsg.getContent().equals(myAgent.getLocalName()))){//+comment
                power.minPower(Double.parseDouble(receivedMsg.getOntology()),myAgent.getLocalName());
//                System.out.println(receivedMsg.getOntology()+"  "+myAgent.getLocalName());
            }

            power2=power.allPower(myAgent.getLocalName());//+comment
            power.reservePower(-Double.parseDouble(receivedMsg.getOntology()),myAgent.getLocalName());//+comment
//            System.out.println(power.allpower(myAgent.getLocalName())+"  "+myAgent.getLocalName());

            Map<String,String> data= jsonGen.dataGen(power1,power2,time.getCurrentTime()/60%24);//+comment
            String stroka = jsonGen.stroka(data,myAgent.getLocalName());
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
